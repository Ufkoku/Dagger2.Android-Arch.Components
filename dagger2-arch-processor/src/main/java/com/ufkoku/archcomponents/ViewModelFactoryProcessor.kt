package com.ufkoku.archcomponents

import com.squareup.javapoet.*
import com.ufkoku.archcomponents.annotations.ConstructorPriority
import com.ufkoku.archcomponents.annotations.DefaultValuesProvider
import com.ufkoku.archcomponents.annotations.GenerateFactory
import com.ufkoku.archcomponents.annotations.Name
import com.ufkoku.archcomponents.exception.FailedToGenerateException
import org.jetbrains.annotations.Nullable
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.ExecutableType
import javax.lang.model.type.PrimitiveType
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic
import kotlin.collections.HashMap


@SupportedAnnotationTypes("com.ufkoku.archcomponents.annotations.GenerateFactory")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ViewModelFactoryProcessor : AbstractProcessor() {

    companion object {

        private const val VIEW_MODEL_QUALIFIED_NAME = "androidx.lifecycle.ViewModel"

        private const val VIEW_MODEL_FACTORY_QUALIFIED_NAME = "androidx.lifecycle.ViewModelProvider.Factory"

        private const val VIEW_MODEL_SAVED_STATE_FACTORY_QUALIFIED_NAME = "androidx.lifecycle.AbstractSavedStateVMFactory"

        private const val SAVED_STATE_HANDLE_QUALIFIED_NAME = "androidx.lifecycle.SavedStateHandle"

        private const val SAVED_STATE_REGISTRY_OWNER_QUALIFIED_NAME = "androidx.savedstate.SavedStateRegistryOwner"

        private const val INJECT_QUALIFIED_NAME = "javax.inject.Inject"

        private const val FACTORY_SUFFIX = "Factory"

        private const val CREATE_METHOD_NAME = "create"

        private const val CREATE_METHOD_KEY_ARG = "key"
        private const val CREATE_METHOD_MODEL_CLASS_ARG = "modelClass"
        private const val CREATE_SAVED_STATE_HANDLE_ARG = "handle"

        private const val CREATE_TYPE = "T"

    }

    private var enabled: Boolean = false

    private lateinit var elViewModel: TypeElement
    private lateinit var tpViewModel: DeclaredType

    private lateinit var elViewModelFactory: TypeElement
    private lateinit var tpViewModelFactory: DeclaredType

    private var savedStateEnabled: Boolean = false

    private lateinit var elSavedStateViewModelFactory: TypeElement
    private lateinit var tpSavedStateViewModelFactory: DeclaredType

    private lateinit var elSavedStateHandle: TypeElement
    private lateinit var tpSavedStateHandle: DeclaredType

    private lateinit var elSavedStateRegistryOwner: TypeElement
    private lateinit var tpSavedStateRegistryOwner: DeclaredType

    private var injectEnabled: Boolean = false

    private lateinit var elInjectAnnotation: TypeElement

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        try {
            elViewModel = processingEnv.elementUtils.getTypeElement(VIEW_MODEL_QUALIFIED_NAME)
            tpViewModel = createDeclaredType(elViewModel)

            elViewModelFactory = processingEnv.elementUtils.getTypeElement(VIEW_MODEL_FACTORY_QUALIFIED_NAME)
            tpViewModelFactory = createDeclaredType(elViewModelFactory)

            enabled = true
        } catch (ex: Exception) {
            enabled = false
            printMessage(Diagnostic.Kind.ERROR, "Failed to resolve ViewModel dependencies")
        }

        try {
            elSavedStateViewModelFactory = processingEnv.elementUtils.getTypeElement(VIEW_MODEL_SAVED_STATE_FACTORY_QUALIFIED_NAME)
            tpSavedStateViewModelFactory = createDeclaredType(elSavedStateViewModelFactory)

            elSavedStateHandle = processingEnv.elementUtils.getTypeElement(SAVED_STATE_HANDLE_QUALIFIED_NAME)
            tpSavedStateHandle = createDeclaredType(elSavedStateHandle)

            elSavedStateRegistryOwner = processingEnv.elementUtils.getTypeElement(SAVED_STATE_REGISTRY_OWNER_QUALIFIED_NAME)
            tpSavedStateRegistryOwner = createDeclaredType(elSavedStateRegistryOwner)

            savedStateEnabled = true
        } catch (ex: Exception) {
            savedStateEnabled = false
            printMessage(Diagnostic.Kind.WARNING, "Failed to resolve SavedState dependencies")
        }

        try {
            elInjectAnnotation = processingEnv.elementUtils.getTypeElement(INJECT_QUALIFIED_NAME)
            injectEnabled = true
        } catch (ex: Exception) {
            injectEnabled = false
            printMessage(Diagnostic.Kind.WARNING, "Failed to resolve Inject annotation")
        }
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        if (enabled && annotations.isNotEmpty()) {
            for (annotation in annotations) {
                val elements = roundEnv.getElementsAnnotatedWith(annotation)
                for (element in elements) {
                    element as TypeElement
                    val declaredType = createDeclaredType(element)
                    //type of marked node must be class, not abstract and be ViewModel
                    if (element.kind == ElementKind.CLASS
                            && !element.modifiers.contains(Modifier.ABSTRACT)
                            && processingEnv.typeUtils.isAssignable(declaredType, tpViewModel)) {
                        try {
                            handleType(element, declaredType)
                        } catch (ex: FailedToGenerateException) {
                            printMessage(
                                    Diagnostic.Kind.ERROR,
                                    "Failed to generate factory for ${element.qualifiedName}. Error message: ${ex.message}",
                                    element)
                        }
                    } else {
                        printMessage(
                                Diagnostic.Kind.WARNING,
                                "Factory won't be generated for ${element.qualifiedName}, because it is abstract or not an instance of androidx.lifecycle.ViewModel")
                    }
                }
            }
            return true
        } else {
            return false
        }
    }

    @Throws(FailedToGenerateException::class)
    private fun handleType(typeElement: TypeElement, declaredType: DeclaredType) {
        //declare basic factory class config
        val factoryBuilder = TypeSpec.classBuilder("${typeElement.simpleName}$FACTORY_SUFFIX")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addTypeVariables(convertTypeParametersToTypeVariableNames(typeElement.typeParameters))

        //extract all public constructors
        val constructors = collectConstructorDataSorted(typeElement, declaredType)

        //check if SavedStateHandle is used in this ViewModel
        val hasSavedStateArg = isSavedStateVm(constructors)
        val defaultValuesProvider: DefaultValueProviderData?
        //if SavedStateHandle is used - mark superclass of factory as SaveStateViewModelFactory
        if (hasSavedStateArg) {
            factoryBuilder.superclass(TypeName.get(tpSavedStateViewModelFactory))
            //try to find default values provider method
            defaultValuesProvider = findDefaultValueProviderMethod(typeElement)
        } else {
            factoryBuilder.addSuperinterface(TypeName.get(tpViewModelFactory))
            defaultValuesProvider = null
        }

        //accumulate all arguments from all constructors to single set
        val arguments = aggregateConstructorArguments(constructors)

        //extract GenerateFactory inject flag
        val addInjectAnnotation = typeElement.getAnnotation(GenerateFactory::class.java)?.inject == true

        //fill factoryBuilder with constructor and fields
        fillConstructorAndFields(arguments, factoryBuilder, addInjectAnnotation, hasSavedStateArg, defaultValuesProvider)

        //implement create method according to available constructors and factory type
        implementCreateMethod(typeElement, declaredType, constructors, factoryBuilder, hasSavedStateArg)

        val javaFile = JavaFile.builder(
                processingEnv
                        .elementUtils
                        .getPackageOf(typeElement)
                        .toString(),
                factoryBuilder.build())
                .build()

        javaFile.writeTo(processingEnv.filer)
    }

    private fun collectConstructorDataSorted(typeElement: TypeElement, declaredType: DeclaredType) =
            typeElement.enclosedElements
                    .filter { it.kind == ElementKind.CONSTRUCTOR && it.modifiers.contains(Modifier.PUBLIC) }
                    .map {
                        val priorityAnn = it.getAnnotation(ConstructorPriority::class.java)
                        val priority = priorityAnn?.priority ?: Int.MAX_VALUE

                        it as ExecutableElement
                        val type = processingEnv.typeUtils.asMemberOf(declaredType, it) as ExecutableType

                        val args = it.parameters.mapIndexed { i, e ->
                            VariableArgumentData(e, type.parameterTypes[i])
                        }

                        ConstructorData(priority, it, type, args)
                    }
                    .sorted()

    private fun isSavedStateVm(constructors: List<ConstructorData>) =
            savedStateEnabled && constructors.firstOrNull { constructor ->
                constructor.type.parameterTypes.firstOrNull {
                    processingEnv.typeUtils.isAssignable(it, tpSavedStateHandle)
                } != null
            } != null

    private fun findDefaultValueProviderMethod(typeElement: TypeElement): DefaultValueProviderData? =
            typeElement.enclosedElements.firstOrNull {
                val annotated = it.getAnnotation(DefaultValuesProvider::class.java) != null
                if (annotated) {
                    val publicStatic = it.modifiers.contains(Modifier.STATIC)
                            && it.modifiers.contains(Modifier.PUBLIC)
                    if (publicStatic) {
                        true
                    } else {
                        processingEnv.messager.printMessage(
                                Diagnostic.Kind.ERROR,
                                "Method marked with DefaultValuesProvider must be public static"
                        )
                        false
                    }
                } else {
                    false
                }
            }?.let {
                it as ExecutableElement
                val type = it.asType() as ExecutableType
                val args = it.parameters.mapIndexed { i, e ->
                    VariableArgumentData(e, type.parameterTypes[i])
                }

                DefaultValueProviderData(it, type, args)
            }

    @Throws(FailedToGenerateException::class)
    private fun aggregateConstructorArguments(list: List<ConstructorData>): Set<IArgumentData> {
        val savedMap = HashMap<String, IArgumentData>()
        val savedSet = TreeSet<IArgumentData> { t1, t2 -> t1.name.compareTo(t2.name) }

        list.flatMap { it.params }
                .filter { arg -> !savedStateEnabled || !processingEnv.typeUtils.isAssignable(arg.type, tpSavedStateHandle) }
                .forEach {
                    val saved = savedMap[it.name]
                    if (saved != null && !processingEnv.typeUtils.isSameType(it.type, saved.type)) {
                        throw FailedToGenerateException("Two arguments with same names ${it.name} but different types ${it.type} and ${saved.type} found.")
                    }
                    savedSet.add(it)
                }

        return savedSet
    }

    @Throws(FailedToGenerateException::class)
    private fun fillConstructorAndFields(arguments: Set<IArgumentData>,
                                         typeBuilder: TypeSpec.Builder,
                                         addInjectAnnotation: Boolean,
                                         superTypeIsSavedStateVmFactory: Boolean,
                                         defaultValuesProvider: DefaultValueProviderData?) {
        val usedArgsMap = HashMap<String, IArgumentData>()

        val constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)

        if (injectEnabled && addInjectAnnotation) {
            constructorBuilder.addAnnotation(ClassName.get(elInjectAnnotation))
        }

        if (savedStateEnabled && superTypeIsSavedStateVmFactory) {
            val savableStateRegistryOwner = TypeArgumentData(elSavedStateRegistryOwner, tpSavedStateRegistryOwner)

            usedArgsMap[savableStateRegistryOwner.name] = savableStateRegistryOwner
            constructorBuilder.addParameter(TypeName.get(savableStateRegistryOwner.type), savableStateRegistryOwner.name)

            if (defaultValuesProvider != null) {
                val argsBuilder = StringBuilder()
                defaultValuesProvider.params.forEach {
                    //fill args string builder for super call
                    if (argsBuilder.isNotEmpty()) {
                        argsBuilder.append(", ")
                    }
                    argsBuilder.append(it.name)

                    //add parameter to constructor args
                    constructorBuilder.addParameter(
                            ParameterSpec.builder(TypeName.get(it.type), it.name)
                                    .addAnnotation(Nullable::class.java)
                                    .build()
                    )

                    //save arg as used
                    usedArgsMap[it.name] = it
                }
                constructorBuilder.addStatement("super(\$L, \$T.\$L(\$L))",
                        savableStateRegistryOwner.name,
                        defaultValuesProvider.element.enclosingElement,
                        defaultValuesProvider.element.simpleName,
                        argsBuilder.toString())
            } else {
                constructorBuilder.addStatement("super(\$L, null)", savableStateRegistryOwner.name)
            }
        }

        arguments.forEach {
            val type = if (it.type.kind.isPrimitive) {
                processingEnv.typeUtils.boxedClass(it.type as PrimitiveType).asType()
            } else {
                it.type
            }

            //build field for each argument
            val fieldSpec = FieldSpec.builder(
                    TypeName.get(type),
                    it.name,
                    Modifier.FINAL, Modifier.PRIVATE)
                    .run {
                        addAnnotation(Nullable::class.java)
                        build()
                    }

            //check if parameter is contained in defaultValueProviderMethod
            //don't add it to constructor args if it is already added
            val used = usedArgsMap[it.name]
            if (used != null) {
                //check if argument with same name has same type
                if (!processingEnv.typeUtils.isSameType(it.type, used.type)) {
                    throw FailedToGenerateException("Two arguments with same names ${it.name} but different types ${it.type} and ${used.type} found.")
                }
            } else {
                val parameterSpec = ParameterSpec.builder(fieldSpec.type, fieldSpec.name)
                        .run {
                            addAnnotation(Nullable::class.java)
                            build()
                        }

                constructorBuilder.addParameter(parameterSpec)
            }

            constructorBuilder.addStatement("this.${fieldSpec.name} = ${fieldSpec.name}")

            typeBuilder.addField(fieldSpec)
        }

        typeBuilder.addMethod(constructorBuilder.build())
    }

    private fun implementCreateMethod(@Suppress("UNUSED_PARAMETER") typeElement: TypeElement,
                                      declaredType: DeclaredType,
                                      constructors: List<ConstructorData>,
                                      typeBuilder: TypeSpec.Builder,
                                      superTypeIsSavedStateVmFactory: Boolean) {

        val typeParameter = TypeVariableName.get(CREATE_TYPE, TypeName.get(tpViewModel))

        val createMethodSpecBuilder = MethodSpec.methodBuilder(CREATE_METHOD_NAME)
                .addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(typeParameter)
                .returns(TypeVariableName.get(CREATE_TYPE))

        if (savedStateEnabled && superTypeIsSavedStateVmFactory) {
            createMethodSpecBuilder.addParameter(TypeName.get(String::class.java), CREATE_METHOD_KEY_ARG)
            createMethodSpecBuilder.addParameter(
                    ParameterizedTypeName.get(ClassName.get(Class::class.java), typeParameter),
                    CREATE_METHOD_MODEL_CLASS_ARG)
            createMethodSpecBuilder.addParameter(TypeName.get(tpSavedStateHandle), CREATE_SAVED_STATE_HANDLE_ARG)
        } else {
            createMethodSpecBuilder.addParameter(
                    ParameterizedTypeName.get(ClassName.get(Class::class.java), typeParameter),
                    CREATE_METHOD_MODEL_CLASS_ARG)
        }

        var defaultConstructorIndex = -1

        val methodBody = CodeBlock.builder()

        for ((constructorIndex, constructorData) in constructors.withIndex()) {
            val ifStatementBuilder = StringBuilder()
            val constructorArgs = StringBuilder()

            for ((argIndex, arg) in constructorData.params.withIndex()) {
                if (argIndex != 0) {
                    constructorArgs.append(", ")
                }

                if (savedStateEnabled && processingEnv.typeUtils.isAssignable(arg.type, tpSavedStateHandle)) {
                    constructorArgs.append(CREATE_SAVED_STATE_HANDLE_ARG)
                } else {
                    if (arg.type.getAnnotation(Nullable::class.java) == null
                            || arg.type.kind.isPrimitive) {
                        if (ifStatementBuilder.isNotEmpty()) {
                            ifStatementBuilder.append(" && ")
                        }
                        ifStatementBuilder.append("this.${arg.name} != null")
                    }

                    constructorArgs.append(arg.name)
                }

            }

            //have what to check for this constructor
            if (ifStatementBuilder.isNotEmpty()) {
                //if it is a first constructor - start control flow
                if (constructorIndex == 0) {
                    methodBody.beginControlFlow("if ($ifStatementBuilder)")
                } else {
                    methodBody.nextControlFlow("else if ($ifStatementBuilder)")
                }
            } else {
                //default constructor found
                defaultConstructorIndex = constructorIndex
                //if it is not first constructor
                if (defaultConstructorIndex != 0) {
                    methodBody.nextControlFlow("else")
                }
            }

            methodBody.addStatement("return (\$L) new \$T(\$L)", CREATE_TYPE, declaredType, constructorArgs)

            //break when default constructor is found
            if (defaultConstructorIndex != -1) break
        }

        //if default constructor is not first one
        if (defaultConstructorIndex != 0) {
            //if default constructor  doesn't exists
            if (defaultConstructorIndex == -1) {
                methodBody.nextControlFlow(" else ")
                methodBody.addStatement("throw new \$T(\"Unable to initialize ViewModel\")", IllegalArgumentException::class.java)
            }
            methodBody.endControlFlow()
        }

        createMethodSpecBuilder.addCode(methodBody.build())

        typeBuilder.addMethod(createMethodSpecBuilder.build())
    }


    private fun createDeclaredType(typeElement: TypeElement): DeclaredType {
        return if (typeElement.typeParameters.size > 0) {
            val types = arrayOfNulls<TypeMirror>(typeElement.typeParameters.size)
            for (i in types.indices) {
                types[i] = typeElement.typeParameters[i].asType()
            }
            processingEnv.typeUtils.getDeclaredType(typeElement, *types)
        } else {
            processingEnv.typeUtils.getDeclaredType(typeElement)
        }
    }

    private fun convertTypeParametersToTypeVariableNames(parameterElements: List<TypeParameterElement>): List<TypeVariableName> {
        val typeVariableNames = ArrayList<TypeVariableName>()
        for (element in parameterElements) {
            typeVariableNames.add(TypeVariableName.get(element))
        }
        return typeVariableNames
    }

    private fun printMessage(kind: Diagnostic.Kind, message: CharSequence, element: Element? = null) {
        if (element == null) {
            processingEnv.messager.printMessage(kind, message)
        } else {
            processingEnv.messager.printMessage(kind, message, element)
        }
    }

    private data class ConstructorData(val priority: Int,
                                       val element: ExecutableElement,
                                       val type: ExecutableType,
                                       val params: List<IArgumentData>) : Comparable<ConstructorData> {

        override fun compareTo(other: ConstructorData): Int {
            val priorityComparision = Integer.compare(priority, other.priority)

            //if priority is same sort by args count
            if (priorityComparision == 0) {
                return Integer.compare(other.params.size, params.size)
            }

            return priorityComparision
        }

    }

    private data class DefaultValueProviderData(val element: ExecutableElement,
                                                val type: ExecutableType,
                                                val params: List<IArgumentData>)

    private interface IArgumentData {
        val name: String
        val type: TypeMirror
    }

    private data class VariableArgumentData(val element: VariableElement,
                                            override val type: TypeMirror) : IArgumentData {

        override val name: String = element.getVariableName()

        private fun VariableElement.getVariableName(): String {
            val nameAnnotation = this.getAnnotation(Name::class.java)
            return nameAnnotation?.name ?: this.simpleName.toString()
        }

    }

    private data class TypeArgumentData(val element: TypeElement, override val type: DeclaredType) : IArgumentData {

        override val name: String = element.simpleName.toString().decapitalize()

    }

}

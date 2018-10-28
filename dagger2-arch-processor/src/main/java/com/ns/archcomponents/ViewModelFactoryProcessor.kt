package com.ns.archcomponents

import com.ns.archcomponents.annotations.ConstructorPriority
import com.ns.archcomponents.annotations.GenerateFactory
import com.ns.archcomponents.annotations.Name
import com.squareup.javapoet.*
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


@SupportedAnnotationTypes("com.ns.archcomponents.annotations.GenerateFactory")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ViewModelFactoryProcessor : AbstractProcessor() {

    companion object {

        private const val VIEW_MODEL_QUALIFIED_NAME = "androidx.lifecycle.ViewModel"

        private const val VIEW_MODEL_FACTORY_QUALIFIED_NAME = "androidx.lifecycle.ViewModelProvider.Factory"

        private const val INJECT_QUALIFIED_NAME = "javax.inject.Inject"

        private const val FABRIC_SUFFIX = "Factory"

        private const val CREATE_METHOD_NAME = "create"

        private const val CREATE_TYPE = "T"

    }

    private lateinit var elViewModel: TypeElement

    private lateinit var tpViewModel: DeclaredType

    private lateinit var elViewModelFactory: TypeElement

    private lateinit var tpViewModelFactory: DeclaredType

    private lateinit var elInjectAnnotation: TypeElement

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        elViewModel = processingEnv.elementUtils.getTypeElement(VIEW_MODEL_QUALIFIED_NAME)
        tpViewModel = createDeclaredType(elViewModel)

        elViewModelFactory = processingEnv.elementUtils.getTypeElement(VIEW_MODEL_FACTORY_QUALIFIED_NAME)
        tpViewModelFactory = createDeclaredType(elViewModelFactory)

        elInjectAnnotation = processingEnv.elementUtils.getTypeElement(INJECT_QUALIFIED_NAME)
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        if (annotations.isNotEmpty()) {
            for (annotation in annotations) {
                val elements = roundEnv.getElementsAnnotatedWith(annotation)
                for (element in elements) {
                    element as TypeElement
                    val declaredType = createDeclaredType(element)
                    if (element.kind == ElementKind.CLASS
                            && !element.modifiers.contains(Modifier.ABSTRACT)
                            && processingEnv.typeUtils.isAssignable(declaredType, tpViewModel)) {
                        handleType(element, declaredType)
                    } else {
                        printMessage(
                                Diagnostic.Kind.WARNING,
                                "Fabric won't be generated for ${element.qualifiedName}, because it is abstract or not an instance of androidx.lifecycle.ViewModel")
                    }
                }
            }
            return true
        } else {
            return false
        }
    }

    private fun handleType(typeElement: TypeElement, declaredType: DeclaredType) {
        val fabricBuilder = TypeSpec.classBuilder("${typeElement.simpleName}$FABRIC_SUFFIX")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(TypeName.get(tpViewModelFactory))

        fabricBuilder.addTypeVariables(convertTypeParametersToTypeVariableNames(typeElement.typeParameters))

        val constructors = collectConstructorDataSorted(typeElement, declaredType)
        val arguments = extractArguments(constructors)

        val addInjectAnnotation =
                typeElement.getAnnotation(GenerateFactory::class.java)?.inject ?: false

        fillConstructorAndFields(arguments, fabricBuilder, addInjectAnnotation)

        addCreateMethod(typeElement, declaredType, constructors, fabricBuilder)

        val javaFile = JavaFile.builder(
                processingEnv
                        .elementUtils
                        .getPackageOf(typeElement)
                        .toString(),
                fabricBuilder.build())
                .build()

        javaFile.writeTo(processingEnv.filer)
    }

    private fun fillConstructorAndFields(arguments: Set<ArgumentData>,
                                         typeBuilder: TypeSpec.Builder,
                                         addInjectAnnotation: Boolean) {
        val constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)

        if (addInjectAnnotation) {
            constructorBuilder.addAnnotation(ClassName.get(elInjectAnnotation))
        }

        arguments.forEach {
            val type = if (it.type.kind.isPrimitive) {
                processingEnv.typeUtils.boxedClass(it.type as PrimitiveType).asType()
            } else {
                it.type
            }

            val fieldSpec = FieldSpec.builder(
                    TypeName.get(type),
                    it.element.getName(),
                    Modifier.FINAL, Modifier.PRIVATE)
                    .run {
                        addAnnotation(Nullable::class.java)
                        build()
                    }

            val parameterSpec = ParameterSpec.builder(fieldSpec.type, fieldSpec.name)
                    .run {
                        addAnnotation(Nullable::class.java)
                        build()
                    }

            constructorBuilder.addParameter(parameterSpec)
            constructorBuilder.addStatement("this.${fieldSpec.name} = ${parameterSpec.name}")

            typeBuilder.addField(fieldSpec)
        }

        typeBuilder.addMethod(constructorBuilder.build())
    }

    private fun addCreateMethod(@Suppress("UNUSED_PARAMETER") typeElement: TypeElement,
                                declaredType: DeclaredType,
                                constructors: List<ConstructorData>,
                                typeBuilder: TypeSpec.Builder) {

        val typeParameter = TypeVariableName.get(CREATE_TYPE, TypeName.get(tpViewModel))

        val createMethodSpecBuilder = MethodSpec.methodBuilder(CREATE_METHOD_NAME)
                .addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(typeParameter)
                .addParameter(ParameterizedTypeName.get(ClassName.get(Class::class.java), typeParameter), "modelClass")
                .returns(TypeVariableName.get(CREATE_TYPE))

        var defaultConstructorIndex = -1

        val methodBody = CodeBlock.builder()
        constructors.forEachIndexed { constructorIndex, constructorData ->
            val ifStatementBuilder = StringBuilder()
            val constructorArgs = StringBuilder()

            constructorData.element.parameters.forEachIndexed { argIndex, argItem ->
                val name = argItem.getName()

                if (argItem.getAnnotation(Nullable::class.java) == null
                        || argItem.asType().kind.isPrimitive) {
                    if (ifStatementBuilder.isNotEmpty()) {
                        ifStatementBuilder.append(" && ")
                    }
                    ifStatementBuilder.append("this.$name != null")
                }

                if (argIndex != 0) {
                    constructorArgs.append(", ")
                }
                constructorArgs.append(name)
            }

            if (defaultConstructorIndex == -1) {
                if (ifStatementBuilder.isNotEmpty()) {
                    if (constructorIndex == 0) {
                        methodBody.beginControlFlow("if ($ifStatementBuilder)")
                    } else {
                        methodBody.nextControlFlow("else if ($ifStatementBuilder)")
                    }
                } else {
                    defaultConstructorIndex = constructorIndex
                    if (defaultConstructorIndex != 0) {
                        methodBody.nextControlFlow("else")
                    }
                }
                methodBody.addStatement("return (\$L) new \$T(\$L)", CREATE_TYPE, declaredType, constructorArgs)
            }
        }

        if (defaultConstructorIndex != 0) {
            if (defaultConstructorIndex == -1) {
                methodBody.nextControlFlow(" else ")
                methodBody.addStatement("throw new \$T(\"Unable to initialize ViewModel\")", IllegalArgumentException::class.java)
            }
            methodBody.endControlFlow()
        }

        createMethodSpecBuilder.addCode(methodBody.build())

        typeBuilder.addMethod(createMethodSpecBuilder.build())
    }

    private fun collectConstructorDataSorted(typeElement: TypeElement,
                                             declaredType: DeclaredType): List<ConstructorData> {
        return typeElement.enclosedElements
                .filter { it.kind == ElementKind.CONSTRUCTOR && it.modifiers.contains(Modifier.PUBLIC) }
                .map {
                    val priorityAnn = it.getAnnotation(ConstructorPriority::class.java)
                    val priority = priorityAnn?.priority ?: Int.MAX_VALUE
                    ConstructorData(
                            priority,
                            it as ExecutableElement,
                            processingEnv.typeUtils.asMemberOf(declaredType, it) as ExecutableType)
                }
                .sorted()
    }

    private fun extractArguments(list: List<ConstructorData>): Set<ArgumentData> {
        return list.fold(TreeSet { t1: ArgumentData, t2: ArgumentData ->
            t1.element.getName().compareTo(t2.element.getName())
        }) { accumulator, item ->
            item.element.parameters
                    .forEachIndexed { index, variableElement ->
                        val parameterType = item.type.parameterTypes[index]
                        accumulator.add(ArgumentData(variableElement, parameterType))
                    }
            accumulator
        }
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

    private fun printMessage(kind: Diagnostic.Kind, message: CharSequence) {
        processingEnv.messager.printMessage(kind, message)
    }

    private fun VariableElement.getName(): String {
        val nameAnnotation = this.getAnnotation(Name::class.java)
        return nameAnnotation?.name ?: this.simpleName.toString()
    }

    private data class ConstructorData(val priority: Int,
                                       val element: ExecutableElement,
                                       val type: ExecutableType) : Comparable<ConstructorData> {

        override fun compareTo(other: ConstructorData): Int {
            return priority - other.priority
        }

    }

    private data class ArgumentData(val element: VariableElement,
                                    val type: TypeMirror)

}

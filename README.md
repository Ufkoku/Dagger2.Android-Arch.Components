# Dagger2.Android + Arch.Components  
  
Example of usage of Dagger.Android and Architecture components
  
## Main ideas  
1. Create an architecture with [savable](https://developer.android.com/topic/libraries/architecture/saving-states) to `Bundle` `ViewModels`;
2. Move `ViewModel` initialization to Dagger2.Android modules;
3. Totally incapsulate `ViewModel` managment logic from final Activity/Fragment.

## Main classes and key parts  
1. [DaggerArchActivity](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchActivity.kt) implements Dagger2.Android contracts
2. [DaggerArchFragment](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchFragment.kt) same features as `DaggerArchActivity` has;
3. [DaggerArchDialogFragment](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchDialogFragment.kt) same features as `DaggerArchActivity` has;
4. [DaggerViewModel](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerViewModel.kt) which provides `Factory` and supports injections via `AndroidInjector`;
5. [DaggerSavableViewModel](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerSavableViewModel.kt) which provides `Factory`, supports injections via `AndroidInjector` and accepts `SavedStateHandle` as the second argument of constructor;
6. Creating `Factory` for each `ViewModel` provides some boilerplate code, so there is an AnnotationProcessor for this purpose. Mark class with `GenerateFactory` and its constructors with `ConstructorPriority`, if needed. Usage example is [here](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ufkoku/daggernewway/ui/main/post/viewmodel/CommentsViewModel.kt)

## Usage as library

You can add `dagger2-arch-components` module as dependency via maven:

```gradle
repositories {
    maven { url 'https://dl.bintray.com/ufkoku/maven/' }
}

dependencies {
    def ver_dagger_arch_components = "2.3.1"
    implementation "com.ufkoku:dagger2-arch-components:$ver_dagger_arch_components"
    
    //optional if you want to use factories instead of injection to `ViewModel` 
    compileOnly "com.ufkoku:dagger2-arch-annotations:$ver_dagger_arch_components"
    kapt "com.ufkoku:dagger2-arch-processor:$ver_dagger_arch_components"
}

kapt {
    correctErrorTypes = true
}
```

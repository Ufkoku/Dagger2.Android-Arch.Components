# Dagger2.Android + Arch.Components  
  
Example of usage of Dagger.Android and Architecture components
  
## Main ideas  
1. Create an architecture with [savable](https://developer.android.com/topic/libraries/architecture/saving-states) to `Bundle` `ViewModels`;
2. Move `ViewModel` initialization to Dagger2.Android modules;
3. Totally incapsulate `ViewModel` managment logic from final Activity/Fragment.

## Main classes and key parts  
1. [DaggerArchActivity](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchActivity.kt) which is base class for activities in the app. It saves provided `savedInstanceState` to use it for `ViewModel` initialization. Also it implements Dagger2.Android injections. And uses its `ViewModelStore` to save attached `ViewModel`s to Bundle;
2. [DaggerArchFragment](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchFragment.kt) same features as `DaggerArchActivity` has;
3. [DaggerArchDialogFragment](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchDialogFragment.kt) same features as `DaggerArchActivity` has;
4. Creating `Factory` for each `ViewModel` provides some boilerplate code, so there is an AnnotationProcessor for this purpose. Usage examples are [here](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ufkoku/daggernewway/ui/main/feed/viewmodel/FeedViewModel.kt) and [here](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ufkoku/daggernewway/ui/main/post/viewmodel/CommentsViewModel.kt). Mark class with `GenerateFactory` and its constructors with `ConstructorPriority`, if needed.

## Dagger2 Usage Concept
1. Each component has minimum two Dagger2 modules:
    * The [first one](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ufkoku/daggernewway/di/ui/main/post/PostCommentsInjectorModule.kt) for subcomponent generation;
    * [Other](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ufkoku/daggernewway/di/ui/main/post/PostCommentsFragmentModule.kt) for dependencies providing;
2. For `ViewModel` initialization from parameters or saved state, you need to provide them some how. Define public fields or methods for arguments in classes, and use them inside Dagger2 modules.

## Usage as library

You can add `dagger2-arch-components` module as dependency via maven:

```gradle
repositories {
    maven { url 'https://dl.bintray.com/ufkoku/maven/' }
}

dependencies {
    def ver_dagger_arch_components = "2.1.1"
    implementation "com.ufkoku:dagger2-arch-components:$ver_dagger_arch_components"
    compileOnly "com.ufkoku:dagger2-arch-annotations:$ver_dagger_arch_components"
    kapt "com.ufkoku:dagger2-arch-processor:$ver_dagger_arch_components"
}

kapt {
    correctErrorTypes = true
}
```

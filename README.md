# Dagger2.Android + Arch.Components  
  
Example of usage of Dagger.Android and Architecture components
  
## Main ideas  
1. Create an architecture with [savable](https://developer.android.com/topic/libraries/architecture/saving-states) to `Bundle` `ViewModels`;
2. Move `ViewModel` initialization to Dagger2.Android modules;
3. Totally incapsulate `ViewModel` managment logic from final Activity/Fragment.

## Main classes and key parts  
1. [DaggerArchActivity](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchActivity.kt) which is base class for activities in the app. It saves provided `savedInstanceState` to use it for `ViewModel` initialization. Also it implements Dagger2.Android injections. And uses its `ViewModelStore` to save attached `ViewModel`s to Bundle;
2. [DaggerArchFragment](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchFragment.kt) same features as `AppActivity` has;
3. `ViewModelStore` incapsulates `Map` with attached `ViewModel`s, so you we need some tools to access them, which implemented [here](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/ViewModelUtils.kt);
4. And we need a `ViewModel` which saves its state, base class for it is [here](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/SavableViewModel.kt). It contains a `bundleSuffix` field, which should resolve bundle key conflict, if two same `ViewModel`s are attached to same anchor;
5. Also creating `Factory` for each `ViewModel` provides some boilerplate code, so there is an AnnotationProcessor for this purpose. Usage example is [here](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ns/daggernewway/ui/main/post/CommentsViewModel.kt), mark class with `GenerateFactory` and its constructors with `ConstructorPriority`.

## Dagger2 Usage Concept
1. Each component has minimum two Dagger2 modules:
    * The [first one](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ns/daggernewway/di/postcomments/PostCommentsInjectorModule.kt) for subcomponent generation;
    * [Other](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ns/daggernewway/di/postcomments/PostCommentsModule.kt) for dependencies providing;
2. For `ViewModel` initialization from parameters or saved state, you need to provide them some how. Define public fields for arguments in classes, and use them inside Dagger2 modules.

## Usage as library

You can add `dagger2-arch-components` module as dependency via maven:

```gradle
repositories {
    maven { url 'https://dl.bintray.com/ufkoku/maven/' }
}

dependencies {
    implementation 'com.ufkoku:dagger2-arch-components:1.5.4'
    implementation 'com.ufkoku:dagger2-arch-annotations:1.5.4'
    kapt 'com.ufkoku:dagger2-arch-processor:1.5.4'
}
```

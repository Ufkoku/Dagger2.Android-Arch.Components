# Dagger2.Android + Arch.Components  
  
Example of usage of Dagger.Android and Architecture compomponents  
  
## Main ideas  
1. Create an architecture with [savable](https://developer.android.com/topic/libraries/architecture/saving-states) to `Bundle` `ViewModels`;
2. Move `ViewModel` initialization to Dagger2.Android modules;

## Main classes and key parts  
1. [DaggerArchActivity](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchActivity.kt) which is base class for activities in the app. It saves provided `savedInstanceState` to use it for `ViewModel` initialization. Also it implements Dagger2.Android injections. And uses its `ViewModelStore` to save attached `ViewModel`s to Bundle;
2. [DaggerArchFragment](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/DaggerArchFragment.kt) same features as `AppActivity` has;
3. `ViewModelStore` incapsulates `Map` with attached `ViewModel`s, so you we need some tools to access them, which implemented [here](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/ViewModelUtils.kt);
4. And we need a `ViewModel` which saves its state, base class for it is [here](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/dagger2-arch-components/src/main/java/com/ufkoku/archcomponents/ViewModelUtils.kt). It contains a `bundleSuffix` field, which should resolve bundle key conflict, if two same `ViewModel`s are attached to same anchor;

## Dagger2 Usage Concept
1. Each component has minimum two Dagger2 modules:
    * The [first one](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ns/daggernewway/di/postcomments/PostCommentsInjectorModule.kt) for subcomponent generation;
    * [Other](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ns/daggernewway/di/postcomments/PostCommentsModule.kt) for dependencies providing;
2. For `ViewModel` initialization from parameters or saved state, you need to provide them some how. Define a [nested module](https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/abbf0e43fabbdea367ec200592db5f0cd1959947/app/src/main/java/com/ns/daggernewway/ui/main/post/PostCommentsFragment.kt#L61) in `Fragment` or `Activity` for this! 
    * Use `include` to attach nested module to [dependency provider module]((https://github.com/Ufkoku/Dagger2.Android-Arch.Components/blob/master/app/src/main/java/com/ns/daggernewway/di/postcomments/PostCommentsModule.kt)).
    * *Note*: Use `@Named` or other *qualifiers* to provide arguments and `savedInstanceState` to prevent dependency conflicts.

## Usage as library

You can add `dagger2archcomponents` module as dependency via maven:

```gradle
repositories {
    maven { url 'https://dl.bintray.com/ufkoku/maven/' }
}

dependencies {
    implementation 'com.ufkoku:dagger2-arch-components:1.0.0'
}
```

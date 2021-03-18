# recent-apps-thumbnail-hiding

[![](https://jitpack.io/v/nimblehq/recent-apps-thumbnail-hiding.svg)](https://jitpack.io/#nimblehq/recent-apps-thumbnail-hiding)

Hide app thumbnail in Android Recent Apps

![20201014_171755](https://user-images.githubusercontent.com/16315358/95976377-9c20f200-0e41-11eb-99e3-bf1abf6406df.gif)

## Installation

**Step 1.** Add the JitPack repository to your build file
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
**Step 2.** Add the dependency
```groovy
dependencies {
    implementation 'com.github.nimblehq:recent-apps-thumbnail-hiding:[LATEST_VERSION]'
}
```

## Usage

- Register `RecentAppsThumbnailHidingLifecycleTracker` in Application layer
```
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(RecentAppsThumbnailHidingLifecycleTracker())
    }

}
```
- Implement [RecentAppsThumbnailHidingListener](https://github.com/nimblehq/recent-apps-thumbnail-hiding/blob/eaf27aea6ffbbacff65af23a05dd26fb698c5025/lib/src/main/java/co/nimblehq/recentapps/thumbnailhiding/RecentAppsThumbnailHidingListener.kt#L21-L30) in your activity or base activity. By default, the library uses [FLAG_SECURE](https://developer.android.com/reference/android/view/WindowManager.LayoutParams#FLAG_SECURE) to hide app thumbnail but it will produce a side-effect that sometimes that flag can't be set/unset fast enough or it blocks the user to take the screenshot.
```
class MainActivity : Activity(), RecentAppsThumbnailHidingListener {

}
```
- Or, we could override `onRecentAppsTriggered` to show your custom layout for app thumbnail in Recent Apps list

```
class MainActivity : Activity(), RecentAppsThumbnailHidingListener {

    override fun onRecentAppsTriggered(activity: Activity, inRecentAppsMode: Boolean) {
        ivRecentAppThumbnail.visibleOrGone(inRecentAppsMode)
    }

}
```

## License

This project is Copyright (c) 2014-2021 Nimble. It is free software,
and may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: /LICENSE

## About

![Nimble](https://assets.nimblehq.co/logo/dark/logo-dark-text-160.png)

This project is maintained and funded by Nimble.

We love open source and do our part in sharing our work with the community!
See [our other projects][community] or [hire our team][hire] to help build your product.

[community]: https://github.com/nimblehq
[hire]: https://nimblehq.co/

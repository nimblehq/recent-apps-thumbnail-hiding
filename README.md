# recent-apps-thumbnail-hiding

[![](https://jitpack.io/v/nimblehq/recent-apps-thumbnail-hiding.svg)](https://jitpack.io/#nimblehq/recent-apps-thumbnail-hiding)

Hide app thumbnail in [Android Recent Apps](https://developer.android.com/guide/components/activities/recents)

Since our privacy is always in question, there are some applications - especially digital banking and wallet applications - that show some sensitive information that must NOT be shown on the "Recent Tasks" screen when stopping the app by pressing the home button. Instead, they will try to show a custom app logo as an app thumbnail in Recents Screen.

![20201014_171755](https://user-images.githubusercontent.com/16315358/95976377-9c20f200-0e41-11eb-99e3-bf1abf6406df.gif)

There is no clear support from Android at the official API level, only a [FLAG_SECURE](https://developer.android.com/reference/android/view/WindowManager.LayoutParams#FLAG_SECURE) that was initially introduced to prevent DRM-protected content from appearing in screenshots, video screencaps, or from being viewed on “non-secure displays” such as Recents Screen; and this flag does not support to show custom app UI in Recents Screen, only blank.

There is an approach to archive this requirement besides using that flag is [HardwareKeyWatcher](https://github.com/nimblehq/recent-apps-thumbnail-hiding/blob/a2a640918a992c2950439dc4d5747c71a64ef682/lib/src/main/java/co/nimblehq/recentapps/thumbnailhiding/HardwareKeyWatcher.java) but it can not cover all of the cases due to fragmented device configurations and custom ROMs, then we have to fulfill missing cases with other approaches. To ease the way of implementation, we had built a lib for [Recent Apps thumbnail hiding](https://github.com/nimblehq/recent-apps-thumbnail-hiding) mechanism, which supports implement a custom layout to show an empty screen with the app's logo when the app is going to Recents Screen.

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

- Register [RecentAppsThumbnailHidingLifecycleTracker](https://github.com/nimblehq/recent-apps-thumbnail-hiding/blob/master/app/src/main/java/co/nimblehq/recentapps/thumbnailhiding/App.kt#L9)
  in Application layer.

    ```kotlin
    class MyApplication : Application() {

        override fun onCreate() {
            super.onCreate()
            registerActivityLifecycleCallbacks(RecentAppsThumbnailHidingLifecycleTracker())
        }

    }
    ```

- Implement [RecentAppsThumbnailHidingListener](https://github.com/nimblehq/recent-apps-thumbnail-hiding/blob/eaf27aea6ffbbacff65af23a05dd26fb698c5025/lib/src/main/java/co/nimblehq/recentapps/thumbnailhiding/RecentAppsThumbnailHidingListener.kt#L21-L30)
  in your activity or base activity. By default, the library
  uses [FLAG_SECURE](https://developer.android.com/reference/android/view/WindowManager.LayoutParams#FLAG_SECURE) to hide
  app thumbnail but it will produce side-effects that sometimes the flag can't be set/unset fast enough (not stable) or it
  blocks the user to take the screenshot.

    ```kotlin
    class MainActivity : Activity(), RecentAppsThumbnailHidingListener {

    }
    ```

- Or, override [onRecentAppsTriggered](https://github.com/nimblehq/recent-apps-thumbnail-hiding/blob/master/app/src/main/java/co/nimblehq/recentapps/thumbnailhiding/MainActivity.kt#L18-L23)
  to show your custom layout for app thumbnail in Recent Apps list.

    ```kotlin
    class MainActivity : Activity(), RecentAppsThumbnailHidingListener {

        override fun onRecentAppsTriggered(activity: Activity, inRecentAppsMode: Boolean) {
            ivRecentAppThumbnail.visibleOrGone(inRecentAppsMode)
        }

    }
    ```

> Checkout the custom thumbnail layout sample to see more detail [here](https://github.com/nimblehq/recent-apps-thumbnail-hiding/blob/master/app/src/main/res/layout/activity_main.xml#L26-L33)

### Exceptions handling

The core approach `HardwareKeyWatcher` in this lib does not work on all devices.
There are some failed cases listed as below:

- [Low API devices](https://github.com/nimblehq/recent-apps-thumbnail-hiding/issues/9): Android 7.1 (API 25) and lower.
- [Custom gesture navigation on some Huawei/Xiaomi devices](https://github.com/nimblehq/recent-apps-thumbnail-hiding/issues/8): EMUI or MIUI roms.

More testing insight could be found [here](https://docs.google.com/spreadsheets/d/1znmSllEYHuOhmla7EWFXYeWuv1EZQiVkB9Mibhcj52s/edit?usp=sharing).
In order to provide an option to cover the hiding app thumbnail on more and more devices,
this lib adds support to apply [FLAG_SECURE](https://developer.android.com/reference/android/view/WindowManager.LayoutParams#FLAG_SECURE).

This support is provided as an *option* and *disabled* by default, to enable it:

1. extend your activity to `RecentAppsThumbnailHidingActivity`.
2. override `enableSecureFlagOnLowApiDevices = true`, `enableSecureFlagOnCustomGestureNavigationDevices = true`, or both.

    ```kotlin
    class MainActivity : RecentAppsThumbnailHidingActivity() {

        // On devices with API 25 and lower: use FLAG_SECURE
        override val enableSecureFlagOnLowApiDevices: Boolean = true

        // On some Huawei/Xiaomi devices with custom gesture navigation: use FLAG_SECURE
        override val enableSecureFlagOnCustomGestureNavigationDevices = true

        // The rest: show custom app logo
        override fun onRecentAppsTriggered(activity: Activity, inRecentAppsMode: Boolean) {
            ivRecentAppThumbnail.visibleOrGone(inRecentAppsMode)
        }

    }
    ```

> ⚠️ Note that, besides supporting to hide app thumbnail, this flag
**will not support to show a custom Recent Apps thumbnail layout**,
also **blocks the user to capture app screenshot**.

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

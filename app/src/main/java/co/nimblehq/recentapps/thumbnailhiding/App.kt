package co.nimblehq.recentapps.thumbnailhiding

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.WindowManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(AppLifecycleTracker())
    }
}

class AppLifecycleTracker : Application.ActivityLifecycleCallbacks {

    private var hardwareKeyWatcher: HardwareKeyWatcher? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        hardwareKeyWatcher = HardwareKeyWatcher(activity).apply {
            setOnHardwareKeysPressedListenerListener(object :
                HardwareKeyWatcher.OnHardwareKeysPressedListener {
                override fun onHomePressed() {
                    activity.showOrHideAppRecentThumbnail(false)
                }

                override fun onRecentAppsPressed() {
                    activity.showOrHideAppRecentThumbnail(false)
                }
            })
            startWatch()
        }
    }

    override fun onActivityResumed(activity: Activity) {
        activity.showOrHideAppRecentThumbnail(true)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        hardwareKeyWatcher?.stopWatch()
        hardwareKeyWatcher = null
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    private fun Activity.showOrHideAppRecentThumbnail(show: Boolean) {
        if (show) {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }
}

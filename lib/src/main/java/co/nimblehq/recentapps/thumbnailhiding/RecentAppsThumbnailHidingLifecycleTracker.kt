package co.nimblehq.recentapps.thumbnailhiding

import android.app.Activity
import android.app.Application
import android.os.Bundle

class RecentAppsThumbnailHidingLifecycleTracker : Application.ActivityLifecycleCallbacks {

    private var hardwareKeyWatcher: HardwareKeyWatcher? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (hardwareKeyWatcher?.context != activity) {
            hardwareKeyWatcher?.stopWatch()
            hardwareKeyWatcher = null
        }
        hardwareKeyWatcher = HardwareKeyWatcher(activity).apply {
            setOnHardwareKeysPressedListenerListener(object :
                HardwareKeyWatcher.OnHardwareKeysPressedListener {
                override fun onHomePressed() {
                    activity.triggerRecentAppsMode(true)
                }

                override fun onRecentAppsPressed() {
                    activity.triggerRecentAppsMode(true)
                }
            })
            startWatch()
        }
    }

    override fun onActivityResumed(activity: Activity) {
        activity.triggerRecentAppsMode(false)
    }

    override fun onActivityPaused(activity: Activity) {
        /*
         * Fix hide app recent for 2 cases:
         * - pulldown notification > settings > recent
         * - messenger chathead > profile in fullscreen > recent
         * - Xiaomi accessibility button > recent
         */
        activity.triggerRecentAppsMode(true)
    }

    override fun onActivityStopped(activity: Activity) {
        if (hardwareKeyWatcher?.context == activity) {
            hardwareKeyWatcher?.stopWatch()
            hardwareKeyWatcher = null
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    private fun Activity.triggerRecentAppsMode(inRecentAppsMode: Boolean) {
        when (val activity = this) {
            is RecentAppsThumbnailHidingListener ->
                activity.onRecentAppsTriggered(
                    activity,
                    inRecentAppsMode
                )
        }
    }
}

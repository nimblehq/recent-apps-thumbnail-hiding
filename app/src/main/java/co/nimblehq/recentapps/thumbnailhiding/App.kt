package co.nimblehq.recentapps.thumbnailhiding

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(RecentAppsThumbnailHidingLifecycleTracker())
    }
}

package co.nimblehq.recentapps.thumbnailhiding.sample

import android.app.Application
import co.nimblehq.recentapps.thumbnailhiding.RecentAppsThumbnailHidingLifecycleTracker

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(RecentAppsThumbnailHidingLifecycleTracker())
    }
}

package co.nimblehq.recentapps.thumbnailhiding.sample

import android.app.Application
import android.content.Context
import co.nimblehq.recentapps.thumbnailhiding.RecentAppsThumbnailHidingLifecycleTracker

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(RecentAppsThumbnailHidingLifecycleTracker())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}

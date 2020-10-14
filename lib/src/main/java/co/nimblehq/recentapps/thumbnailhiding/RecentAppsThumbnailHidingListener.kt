package co.nimblehq.recentapps.thumbnailhiding

import android.app.Activity
import android.view.WindowManager

interface RecentAppsThumbnailHidingListener {

    /**
     * Default implementation: enable app thumbnail hiding with `FLAG_SECURE` flag
     * Override to implement custom app thumbnail hiding
     * Override with empty body or ignore interface implementation to ignore app thumbnail hiding
     */
    fun onRecentAppsTriggered(
        activity: Activity,
        inRecentAppsMode: Boolean
    ) {
        activity.showOrHideRecentAppThumbnail(inRecentAppsMode)
    }

    private fun Activity.showOrHideRecentAppThumbnail(inRecentAppsMode: Boolean) {
        if (inRecentAppsMode) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }
}

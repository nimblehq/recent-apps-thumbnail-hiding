package co.nimblehq.recentapps.thumbnailhiding

import android.app.Activity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RecentAppsThumbnailHidingActivity() {

    override val enableSecureFlagOnLowApiDevices: Boolean = true

    override val enableSecureFlagOnDevicesWithCustomGestureNavigation: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRecentAppsTriggered(
        activity: Activity,
        inRecentAppsMode: Boolean
    ) {
        ivRecentAppsLogo.visibility = if (inRecentAppsMode) VISIBLE else GONE
    }
}

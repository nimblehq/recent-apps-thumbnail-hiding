package co.nimblehq.recentapps.thumbnailhiding

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.nimblehq.recentapps.thumbnailhiding.navbar.NavigationBarObserver

abstract class RecentAppsThumbnailHidingActivity : AppCompatActivity(),
    RecentAppsThumbnailHidingListener {

    protected open val enableSecureFlagOnLowApiDevices: Boolean = false

    /**
     * HardwareKeyWatcher doesn't work on API 25 or lower,
     * allow to use FLAG_SECURE instead to hide app thumbnail.
     */
    val isSecureFlagEnabled: Boolean
        get() = enableSecureFlagOnLowApiDevices && Build.VERSION.SDK_INT < Build.VERSION_CODES.O

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSecureFlagEnabled || !NavigationBarObserver.isNavigationBarShowing(this)) {
            enableSecureFlag(true)
        }
    }
}

fun Activity.enableSecureFlag(isEnabled: Boolean) {
    if (isEnabled) {
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

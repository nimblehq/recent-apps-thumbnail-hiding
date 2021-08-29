package co.nimblehq.recentapps.thumbnailhiding

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.nimblehq.recentapps.thumbnailhiding.navbar.NavigationBarObserver.isGestureEnabled

abstract class RecentAppsThumbnailHidingActivity : AppCompatActivity(),
    RecentAppsThumbnailHidingListener {

    /**
     * HardwareKeyWatcher doesn't work on API 25 or lowers,
     * allow to use FLAG_SECURE instead to hide app thumbnail.
     */
    open val enableSecureFlagOnLowApiDevices: Boolean = false

    /**
     * HardwareKeyWatcher doesn't work on some Xiaomi or Huawei devices with custom gesture navigation enabled,
     * allow to use FLAG_SECURE instead to hide app thumbnail.
     */
    open val enableSecureFlagOnDevicesWithCustomGestureNavigation: Boolean = false

    private val isSecureFlagOnLowApiDevicesEnabled: Boolean
        get() = enableSecureFlagOnLowApiDevices && Build.VERSION.SDK_INT < Build.VERSION_CODES.O

    private val isSecureFlagOnDevicesWithCustomGestureNavigationEnabled: Boolean
        get() = enableSecureFlagOnDevicesWithCustomGestureNavigation && isGestureEnabled(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSecureFlagOnLowApiDevicesEnabled || isSecureFlagOnDevicesWithCustomGestureNavigationEnabled) {
            enableSecureFlag(true)
        }
    }
}

fun Activity.enableSecureFlag(enable: Boolean) {
    if (enable) {
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

fun Activity.isSecureFlagEnabled(): Boolean {
    val flags = window.attributes.flags
    return (flags and WindowManager.LayoutParams.FLAG_SECURE) != 0
}

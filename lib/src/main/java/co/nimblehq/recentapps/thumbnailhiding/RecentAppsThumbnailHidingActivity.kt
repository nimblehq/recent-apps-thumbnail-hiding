package co.nimblehq.recentapps.thumbnailhiding

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class RecentAppsThumbnailHidingActivity : AppCompatActivity(), RecentAppsThumbnailHidingListener {

    open val enableSecureFlagOnLowApiDevices: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (enableSecureFlagOnLowApiDevices && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            enableSecureFlag(true)
        }
    }
}

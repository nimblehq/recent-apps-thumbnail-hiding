package co.nimblehq.recentapps.thumbnailhiding

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

abstract class RecentAppsThumbnailHidingActivity : AppCompatActivity(), RecentAppsThumbnailHidingListener {

    open val enableSecureFlagOnLowApiDevices: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (enableSecureFlagOnLowApiDevices && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }
}

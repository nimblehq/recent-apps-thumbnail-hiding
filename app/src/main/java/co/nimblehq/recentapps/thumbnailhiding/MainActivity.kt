package co.nimblehq.recentapps.thumbnailhiding

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var hardwareKeyWatcher: HardwareKeyWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        hardwareKeyWatcher = HardwareKeyWatcher(this)
        hardwareKeyWatcher.setOnHardwareKeysPressedListenerListener(object :
            HardwareKeyWatcher.OnHardwareKeysPressedListener {
            override fun onHomePressed() {
                showHideAppRecentThumbnail(false)
            }

            override fun onRecentAppsPressed() {
                showHideAppRecentThumbnail(false)
            }
        })
        hardwareKeyWatcher.startWatch()
    }

    override fun onResume() {
        super.onResume()
        showHideAppRecentThumbnail(true)
    }

    override fun onStop() {
        super.onStop()
        hardwareKeyWatcher.stopWatch()
    }

    private fun showHideAppRecentThumbnail(show: Boolean) {
        if (show) {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }
}

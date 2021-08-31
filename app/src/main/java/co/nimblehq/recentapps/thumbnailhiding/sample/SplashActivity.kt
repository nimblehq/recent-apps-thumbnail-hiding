package co.nimblehq.recentapps.thumbnailhiding.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        ivRecentAppsLogo.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000L)
    }
}

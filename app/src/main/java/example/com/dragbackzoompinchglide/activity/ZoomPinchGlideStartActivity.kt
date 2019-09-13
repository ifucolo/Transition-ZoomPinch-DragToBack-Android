package example.com.dragbackzoompinchglide.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ifucolo.imagedragbackzoompinchglide.pinch.ImagePinBackStart
import example.com.dragbackzoompinchglide.R
import kotlinx.android.synthetic.main.activity_start.*

class ZoomPinchGlideStartActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, ZoomPinchGlideStartActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        ImagePinBackStart(
            activity = this,
            imageView = photoOne,
            imgUrl = getString(R.string.img_url),
            activityIntent = ZoomPinchGlideEndActivity.newIntent(this)
        )

        ImagePinBackStart(
            activity = this,
            imageView = photoTwo,
            imgUrl = getString(R.string.img_url),
            activityIntent = ZoomPinchGlideEndActivity.newIntent(this)
        )
    }
}

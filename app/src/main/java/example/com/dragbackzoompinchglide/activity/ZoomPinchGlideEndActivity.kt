package example.com.dragbackzoompinchglide.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ifucolo.imagedragbackzoompinchglide.pinch.ImagePinBackEnd
import example.com.dragbackzoompinchglide.R
import kotlinx.android.synthetic.main.activity_end.*

class ZoomPinchGlideEndActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, ZoomPinchGlideEndActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        ImagePinBackEnd(
            activity = this,
            lifecycleOwner = this,
            imageView = photo,
            frameTouch = frameTouch,
            activityIntent = intent
        )
    }

}

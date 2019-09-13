package example.com.dragback.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ifucolo.imagepinchback.normal.ImageDragBackEnd
import example.com.dragbackzoompinchglide.R
import kotlinx.android.synthetic.main.activity_zoom_pinch_glide_end.*

class DragBackEndActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, DragBackEndActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_pinch_glide_end)

        val imageDragBackEnd = ImageDragBackEnd(
            activity = this,
            lifecycleOwner = this,
            imageView = photo,
            frameTouch = frameTouch,
            activityIntent = intent
        )

        Glide.with(this)
            .load(imageDragBackEnd.imgUrl)
            .into(photo)
    }

}

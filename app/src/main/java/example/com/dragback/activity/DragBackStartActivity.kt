package example.com.dragback.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ifucolo.imagepinchback.normal.ImageDragBackStart
import example.com.dragbackzoompinchglide.R
import kotlinx.android.synthetic.main.activity_start.*

class DragBackStartActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, DragBackStartActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        Glide.with(this)
            .load(getString(R.string.img_url))
            .into(photoOne)

        Glide.with(this)
            .load(getString(R.string.img_url))
            .into(photoTwo)

        ImageDragBackStart(
            activity = this,
            imageView = photoOne,
            imgUrl = getString(R.string.img_url),
            activityIntent = DragBackEndActivity.newIntent(this)
        )

        ImageDragBackStart(
            activity = this,
            imageView = photoTwo,
            imgUrl = getString(R.string.img_url),
            activityIntent = DragBackEndActivity.newIntent(this)
        )
    }
}

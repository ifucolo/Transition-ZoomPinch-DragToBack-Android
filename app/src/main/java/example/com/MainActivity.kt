package example.com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import example.com.dragback.activity.DragBackStartActivity
import example.com.dragbackzoompinchglide.R
import example.com.dragbackzoompinchglide.activity.ZoomPinchGlideStartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPure.setOnClickListener {
            startActivity(DragBackStartActivity.newIntent(this))
        }

        btnThird.setOnClickListener {
            startActivity(ZoomPinchGlideStartActivity.newIntent(this))
        }
    }
}
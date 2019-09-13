package com.ifucolo.imagepinchback.normal

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat

class ImageDragBackStart(
    private val activity: Activity,
    private val imageView: ImageView,
    private val imgUrl: String,
    private val activityIntent: Intent,
    needImageClickListener: Boolean = true
) {

    companion object {
        const val EXTRA_IMG_URL = "img_url"
        const val transition_id = "transition"
    }

    init {
        if (needImageClickListener) {
            imageView.setOnClickListener {
                startActivityTransition()
            }
        }
    }

    /**
     * If you don't use the lib clickLister you need call this method on image click listner
     * or other button that manage the click for you
     */
    fun startActivityTransition() {
        activityIntent.putExtra(EXTRA_IMG_URL, imgUrl)

        ViewCompat.setTransitionName(imageView,
            transition_id
        )

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity,
            imageView,
            transition_id
        )

        activity.startActivity(activityIntent, options.toBundle())
    }
}
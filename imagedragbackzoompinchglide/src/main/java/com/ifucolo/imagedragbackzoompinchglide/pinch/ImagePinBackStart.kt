package com.ifucolo.imagedragbackzoompinchglide.pinch

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide

class ImagePinBackStart(
    private val activity: Activity,
    private val imageView: ImageView,
    private val imgUrl: String,
    private val activityIntent: Intent,
    private val needLoadImage: Boolean = true,
    private val needImageClickListener: Boolean = true,
    private val placeHolderId: Int = 0
) {

    companion object {
        const val EXTRA_IMG_URL = "img_url"
        const val transition_id = "transition"
    }

    init {
        if (needLoadImage)
            loadImage()

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
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView,
            transition_id
        )

        activity.startActivity(activityIntent, options.toBundle())
    }

    /**
     *
     */
    private fun loadImage() {
        Glide.with(activity)
            .load(imgUrl)
            .placeholder(placeHolderId)
            .into(imageView)
    }
}
package com.ifucolo.imagedragbackzoompinchglide.pinch

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.github.chrisbanes.photoview.PhotoViewAttacher
import com.ifucolo.imagedragbackzoompinchglide.widget.FrameTouch
import com.ifucolo.imagedragbackzoompinchglide.widget.FrameTouchEvents
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.ifucolo.imagedragbackglide.R
import com.ifucolo.imagedragbackzoompinchglide.util.calculateColorBackground
import com.ifucolo.imagedragbackzoompinchglide.util.pxToDp
import kotlin.math.sqrt


class ImagePinBackEnd(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
    val imageView: ImageView,
    private val frameTouch: FrameTouch,
    private val activityIntent: Intent,
    private val needLoadImage: Boolean = true,
    private val withZoomPinch: Boolean = true,
    private val backgroundColor: Int = Color.BLACK
) {

    lateinit var imgUrl: String
    private var photoViewAttacher: PhotoViewAttacher? = null
    private val distance: Double
        get() {
            val distance = sqrt((imageView.translationY * imageView.translationY + imageView.translationX * imageView.translationX).toDouble()).toInt()
            return pxToDp(activity, distance.toFloat()).toDouble()
        }

    init {
        startReceiveTransaction()
    }

    private fun startReceiveTransaction() {
        imgUrl = activityIntent.getStringExtra(ImagePinBackStart.EXTRA_IMG_URL)

        activity.overridePendingTransition(
            R.anim.enter_anim,
            R.anim.enter_anim
        )
        ViewCompat.setTransitionName(imageView,
            ImagePinBackStart.transition_id
        )
        supportPostponeEnterTransition()

        if (withZoomPinch)
            setupPhotoViewAttacher()

        if (needLoadImage)
            loadImage()

        frameTouch.onFrameTouchUpLiveData.observe(lifecycleOwner, Observer {
            when(it) {
                is FrameTouchEvents.FrameTouchUp -> onFrameTouchUp()
                is FrameTouchEvents.ScrollMovie -> onScrollMovie(it.x, it.y)
            }
        })
    }

    private fun loadImage() {
        Glide.with(activity)
            .load(imgUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }

            })
            .into(object : SimpleTarget<Drawable>(){
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    imageView.setImageDrawable(resource)
                    photoViewAttacher?.update()
                }
            })
    }

    private fun setupPhotoViewAttacher() {
        photoViewAttacher = PhotoViewAttacher(imageView)
        photoViewAttacher?.let {
            it.minimumScale = 0.5f
            it.maximumScale = it.maximumScale
            it.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun supportStartPostponedEnterTransition() {
        ActivityCompat.startPostponedEnterTransition(activity)
    }

    private fun supportPostponeEnterTransition() {
        ActivityCompat.postponeEnterTransition(activity)
    }

    private fun onFrameTouchUp() {
        when {
            distance > 50 -> activity.onBackPressed()
            else -> {
                val animatorSet = AnimatorSet()

                val colorAnimator = ObjectAnimator.ofInt(
                    frameTouch, "backgroundColor",
                    calculateColorBackground(distance),
                    backgroundColor
                )

                colorAnimator.setEvaluator(ArgbEvaluator())

                animatorSet.playTogether(
                    ObjectAnimator.ofFloat(imageView, "translationX", 0f),
                    ObjectAnimator.ofFloat(imageView, "translationY", 0f),
                    colorAnimator
                )

                animatorSet.start()
            }
        }
    }

    private fun onScrollMovie(x: Float, y: Float) {
        if (1 >= photoViewAttacher?.scale?: 0f) {
            frameTouch.setBackgroundColor(calculateColorBackground(distance))
            imageView.translationY = imageView.translationY - y
            imageView.translationX = imageView.translationX - x
        }
    }
}
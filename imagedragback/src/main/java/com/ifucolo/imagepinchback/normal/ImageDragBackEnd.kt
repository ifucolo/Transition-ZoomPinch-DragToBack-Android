package com.ifucolo.imagepinchback.normal

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.ifucolo.imagepinchback.widget.FrameTouch
import com.ifucolo.imagepinchback.widget.FrameTouchEvents
import com.ifucolo.imagepinchback.R
import com.ifucolo.imagepinchback.util.calculateColorBackground
import com.ifucolo.imagepinchback.util.pxToDp
import kotlin.math.sqrt


class ImageDragBackEnd(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
    val imageView: ImageView,
    private val frameTouch: FrameTouch,
    private val activityIntent: Intent,
    private val backgroundColor: Int = Color.BLACK
) {

    lateinit var imgUrl: String

    private val distance: Double
        get() {
            val distance = sqrt((imageView.translationY * imageView.translationY + imageView.translationX * imageView.translationX).toDouble()).toInt()
            return pxToDp(activity, distance.toFloat()).toDouble()
        }

    init {
        startReceiveTransaction()
    }

    private fun startReceiveTransaction() {
        imgUrl = activityIntent.getStringExtra(ImageDragBackStart.EXTRA_IMG_URL)

        activity.overridePendingTransition(
            R.anim.enter_anim,
            R.anim.enter_anim
        )

        ViewCompat.setTransitionName(
            imageView,
            ImageDragBackStart.transition_id
        )

        supportPostponeEnterTransition()
        supportStartPostponedEnterTransition()
        setupLiveData()
    }

    private fun setupLiveData() {
        frameTouch.onFrameTouchUpLiveData.observe(lifecycleOwner, Observer {
            when(it) {
                is FrameTouchEvents.FrameTouchUp -> onFrameTouchUp()
                is FrameTouchEvents.ScrollMovie -> onScrollMovie(it.x, it.y)
            }
        })
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
                    frameTouch,
                    "backgroundColor",
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
        frameTouch.setBackgroundColor(calculateColorBackground(distance))
        imageView.translationY = imageView.translationY - y
        imageView.translationX = imageView.translationX - x
    }
}
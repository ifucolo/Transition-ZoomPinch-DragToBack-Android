package com.ifucolo.imagedragbackzoompinchglide.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.FrameLayout

import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class FrameTouch(context: Context, attrs: AttributeSet?)
    : FrameLayout(context, attrs), ScaleGestureDetector.OnScaleGestureListener {

    private var detector: GestureDetectorCompat
    private var scaleDetector: ScaleGestureDetector

    private val _onFrameTouchUpLiveData = MutableLiveData<FrameTouchEvents>()
    val onFrameTouchUpLiveData: LiveData<FrameTouchEvents> = _onFrameTouchUpLiveData

    init {
        detector = GestureDetectorCompat(context,  object : GestureDetector.SimpleOnGestureListener() {

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                _onFrameTouchUpLiveData.postValue(
                    FrameTouchEvents.ScrollMovie(
                        distanceX,
                        distanceY
                    )
                )
                return super.onScroll(e1, e2, distanceX, distanceY)
            }

            override fun onDown(e: MotionEvent): Boolean = true
        })

        scaleDetector = ScaleGestureDetector(context, this)
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean = true
    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean = true
    override fun onScaleEnd(detector: ScaleGestureDetector) {}

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP)
            _onFrameTouchUpLiveData.postValue(FrameTouchEvents.FrameTouchUp)

        super.dispatchTouchEvent(event)

        return detector.onTouchEvent(event)
    }
}
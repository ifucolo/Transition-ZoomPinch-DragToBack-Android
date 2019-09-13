package com.ifucolo.imagedragbackzoompinchglide.widget

sealed class FrameTouchEvents {
    object FrameTouchUp: FrameTouchEvents()
    data class ScrollMovie(val x: Float, val y: Float): FrameTouchEvents()
}

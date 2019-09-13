package com.ifucolo.imagepinchback.util

import android.content.Context
import android.graphics.Color
import kotlin.math.roundToInt


fun pxToDp(context: Context, px: Float): Int {
    val density = context.resources.displayMetrics.density
    return (px / density).roundToInt()
}

fun calculateColorBackground(distance: Double): Int {
    return Color.argb(0.0.coerceAtLeast(255 - distance * 1.5).toInt(), 0, 0, 0)
}

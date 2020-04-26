package com.example.iplayer

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

inline fun getValueAnimator(
    forward: Boolean,
    duration: Long,
    interpolator: TimeInterpolator,
    crossinline updateListener: (progress: Float) -> Unit
): ValueAnimator {
    val va =
        if (forward) ValueAnimator.ofFloat(0f, 1f)
        else ValueAnimator.ofFloat(1f, 0f)

    va.duration = duration
    va.interpolator = interpolator
    va.addUpdateListener { updateListener(it.animatedValue as Float) }

    return va
}

fun Any.bindColor(context: Context, @ColorRes id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    ContextCompat.getColor(context, id)
}

fun Any.bindString(context: Context, @StringRes id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    context.resources.getString(id)
}

fun blendColors(colorOne: Int, colorTwo: Int, ratio: Float): Int {

    val inverseRatio = 1f - ratio

    val a = (Color.alpha(colorOne) * inverseRatio) + (Color.alpha(colorTwo) * ratio)
    val r = (Color.red(colorOne) * inverseRatio) + (Color.red(colorTwo) * ratio)
    val g = (Color.green(colorOne) * inverseRatio) + (Color.green(colorTwo) * ratio)
    val b = (Color.blue(colorOne) * inverseRatio) + (Color.blue(colorTwo) * ratio)

    return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
}
package com.example.iplayer

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.iplayer.data.Album
import java.util.*

fun Album.getReleaseDateYear(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this.releaseDate
    return calendar.get(Calendar.YEAR).toString()
}


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

fun Any.bindDimen(context: Context, @DimenRes id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    context.resources.getDimension(id)
}

fun Fragment.bindColor(@ColorRes id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    ContextCompat.getColor(context!!, id)
}

fun Fragment.bindDimen(@DimenRes id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    context!!.resources.getDimension(id)
}

fun Fragment.bindString(@StringRes id: Int) = lazy(LazyThreadSafetyMode.NONE) {
    context!!.getString(id)
}

inline val Context.screenWidth: Int
    get() = Point().also { (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(it) }.x
inline val View.screenWidth: Int
    get() = context!!.screenWidth

fun blendColors(colorOne: Int, colorTwo: Int, ratio: Float): Int {

    val inverseRatio = 1f - ratio

    val a = (Color.alpha(colorOne) * inverseRatio) + (Color.alpha(colorTwo) * ratio)
    val r = (Color.red(colorOne) * inverseRatio) + (Color.red(colorTwo) * ratio)
    val g = (Color.green(colorOne) * inverseRatio) + (Color.green(colorTwo) * ratio)
    val b = (Color.blue(colorOne) * inverseRatio) + (Color.blue(colorTwo) * ratio)

    return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
}
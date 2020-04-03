package com.developersancho.aytar.ktx

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun Context.convertDpToPixel(dimension: Int): Int {
    return resources.getDimensionPixelSize(dimension)
}

@ColorInt
fun Context.getCompatColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}


fun Context.getCircularProgressDrawable(): CircularProgressDrawable {
    val progressDrawable = CircularProgressDrawable(this)
    progressDrawable.strokeWidth = 10f
    progressDrawable.centerRadius = 50f
    progressDrawable.start()
    return progressDrawable
}
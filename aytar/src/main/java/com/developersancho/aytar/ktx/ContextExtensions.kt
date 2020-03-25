package com.developersancho.aytar.ktx

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.convertDpToPixel(dimension: Int): Int {
    return resources.getDimensionPixelSize(dimension)
}

@ColorInt
fun Context.getCompatColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}
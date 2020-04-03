package com.developersancho.aytar.ktx

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(url: String, progressDrawable: CircularProgressDrawable) {
    val options: RequestOptions = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_round)

    Glide.with(this)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}



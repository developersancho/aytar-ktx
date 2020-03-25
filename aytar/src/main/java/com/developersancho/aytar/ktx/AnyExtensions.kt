package com.developersancho.aytar.ktx

import com.google.gson.GsonBuilder

inline fun <reified T : Any> Any.mapTo(): T? =
    GsonBuilder().create().run {
        try {
            toJson(this@mapTo)?.let { fromJson(it, T::class.java) }
        } catch (e: Exception) {
            null
        }
    }
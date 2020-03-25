package com.developersancho.aytar.ktx

fun String?.safe(): String {
    return this ?: ""
}

fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()
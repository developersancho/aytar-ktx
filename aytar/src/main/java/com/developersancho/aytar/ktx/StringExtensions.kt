package com.developersancho.aytar.ktx

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String?.safe(): String {
    return this ?: ""
}

fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()

fun CharSequence?.isValidText(): Boolean {
    return this != null && this.isNotEmpty() && this.toString() != "" && this.toString() != " "
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun String?.isValidEmail(): Boolean {
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String?.isValidPhoneNumber(): Boolean {
    return if (this!!.length == 10) {
        true
    } else Patterns.PHONE.matcher(this).matches()
}
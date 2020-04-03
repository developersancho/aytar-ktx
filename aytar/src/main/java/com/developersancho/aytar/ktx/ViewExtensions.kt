package com.developersancho.aytar.ktx

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.snackbar.Snackbar

private const val START_DELAY = 300L
private const val DURATION = 2000L
private val INTERPOLATOR_IN = LinearOutSlowInInterpolator()


class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)

    }
}

fun View.safeClickListener2(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.alpha = 0f
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.alpha = 1f
                v.invalidate()


            }
        }
        false
    }
    setOnClickListener(safeClickListener)
}

fun View.safeClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}


fun View.fadeIn() {
    fade(value = 1f)
}

fun View.fadeOut() {
    fade(value = 0f)
}

fun View.fade(
    value: Float,
    startDelay: Long = START_DELAY,
    duration: Long = DURATION,
    onAnimationStart: () -> Unit = {},
    onAnimationEnd: () -> Unit = {}
) {
    animate()
        .setStartDelay(startDelay)
        .alpha(value)
        .setDuration(duration)
        .setInterpolator(INTERPOLATOR_IN).withLayer()
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator?) {
                onAnimationEnd()
            }
        })
}

fun View.showSnackbar(msgId: Int, length: Int) {
    showSnackbar(context.getString(msgId), length)
}

fun View.showSnackbar(msg: String, length: Int) {
    showSnackbar(msg, length, null) {}
}

fun View.showSnackbar(
    msgId: Int,
    length: Int,
    actionMessageId: Int,
    action: (View) -> Unit
) {
    showSnackbar(context.getString(msgId), length, context.getString(actionMessageId), action)
}

fun View.showSnackbar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }
    }
    snackbar.show()
}


fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm =
            context?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideSoftKeyboard() {
    run {
        val imm =
            this.context.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.showOrGone(isShow: Boolean) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}
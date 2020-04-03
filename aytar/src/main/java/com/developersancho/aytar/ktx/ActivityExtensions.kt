package com.developersancho.aytar.ktx

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import java.util.concurrent.TimeUnit

fun <T : ViewDataBinding> FragmentActivity.dataBinding(): Lazy<T> = object : Lazy<T> {
    private var binding: T? = null

    override fun isInitialized(): Boolean = binding != null

    override val value: T
        get() = binding ?: bind<T>(getContentView()).also {
            it.lifecycleOwner = this@dataBinding
            binding = it
        }

    private fun FragmentActivity.getContentView(): View {
        return checkNotNull(findViewById<ViewGroup>(R.id.content).getChildAt(0)) {
            "Call setContentView or Use Activity's secondary constructor passing layout res id."
        }
    }

    private fun <T : ViewDataBinding> bind(view: View): T = DataBindingUtil.bind(view)!!
}


fun <T : ViewBinding> FragmentActivity.viewBinding(bind: (View) -> T): Lazy<T> = object : Lazy<T> {
    private var binding: T? = null

    override fun isInitialized(): Boolean = binding != null

    override val value: T
        get() = binding ?: bind(getContentView()).also {
            binding = it
        }

    private fun FragmentActivity.getContentView(): View {
        return checkNotNull(findViewById<ViewGroup>(R.id.content).getChildAt(0)) {
            "Call setContentView or Use Activity's secondary constructor passing layout res id."
        }
    }
}


fun Activity.checkPermission(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission)

fun Activity.shouldRequestPermissionRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)


fun Activity.requestAllPermissions(
    permissionsArray: Array<String>,
    requestCode: Int
) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}


fun Activity.runDelayed(delay: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, f: () -> Unit) {
    Handler().postDelayed({
        if (!isFinishing) {
            f.invoke()
        }
    }, timeUnit.toMillis(delay))
}

fun Activity.recreate(animate: Boolean = true) {
    if (animate) {
        val restartIntent = Intent(this, this::class.java)

        val extras = intent.extras
        if (extras != null) {
            restartIntent.putExtras(extras)
        }
        ActivityCompat.startActivity(
            this, restartIntent,
            ActivityOptionsCompat
                .makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
                .toBundle()
        )
        finish()
    } else {
        recreate()
    }
}
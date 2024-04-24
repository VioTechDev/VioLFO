package com.vio

import android.os.Build
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

abstract class BaseFLO : AppCompatActivity() {
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        hideNavigationBar(window)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideNavigationBar(window)
    }

    private fun hideNavigationBar(window: Window?) {
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowInsetController =
                    ViewCompat.getWindowInsetsController(window.decorView)
                if (windowInsetController != null) {
                    windowInsetController.systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    windowInsetController.hide(WindowInsetsCompat.Type.navigationBars())
                    window.setDecorFitsSystemWindows(true)
                }
            } else {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        }
    }
}

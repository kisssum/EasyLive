package com.kisssum.bianqian3

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initStatusBar()
        resotreTheme()
        changeStatusBarTextColor()
    }

    private fun initStatusBar() {
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun resotreTheme() {
        val isDrakTheme = getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("isDarkTheme", false)

        when (isDrakTheme) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun changeStatusBarTextColor() {
        when (isDarkTheme(this)) {
            true -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            else -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }
}
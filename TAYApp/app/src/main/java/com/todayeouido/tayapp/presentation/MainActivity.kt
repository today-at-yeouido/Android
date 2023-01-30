package com.todayeouido.tayapp.presentation

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)

        val density = resources.displayMetrics.density

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = this.windowManager.currentWindowMetrics.bounds

            // statusBar 포함 높이
            displayHeight = (display.height() / density).dp
            displayWidth = (display.width() / density).dp
        } else {
            val outMetrics = DisplayMetrics()

            @Suppress("DEPRECATION")
            val display = this.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)

            displayHeight = (outMetrics.heightPixels / density).dp
            displayWidth = (outMetrics.widthPixels / density).dp
        }

        setContent {
            TayApp()
        }
    }

    companion object {
        var displayHeight: Dp = 0.dp
        var displayWidth: Dp = 0.dp
    }
}

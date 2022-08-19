package com.example.tayapp.presentation.components

import android.app.ActionBar
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.Size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun GradientCompponent(modifier: Modifier = Modifier){
    GradientView(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = intArrayOf(
            Color.Transparent.toArgb(),
            (TayAppTheme.colors.background).toArgb(),
        )
    )
}


@Composable
fun GradientView(
    modifier: Modifier = Modifier,
    @Size(value = 2) colors: IntArray,
    visible: Boolean = true,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val gradientBackground = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
            ).apply {
                cornerRadius = 0f
            }
            View(context).apply {
                layoutParams = ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT
                )
                background = gradientBackground
                visibility = when (visible) {
                    true -> View.VISIBLE
                    else -> View.INVISIBLE
                }
            }
        }
    )
}
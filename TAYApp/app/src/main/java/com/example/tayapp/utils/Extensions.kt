package com.example.tayapp.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.MainActivity

@Composable
fun getActivity() = LocalContext.current as ComponentActivity

private fun Int.textDp(density: Density): TextUnit = with(density) {
    this@textDp.dp.toSp()
}

val Int.textDp: TextUnit
    @Composable get() =  this.textDp(density = LocalDensity.current)

private fun Double.textDp(density: Density): TextUnit = with(density) {
    this@textDp.dp.toSp()
}

val Double.textDp: TextUnit
    @Composable get() =  this.textDp(density = LocalDensity.current)

private fun Int.matchWidth(): Dp {
    return this@matchWidth.dp * (MainActivity.displayWidth / 360.dp)
}

val Int.matchWidth: Dp
    @Composable get() =  this.matchWidth()


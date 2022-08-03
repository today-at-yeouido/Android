package com.example.tayapp.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

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


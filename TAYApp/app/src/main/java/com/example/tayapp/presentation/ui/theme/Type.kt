package com.example.tayapp.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R

private fun Int.textDp(density: Density): TextUnit = with(density) {
    this@textDp.dp.toSp()
}

val Int.textDp: TextUnit
    @Composable get() =  this.textDp(density = LocalDensity.current)

private val SpoqaHanSansNeo = FontFamily(
    Font(R.font.spoqahansansneo_regular, FontWeight.Normal),
    Font(R.font.spoqahansansneo_bold, FontWeight.Bold),
    Font(R.font.spoqahansansneo_light, FontWeight.Light),
    Font(R.font.spoqahansansneo_medium, FontWeight.Medium),
    Font(R.font.spoqahansansneo_thin, FontWeight.Thin),
)


val TayTypography = Typography(
    defaultFontFamily = SpoqaHanSansNeo,

    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = (-0.3).sp,
        lineHeight = (22.4).sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = (-0.3).sp,
        lineHeight = (22.4).sp
    ),

    subtitle1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        letterSpacing = (-0.3).sp,
        lineHeight = (22.4).sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = (-0.2).sp,
        lineHeight = (26.4).sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
        lineHeight = (26.4).sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        lineHeight = 26.sp
    )

)
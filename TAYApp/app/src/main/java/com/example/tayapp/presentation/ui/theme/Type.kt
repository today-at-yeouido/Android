package com.example.tayapp.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R

private val SpoqaHanSansNeo = FontFamily(
    Font(R.font.spoqahansansneo_regular, FontWeight.Normal),
    Font(R.font.spoqahansansneo_bold, FontWeight.Bold),
    Font(R.font.spoqahansansneo_light, FontWeight.Light),
    Font(R.font.spoqahansansneo_medium, FontWeight.Medium),
    Font(R.font.spoqahansansneo_thin, FontWeight.Thin),
)


val Typography = Typography(
    defaultFontFamily = SpoqaHanSansNeo,

    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        letterSpacing = (-0.4).sp,
        lineHeight = 22.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 27.sp,
        letterSpacing = (-0.4).sp,
        lineHeight = 22.sp
    ),

    subtitle1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = (-0.4).sp,
        lineHeight = 22.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp,
        letterSpacing = (-0.267).sp,
        lineHeight = 26.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 19.sp,
        letterSpacing = 0.sp,
        lineHeight = 26.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        lineHeight = 26.sp
    )

)
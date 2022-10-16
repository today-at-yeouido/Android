package com.todayeouido.tayapp.presentation.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.presentation.ui.theme.TayTypography

@Composable
fun Title(
    string: String,
    modifier: Modifier = Modifier
) {
    Text(
        "$string", style = TayTypography.h3,
        modifier = modifier,
        color = TayAppTheme.colors.headText
    )
}
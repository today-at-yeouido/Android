package com.example.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun TayTag(
    string: String = "전체",
    isClicked: Boolean = false,
    onStringClick:(String)->Unit = {},
    onClick: () -> Unit ={}
){
    Box(
        modifier = Modifier
            .background(
                color = if (isClicked) TayAppTheme.colors.icon
                else TayAppTheme.colors.background,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = TayAppTheme.colors.border,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
            .clickable( enabled = !isClicked ) { onStringClick(string); onClick() }

    ) {
        Text(
            text = "$string",
            color = if (isClicked) TayAppTheme.colors.background
            else TayAppTheme.colors.bodyText
        )
    }
}

package com.todayeouido.tayapp.presentation.components

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme


@Composable
fun TayDivider(modifier: Modifier = Modifier){
    Divider(
        modifier = modifier,
        color = TayAppTheme.colors.layer3,
        thickness = 1.dp
    )
}

@Preview
@Composable
fun DividerPreview(){
    TayAppTheme {
        TayDivider()
    }
}
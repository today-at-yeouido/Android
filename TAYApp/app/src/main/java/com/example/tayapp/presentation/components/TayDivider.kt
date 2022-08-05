package com.example.tayapp.presentation.components

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray100


@Composable
fun TayDivider(modifier: Modifier = Modifier){
    Divider(
        modifier = modifier,
        color = lm_gray100,
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
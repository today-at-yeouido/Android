package com.example.tayapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayTypography

@Composable
fun Title(string: String) {
    Text(
        "$string", style = TayTypography.h3,
        modifier = Modifier.padding(
            horizontal = KeyLine,
            vertical = 7.dp
        )
    )
}
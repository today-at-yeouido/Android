package com.example.tayapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun TayCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    color: Color = TayAppTheme.colors.background,
    borderStroke: BorderStroke = BorderStroke(1.dp, TayAppTheme.colors.border),
    enable: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
){
    Surface(
        modifier = modifier.clickable(onClick = onClick, enabled = enable),
        color = color,
        shape = shape,
        border = borderStroke,
        content = content
    )

}

@Preview
@Composable
private fun CardPreview(){
    TayAppTheme(){
        TayCard() {
            Text(text = "test")
        }
    }
}

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.TayAppTheme

private object uiValue  {
    val borderWidth = 1.dp
    val verticalPadding = 2.dp
    val horizontalPadding = 5.dp
}


@Composable
fun LabelBill(
    string: String,
    color: Color = TayAppTheme.colors.background
){
    LabelStatus(
        string = string,
        color = color,
        textColor = TayAppTheme.colors.bodyText,
        isBorder = true
    )
}


@Composable
fun LabelStatus(
    string: String = "전체",
    color: Color = TayAppTheme.colors.background,
    onClick:(String)->Unit = {},
    textColor: Color = TayAppTheme.colors.bodyText,
    isBorder: Boolean = false
){
    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(100.dp)
            )
            .border(
                width = if (isBorder) uiValue.borderWidth else 0.dp,
                color = TayAppTheme.colors.border,
                shape = RoundedCornerShape(100.dp),
                )
            .padding(
                vertical = uiValue.verticalPadding,
                horizontal = uiValue.horizontalPadding
            )
            .clickable { onClick(string) }

    ) {
        Text(
            text = "$string",
            color = textColor,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}


@Preview
@Composable
fun PreveiwPillLabel(){
    TayAppTheme() {
        LabelStatus(
            string = "심사",
            color = TayAppTheme.colors.caution
        )
    }
}

@Preview
@Composable
fun PreveiwBill() {
    TayAppTheme() {
        LabelBill(
            string = "개정안"
        )
    }
}
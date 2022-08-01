package com.example.tayapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray000
import com.example.tayapp.presentation.ui.theme.lm_gray800

val ButtonSmallWidth = 140.dp
val ButtonSmallHeight = 40.dp

val ButtonMediumWidth = 160.dp
val ButtonMediumHeight = 40.dp

val ButtonLargeWidth = 328.dp
val ButtonLargeHeight = 44.dp

val ButtonFullHeight = 44.dp


@Composable
fun TayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    border: BorderStroke? = null,
    backgroundColor: Color = TayAppTheme.colors.defaultButton,
    contentColor: Color = lm_gray000,
    contentPadding: PaddingValues = PaddingValues(vertical = 6.dp, horizontal = 8.dp),
    content: @Composable RowScope. () -> Unit
){
    Surface(
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        border = border,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                enabled = enabled
            )

    ) {
        Row (
            Modifier
                .defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = ButtonDefaults.MinHeight
                )
                .padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Preview
@Composable
fun PreviewTayButton(){
    TayAppTheme() {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TayButton(onClick = { /*TODO*/ }) {
                Text("버튼", style = TayAppTheme.typo.typography.button)
            }

            TayButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(ButtonSmallWidth)
                    .height(ButtonSmallHeight)
            ) {
                Text("버튼", style = TayAppTheme.typo.typography.button)
            }

            TayButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(ButtonMediumWidth)
                    .height(ButtonMediumHeight)
            ) {
                Text("버튼", style = TayAppTheme.typo.typography.button)
            }


            TayButton(
                onClick = { /*TODO*/ },
                backgroundColor = lm_gray800
            ) {
                Text("버튼", style = TayAppTheme.typo.typography.button)
            }

        }
    }
}

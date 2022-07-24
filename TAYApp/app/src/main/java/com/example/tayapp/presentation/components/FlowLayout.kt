package com.example.tayapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FlowLayout(
    modifier: Modifier = Modifier,
    verticalContentPadding: Dp = 0.dp,
    horizontalContentPadding: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->


        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {

            var xPosition = 0
            var yPosition = 0

            placeables.forEach { placeable ->

                if(xPosition + placeable.width > constraints.maxWidth) {
                    xPosition = 0
                    yPosition += (placeable.height + verticalContentPadding.roundToPx())
                }

                placeable.placeRelative(x = xPosition, y = yPosition)
                xPosition += (placeable.width + horizontalContentPadding.roundToPx() )
            }
        }
    }
}
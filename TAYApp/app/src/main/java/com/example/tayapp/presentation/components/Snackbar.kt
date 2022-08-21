package com.example.tayapp.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.TayAppTheme


@Composable
fun TaySnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = RoundedCornerShape(10.dp),
    backgroundColor: Color = TayAppTheme.colors.bodyText,
    contentColor: Color = TayAppTheme.colors.danger2,
    imageVector: ImageVector,
    imageColor: Color
) {
    val actionLabel = snackbarData.actionLabel
    val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
        @Composable {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { snackbarData.performAction() }),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = imageColor
                )
                Text(
                    text = actionLabel,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = TayAppTheme.colors.background
                )
            }
        }
    } else {
        null
    }
    Snackbar(
        modifier = modifier.padding(12.dp).height(36.dp),
        content = { Text(snackbarData.message) },
        action = actionComposable,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = 0.dp
    )
}


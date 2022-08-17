package com.example.tayapp.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.utils.dashedBorder

/**
 * font sp -> dp 팔요
 */
@Composable
fun PillList(
    bill: String = "제정안",
    status: String = "접수"
) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Pill(bill)
        Pill(status)
    }
}

@Composable
fun Pill(
    text: String,
    isPressed: Boolean = false,
) {

    val fontSize = 12.sp

    when (text) {
        "제정안", "개정안", "일부개정안", "폐지안" -> {
            Pill(
                textColor = TayAppTheme.colors.subduedText,
                backgroundColor = TayAppTheme.colors.background,
                border = BorderStroke(1.dp, TayAppTheme.colors.layer3)
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "접수" -> {
            Pill(
                textColor = TayAppTheme.colors.bodyText,
                backgroundColor = TayAppTheme.colors.layer3
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "심사", "심의", "정부이송" -> {
            Pill(
                textColor = TayAppTheme.colors.bodyText,
                backgroundColor = TayAppTheme.colors.caution1
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "가결", "공포" -> {
            Pill(
                textColor = lm_gray700,
                backgroundColor = TayAppTheme.colors.success1
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "대안", "부결", "철회", "폐기" -> {
            Pill(
                textColor = lm_gray000,
                backgroundColor = TayAppTheme.colors.danger1
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
    }
}


@Composable
fun DashPill(
    text: String,

    ) {
    val fontSize = 14.sp

    when (text) {
        "접수" -> {
            DashPill(
                textColor = TayAppTheme.colors.subduedText,
                dashedColor = TayAppTheme.colors.layer3,
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "심사", "심의", "정부이송" -> {
            DashPill(
                textColor = TayAppTheme.colors.subduedText,
                dashedColor = TayAppTheme.colors.caution1,
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "가결", "공포" -> {
            DashPill(
                textColor = TayAppTheme.colors.subduedText,
                dashedColor = TayAppTheme.colors.success1,
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "대안", "부결", "철회", "폐기" -> {
            DashPill(
                textColor = TayAppTheme.colors.subduedText,
                dashedColor = TayAppTheme.colors.danger1,
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
    }
}

@Composable
fun DashPill(
    shape: RoundedCornerShape = RoundedCornerShape(50),
    textColor: Color = TayAppTheme.colors.bodyText,
    dashedColor: Color = TayAppTheme.colors.background,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 1.dp, horizontal = 6.dp),
    content: @Composable RowScope. () -> Unit,
) {
    Surface(
        shape = shape,
        color = TayAppTheme.colors.background,
        contentColor = textColor,
        border = border,
        modifier = Modifier
            .clip(shape)
            .dashedBorder(1.5.dp, dashedColor, shape, 3.dp, 3.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            content = content
        )
    }
}

@Composable
fun BadgePill(
    text: String,
    color: Color = TayAppTheme.colors.primary30
) {
    Pill(
        textColor = TayAppTheme.colors.headText,
        backgroundColor = color,
        shape = RoundedCornerShape(5.dp)
    ) {
        Text("$text", fontSize = 12.sp, fontWeight = FontWeight.Normal)
    }
}

@Composable
private fun Pill(
    shape: RoundedCornerShape = RoundedCornerShape(50),
    textColor: Color = TayAppTheme.colors.bodyText,
    backgroundColor: Color = TayAppTheme.colors.background,
    animationColor: Color = TayAppTheme.colors.danger2,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 1.dp, horizontal = 6.dp),
    content: @Composable RowScope. () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color by animateColorAsState(if (isPressed) animationColor else backgroundColor)

    Surface(
        shape = shape,
        color = color,
        contentColor = textColor,
        border = border,
        modifier = Modifier
            .clip(shape)
    ) {
        Row(
            modifier = Modifier
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            content = content
        )
    }
}


@Preview
@Composable
fun PreveiwPillLabel() {
    TayAppTheme() {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Pill(
                "가결",
                isPressed = false
            )

            Pill(
                "심사",
                isPressed = true
            )

            PillList("제정안", "가결")
        }
    }
}

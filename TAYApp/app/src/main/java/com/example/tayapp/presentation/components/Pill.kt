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
                textColor = lm_gray600,
                backgroundColor = lm_gray000,
                border = BorderStroke(1.dp, lm_gray100)
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "접수" -> {
            Pill(
                textColor = lm_gray700,
                backgroundColor = if (isPressed) lm_gray700 else lm_gray600
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "심사", "심의" -> {
            Pill(
                textColor = lm_gray700,
                backgroundColor = if (isPressed) dm_sementic_yellow2 else lm_sementic_yellow2
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "가결", "정부이송", "공포", "대안" -> {
            Pill(
                textColor = lm_gray700,
                backgroundColor = if (isPressed) dm_sementic_green2 else dm_sementic_green2
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "부결", "철회", "폐기" -> {
            Pill(
                textColor = lm_gray000,
                backgroundColor = if (isPressed) dm_sementic_red2 else lm_sementic_red2
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
                textColor = lm_gray600,
                dashedColor = lm_gray100,
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "심사", "심의", "정부이송" -> {
            DashPill(
                textColor = lm_gray600,
                dashedColor = lm_semantic_yellow1,
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "가결", "공포" -> {
            DashPill(
                textColor = lm_gray600,
                dashedColor = lm_sememtic_green1,
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
        "대안", "부결", "철회", "폐기" -> {
            DashPill(
                textColor = lm_gray600,
                dashedColor = lm_sementic_red1,
            ) {
                Text("$text", fontSize = fontSize, fontWeight = FontWeight.Normal)
            }
        }
    }
}

@Composable
fun DashPill(
    shape: RoundedCornerShape = RoundedCornerShape(50),
    onClick: () -> Unit = {},
    textColor: Color = TayAppTheme.colors.bodyText,
    dashedColor: Color = TayAppTheme.colors.background,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 1.dp, horizontal = 6.dp),
    content: @Composable RowScope. () -> Unit,
) {
    Surface(
        shape = shape,
        color = lm_gray000,
        contentColor = textColor,
        border = border,
        modifier = Modifier
            .clip(shape)
            .dashedBorder(1.5.dp, dashedColor, shape, 3.dp, 3.dp)
            .clickable {
                onClick()
            }
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
    color: Color = lm_primary30
) {
    Pill(
        textColor = lm_gray800,
        backgroundColor = color,
        shape = RoundedCornerShape(5.dp)
    ) {
        Text("$text", fontSize = 12.sp, fontWeight = FontWeight.Normal)
    }
}

@Composable
private fun Pill(
    shape: RoundedCornerShape = RoundedCornerShape(50),
    onClick: () -> Unit = {},
    textColor: Color = TayAppTheme.colors.bodyText,
    backgroundColor: Color = TayAppTheme.colors.background,
    animationColor: Color = Color.Red,
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
            .clickable {
                onClick()
            }
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

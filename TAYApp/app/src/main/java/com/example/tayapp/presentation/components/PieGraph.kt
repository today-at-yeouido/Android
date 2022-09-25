package com.example.tayapp.presentation.components

import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.*
import java.lang.Math.round
import kotlin.math.roundToInt


/** 찬성 수, 반대 수, 기권 수, 투표 수 */
@Composable
fun PieGraph(
    points: List<Int?> = listOf(200, 50, 30, 280),
    colors: List<Color> = listOf(
        TayAppTheme.colors.success1,
        TayAppTheme.colors.danger1,
        TayAppTheme.colors.caution1
    )
) {
    //전체 인원 수
    val total = points[3]!!.toFloat()

    //찬성 수, 반대 수, 기권 수 -> 비율로 변환
    val proportions = points.subList(0, 3).mapIndexed { index, i ->
        i!!.toFloat() / total
    }

    //찬성 비율 소수점
    val percent = (proportions[0].toDouble() * 1000.0).roundToInt() / 10.0

    //애니메이션 플레이가 되었었나..? -> 체크 해봐야 할듯
    var animationPlayed by remember{ mutableStateOf(false) }

    //플레이가 한번 되었다..?
    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    //모든 비율의 합
    var angleSum: Float= 1f

    /**
     *     1. 맨 밑에 깔려야 하는 그림이 제일 먼저 그려져야 해서 reversed로 뒤집음
     *     2. 모든 원이 동시에 그려진다.
     *     찬성 수 : 찬성 수 비율
     *     반대 수 : 반대 + 찬성 비율
     *     기권 : 전체
     *     만큼 그려진다. -> delay를 사용하지 않아도 되고, 비율만큼 시간을 조정하지 않아도 된다.
     */
    
    Box() {
        proportions.reversed().forEachIndexed{ index, item ->
            DrawCircle(color = colors[2-index], sweepAngle = angleSum, animationPlayed = animationPlayed)
            angleSum -= item
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text =  percent.toString() +" %",
            fontSize = 24.sp,
            color = TayAppTheme.colors.bodyText
        )
    }
}

@Composable
private fun DrawCircle(
    color: Color, sweepAngle: Float, animationPlayed: Boolean
) {
    //애니매이션이 현재 얼마나 그려졌는지에 대한 State
    //targetValue 만큼 그려지고 durationMillis 동안 그려진다.
    val curPercentage by animateFloatAsState(
        targetValue = if(animationPlayed) sweepAngle else 0f,
        animationSpec = tween(
            durationMillis = (2800 * sweepAngle).toInt(),
            delayMillis = 0
        )
    )

    Canvas(
        modifier = Modifier
            .size(150.dp)
    ) {
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360 * curPercentage,
            useCenter = false,
            style = Stroke( width = 50f, cap = StrokeCap.Round)
        )
    }
}

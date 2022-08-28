package com.example.tayapp.presentation.components

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp


/** 찬성 수, 반대 수, 기권 수, 투표 수 */
@Composable
fun PieGraph(
    points: List<Int?> = listOf(200, 50, 30, 280),
    colors: List<Color> = listOf(Color.Red, Color.Blue, Color.Green, Color.LightGray)
) {

    Log.d("찬반기투", "${points[0]}  ${points[1]}  ${points[2]}  ${points[3]}")

    val total = points[3]!!.toFloat()

    val proportions = points.mapIndexed { index, i ->
        if (index == points.size - 1) {
            ((total - i!!) * 100 / total)
        } else {
            (i!! * 100 / total)
        }
    }

    val sweepAnglePercentage = proportions.map {
        360 * it / 100
    }

    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 70f
        color = Color.Black.toArgb()
    }

    Canvas(
        modifier = Modifier
            .size(150.dp)
            .background(Color.White)
    ) {
        // 시작 각도
        var startAngle = 100f

        sweepAnglePercentage.forEachIndexed { index, sweepAngle ->
            drawArc(
                color = colors[index], startAngle = startAngle, sweepAngle = sweepAngle
            )
            startAngle += sweepAngle
        }
        drawContext.canvas.nativeCanvas.drawText(
            "${proportions[0].toInt()}%", center.x, center.y + 25f, paint
        )
    }
}


private fun DrawScope.drawArc(
    color: Color, startAngle: Float, sweepAngle: Float
) {

    val padding = 100f

    drawArc(
        color = color,
        // 시작 각도
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(width = size.width - padding, height = size.width - padding),
        style = Stroke(
            width = 50f
        ),
        topLeft = Offset(padding / 2f, padding / 2f)
    )
}
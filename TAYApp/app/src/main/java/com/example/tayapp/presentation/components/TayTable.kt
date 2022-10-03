package com.example.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.TayApp
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray100
import com.example.tayapp.presentation.ui.theme.lm_gray600
import com.example.tayapp.presentation.ui.theme.lm_gray700

/**
 * row: 행의 개수
 * column: 열의 개수
 * data: 테이블에 들어가는 전체 데이터
 */
@Composable
fun TayTable(row: Int, col: Int, data: List<String>){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(lm_gray100),
    ) {
        for(i in 0..row){
            //한 행씩 분리해서 Row생성
            TayTableRow(data = data.subList(i*(col+1), (i+1)*(col+1)))
        }
    }
}

@Composable
private fun TayTableRow(data: List<String>){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .border(1.dp, lm_gray700, RectangleShape)){
        //열 간격 조정을 위한 변수(수정예정)
        val weight = (1.0/data.size.toFloat()).toFloat()
        data.forEach {
            Text(
                text = it,
                modifier = Modifier
                    .weight(weight,true)
            )
            Divider(
                color = lm_gray700,
                modifier = Modifier.fillMaxHeight().width(1.dp)
            )
        }
    }
}

@Composable
@Preview
fun TayTablePreview(){
    TayAppTheme() {
        TayTable(data = listOf("112983471098234098123490","2","3","4asdfadfassdfgsergsergserges","5","6","segrsesgeg7","8","9"), row = 2, col = 2)
    }
}
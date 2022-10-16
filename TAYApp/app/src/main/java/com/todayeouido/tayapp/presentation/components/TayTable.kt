package com.todayeouido.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todayeouido.tayapp.domain.use_case.TableRow
import com.todayeouido.tayapp.presentation.TayApp
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.presentation.ui.theme.lm_gray100
import com.todayeouido.tayapp.presentation.ui.theme.lm_gray600
import com.todayeouido.tayapp.presentation.ui.theme.lm_gray700

/**
 * row: 행의 개수
 * column: 열의 개수
 * data: 테이블에 들어가는 전체 데이터
 */
@Composable
fun TayTable(table: TableRow){


    Column(
        modifier = Modifier
            .background(TayAppTheme.colors.information1)
            .padding(6.dp)
            .fillMaxWidth()
            ,
    ) {
        for(i in 0..table.row){
            //한 행씩 분리해서 Row생성
            TayTableRow(
                data = table.data.subList(i*(table.col+1), (i+1)*(table.col+1)),
                if(i == 0) TayAppTheme.colors.layer1 else TayAppTheme.colors.background
            )
        }
    }
}

@Composable
private fun TayTableRow(
    data: List<String>,
    backgroundColor: Color = TayAppTheme.colors.background
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .background(backgroundColor)
        .border(1.dp, TayAppTheme.colors.border, RectangleShape)
    )
    {
        //열 간격 조정을 위한 변수(수정예정)
        val weight = (1.0/data.size.toFloat()).toFloat()
        data.forEach {
            Text(
                text = it,
                modifier = Modifier
                    .weight(weight,true)
                    .padding(7.dp),
                textAlign = TextAlign.Center
            )
            Divider(
                color = TayAppTheme.colors.border,
                modifier = Modifier.fillMaxHeight().width(1.dp)
            )
        }
    }
}

@Composable
@Preview
fun TayTablePreview(){
    TayAppTheme() {
//        TayTable(data = listOf("112983471098234098123490","2","3","4asdfadfassdfgsergsergserges","5","6","segrsesgeg7","8","9"), row = 2, col = 2)
    }
}
package com.example.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.domain.model.Bill
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons

data class Progress(
    val tag: String,
    val detail: String
)

val ProgressList: List<Progress> = listOf(
    Progress("접수", "국회의원(10인 이상), 위원회, 정부에서 제안/제출해요"),
    Progress("심사", "담당 상임위에서 검토, 토론 후 의결해요"),
    Progress("심의", "법제사법위에 보내져 형식이 맞는지 검토해요 (체계자구심사)"),
    Progress("가결", "본회의에서 의결한 결과, 과반수 찬성으로 통과됐어요"),
    Progress("정부이송", "15일 이내 대통령이 이의가 없다면 법률로 확정돼요."),
    Progress("공포", "특별한 규정이 없으면 공포한 날로부터 20일 경과 후 효력이 생겨요")
)

@Composable
fun BillProgressDetail(){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(horizontal = KeyLine, vertical = 34.dp).navigationBarsPadding()
    ){
        item{
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = lm_gray075)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = TayIcons.help,
                    contentDescription = "null",
                    tint = lm_sementic_blue2,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "진행현황",
                    fontSize = 16.sp,
                    color = lm_gray800,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        itemsIndexed(ProgressList){index, item ->
            if(index != 0){
                TayDivider()
                Spacer(modifier = Modifier.size(20.dp))
            }

            BillProgressDetailItem(progress = item)
        }

    }
}

@Composable
private fun BillProgressDetailItem(progress: Progress){
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Pill(progress.tag)
        Text(text = progress.detail, fontWeight = FontWeight.Normal, fontSize = 16.sp, color = lm_gray600)
    }
}

@Composable
@Preview
private fun BillProgressDetailPreview(){
    TayAppTheme() {
        BillProgressDetail()
    }
}
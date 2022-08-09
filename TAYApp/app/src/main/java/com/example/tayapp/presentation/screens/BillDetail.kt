package com.example.tayapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons

@Composable
fun BillDetail(){
    val scrollState = rememberScrollState()

    Column {
        TayTopAppBarWithBack("의안 상세", {})
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            DetailHeader()
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = KeyLine,
                        vertical = 24.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardBillLine()
                BillPointText()
                BillRevisionText()
                BillDetailNews()
            }
        }
    }
}

@Composable
fun DetailHeader(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(lm_card_yellow, RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
            .padding(bottom = 20.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = KeyLine),

        ) {
            Spacer(modifier = Modifier.height(20.dp))
            PillList("일부개정안", "정부이송")

            Text(
                text = "중대재해 처벌 등에 관한 법률",
                style = TayAppTheme.typo.typography.h1
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "김삼순의원 등 10명",
                    fontSize = 12.sp,
                    color = lm_gray600,
                    fontWeight = FontWeight.Normal
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = TayIcons.visibility_outlined,
                        contentDescription = "null",
                        tint = lm_gray600,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "19.3만",
                        fontSize = 12.sp,
                        color = lm_gray600,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
        CardCommittee()

        LazyRow(
            contentPadding = PaddingValues(horizontal = KeyLine)
        ){
            item {
                CardBillProgress()

            }
        }


    }
}

@Composable
private fun CardCommittee(){
    TayCard(
        modifier = Modifier.padding(horizontal = KeyLine)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Card_Inner_Padding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "상임위원회",
                    fontSize = 16.sp,
                    color = lm_gray700,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = TayIcons.help,
                    contentDescription = "null",
                    tint = lm_sementic_blue,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = "미정",
                fontSize = 16.sp,
                color = lm_gray600,
                fontWeight = FontWeight.Normal
            )

        }

    }
}

@Composable
private fun CardBillProgress(){
    TayCard() {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(Card_Inner_Padding),
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row() {
                    Text(
                        text = "진행현황",
                        fontSize = 16.sp,
                        color = lm_gray700,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = TayIcons.help,
                        contentDescription = "null",
                        tint = lm_sementic_blue,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "D+452",
                        fontSize = 16.sp,
                        color = lm_gray600,
                        fontWeight = FontWeight.Normal
                    )
                }
                Row {
                    BillProgressItem("접수")
                    BillArrow()
                    BillProgressItem("심사")
                    BillArrow()
                    BillProgressItem("심의")
                    BillArrow()
                    BillProgressItem("부결")
                    BillArrow()
                    BillProgressItem("정부이송")
                    BillArrow()
                    BillProgressItem("공포")
                }
            }

        }
    }
}

@Composable
private fun CardBillLine(){
    TayCard(
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(vertical = 18.dp, horizontal = 11.dp)
        ) {
            Text(
                text = "이 법에 접수된 의안이 또 있어요!",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = lm_gray700,
                modifier = Modifier.padding(horizontal = 3.dp),
                maxLines = 1
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LineSearchedBill()
                LineSearchedBill()
            }
            TayButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("버튼", style = TayAppTheme.typo.typography.button)
            }
        }
    }
}

@Composable
private fun BillPointText(){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Title(string = "법안 핵심 내용")
        Text(
            text = "제안이유 및 주요내용",
            style = TayAppTheme.typo.typography.body1
        )
        Text(
            text = "현행법은 사업 또는 사업장, 공중이용시설 및 공중교통수단을 운영하거나 인체에 해로운 원료나 제조물을 취급하면서 안전ㆍ보건조치의무를 위반하여 인명피해를 발생케 한 사업주, 경영책임자, 공무원 및 법인에 대한 처벌 등을 규정함으로써 중대재해를 예방하고자 제정되었음. 동 법률의 입법 취지가 사업주와 경영책임자 등에 대한 처벌을 강화하는 것이나 이들의 처벌에 대한 규정만으로 모든 재해를 예방하는 데에는 한계가 있으며, 사업주와 경영책임자 등이 안전 및 보건 확보를 위한 충분한 조치를 하였음에도 재해가 발생한 경우 법률 적용의 다툼이 있을 수 있고 과도한 처벌로 인한 선량한 자의 억울한 피해도 발생할 수 있음. 이에 법무부장관은 중대재해 발생을 예방하기 위하여 관계 부처의 장과 협의하여 중대재해 예방에 관한 기준을 고시하고, 사업주와 경영책임자 등에게 이를 권고할 수 있도록 하고자 함. 또한 고시에 따라 작업환경에 관한 표준 적용, 중대재해 예방 감지 및 조치 지능화 등을 하기 위한 정보통신 시설의 설치 등을 이행하고, 이를 인증 받은 경우에는 사업주와 경영책임자 등에게 적용하는 처벌 형량을 감경할 수 있도록 하여, 사업주와 경영책임자 등이 보다 적극적으로 중대재해 예방을 위한 노력과 조치를 취하도록 함으로써 시민과 종사자의 생명과 신체를 폭넓게 보호하고자 함(안 제5조의2, 제5조의3 및 제17조 신설).",
            style = TayAppTheme.typo.typography.body2
        )
    }
}

@Composable
private fun BillDetailNews(){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Title(string = "관련 뉴스,")
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CardNews()
            CardNews()
            CardNews()
            CardNews()
            CardNews()
        }
    }
}

/**
 * pill수정 필요
 */
@Composable
private fun BillRevisionText(){
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Title(string = "개정 내용 확인하기")
        
        TayCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(Card_Inner_Padding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Title(string = "개정 내용 확인하기")
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    BillRevisionItem()
                    BillRevisionItem()
                    BillRevisionItem()
                    BillRevisionItem()
                }
            }
        }

        TayButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("개정 내용 확인하기", style = TayAppTheme.typo.typography.button)
            Icon(
                imageVector = TayIcons.navigate_next,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun BillRevisionItem(){
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Pill("개정안")
        Text(
            text = "어쩌구저꺼주",
            style = TayAppTheme.typo.typography.body2
        )
    }
}

@Composable
private fun BillProgressItem(
    string: String
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Pill("$string")
        Text(
            text = "22.07.20",
            fontSize = 9.sp,
            color = lm_gray600,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun BillArrow(){
    Icon(
        imageVector = TayIcons.navigate_next,
        contentDescription = ""
    )
}

@Preview
@Composable
fun BillDetailPreview(){
    TayAppTheme {
        BillDetail()
        //BillProgressItem()
        //CardBillProgress()
    }
}
package com.example.tayapp.presentation.screens.initial.pager

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.ButtonLargeHeight
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.components.TayTextField
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.utils.textDp
import kotlinx.coroutines.launch


@Composable
fun BoxScope.BasicInformation(
    onClick: ()-> Unit
) {
    val scope = rememberCoroutineScope()
    Column {
        Spacer(Modifier.height(10.dp))
        Text(
            "기본정보를 입력해주세요.",
            color = lm_gray800,
            style = TayAppTheme.typo.typography.h3
        )
        Spacer(modifier = Modifier.height(33.dp))
        Column {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("이메일")
                TayTextField(value = "", onValueChange = {})
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconText(text = "사용가능")
                    IconText(text = "이메일 인증")
                }
            }
            Spacer(modifier = Modifier.height(31.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("비밀번호")
                TayTextField(value = "", onValueChange = {})
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconText(text = "8~20자 이내")
                    IconText(text = "영문 포함")
                    IconText(text = "숫자 포함")
                }
                TayTextField(value = "", onValueChange = {})
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconText(text = "비밀번호 일치")
                }
            }
        }
    }

    TayButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonLargeHeight)
            .align(Alignment.BottomCenter),
        contentColor = lm_gray400,
        backgroundColor = lm_gray100,
    ) {
        Text("확인", style = TayAppTheme.typo.typography.button)
    }
}

@Composable
private fun IconText(color: Color = lm_sementic_green2, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = TayIcons.check, contentDescription = null, tint = color)
        Text(text, color = color, fontSize = 14.textDp)
    }
}
package com.example.tayapp.presentation.screens.initial.pager

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.ButtonLargeHeight
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.components.TayCheckbox
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.utils.matchWidth
import com.example.tayapp.utils.textDp
import kotlinx.coroutines.launch


@Composable
fun BoxScope.TermsOfService(toNext: suspend () -> Unit, animation: suspend () -> Unit) {
    val scope = rememberCoroutineScope()
    Column {

        var bool1 by remember { mutableStateOf(false) }
        var bool2 by remember { mutableStateOf(false) }
        var bool3 by remember { mutableStateOf(false) }
        var bool4 by remember { mutableStateOf(false) }
        var bool5 by remember { mutableStateOf(false) }


        Spacer(Modifier.height(10.dp))
        Text(
            "오늘, 여의도 서비스 이용약관에 \n동의하여 주세요",
            color = lm_gray800,
            style = TayAppTheme.typo.typography.h3
        )
        Spacer(modifier = Modifier.height(26.dp))
        Row(
            modifier = Modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TayCheckbox(
                checked = bool1,
                onCheckedChange = {
                    if (!bool1) {
                        bool1 = true
                        bool2 = true
                        bool3 = true
                        bool4 = true
                        bool5 = true
                    } else bool1 = false
                }
            )
            Text(
                "아래 약관에 모두 동의합니다",
                fontWeight = FontWeight.Medium,
                color = lm_gray700,
                fontSize = 16.textDp,
                modifier = Modifier.width(260.matchWidth)
            )
        }
        Divider(color = lm_gray100)
        Row(
            modifier = Modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TayCheckbox(
                checked = bool2,
                onCheckedChange = { bool2 = !bool2 },
            )
            Text(
                "[필수] 서비스 이용약관", fontWeight = FontWeight.Normal, color = lm_gray600,
                modifier = Modifier.width(260.matchWidth)
            )
            Icon(imageVector = TayIcons.navigate_next, contentDescription = "")
        }
        Row(
            modifier = Modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TayCheckbox(
                checked = bool3,
                onCheckedChange = { bool3 = !bool3 },
            )
            Text(
                "[필수] 개인정보 처리방침",
                fontWeight = FontWeight.Normal,
                color = lm_gray600,
                modifier = Modifier.width(260.matchWidth)
            )
            Icon(imageVector = TayIcons.navigate_next, contentDescription = "")
        }
        Row(
            modifier = Modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TayCheckbox(
                checked = bool4,
                onCheckedChange = { bool4 = !bool4 },
            )
            Text(
                "[선택] 마케팅 정보 수신 동의",
                fontWeight = FontWeight.Normal,
                color = lm_gray600,
                modifier = Modifier.width(260.matchWidth)
            )
            Icon(imageVector = TayIcons.navigate_next, contentDescription = "")
        }
        Row(
            modifier = Modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TayCheckbox(
                checked = bool5,
                onCheckedChange = { bool5 = !bool5 },
            )
            Text(
                "[선택] 개인정보 수집 이용 동의",
                fontWeight = FontWeight.Normal,
                color = lm_gray600,
                modifier = Modifier.width(260.matchWidth)
            )
            Icon(imageVector = TayIcons.navigate_next, contentDescription = "")
        }
    }
    TayButton(
        onClick = {
            scope.launch {
                launch { animation() }
                toNext()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonLargeHeight)
            .align(Alignment.BottomCenter),
        contentColor = lm_gray400,
        backgroundColor = lm_gray100,
    ) {
        Text("이메일 인증하기", style = TayAppTheme.typo.typography.button)
    }
}


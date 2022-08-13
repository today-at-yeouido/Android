package com.example.tayapp.presentation.screens.initial


import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.utils.matchWidth
import com.example.tayapp.utils.textDp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen(navController: NavController, upPress: () -> Unit = {}) {
    Column {
        TayTopAppBarWithBack(string = "회원가입", upPress = upPress)
        val pagerState = rememberPagerState()
        val float = remember { Animatable(270f) }
        Canvas(modifier = Modifier.fillMaxWidth()) {
            Log.d("##88", "size ${size.width}")
            drawLine(color = lm_primary50, start = Offset(0f, 0f), end = Offset(float.value, 0f))
        }

        HorizontalPager(
            state = pagerState, count = 4, userScrollEnabled = false
        ) {
            RegisterItems(it, { float.animateTo(float.value + 270f) }) {
                pagerState.animateScrollToPage(it + 1)
            }
        }
    }

}

@Composable
fun RegisterItems(index: Int, animation: suspend () -> Unit, toNext: suspend () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(horizontal = KeyLine, vertical = 30.dp)
    ) {
        when (index) {
            0 -> EmailFiled(toNext = toNext, animation = animation)
            1 -> TermsOfService(toNext = toNext, animation = animation)
        }
    }
}

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


@Composable
fun BoxScope.EmailFiled(toNext: suspend () -> Unit, animation: suspend () -> Unit) {
    val scope = rememberCoroutineScope()
    Column {
        var email by remember { mutableStateOf("") }

        Spacer(Modifier.height(10.dp))
        Text("아이디로 사용할 이메일을 적어주세요", color = lm_gray800, style = TayAppTheme.typo.typography.h3)
        Spacer(Modifier.height(33.dp))
        EmailField(email) { email = it }
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

@Composable
private fun EmailField(value: String, onValueChange: (String) -> Unit) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("이메일", color = lm_gray600, fontWeight = FontWeight.Normal)
        TayTextField(
            value = value, placeholder = {
                Text(
                    "Tay@gmail.com", fontSize = 16.textDp, color = lm_gray300
                )
            }, onValueChange = onValueChange
        )
    }
}


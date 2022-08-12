package com.example.tayapp.presentation.screens.initial

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tayapp.presentation.components.ButtonLargeHeight
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.components.TayTextField
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.utils.textDp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen(navController: NavController, upPress: () -> Unit = {}) {
    Column() {
        TayTopAppBarWithBack(string = "회원가입", upPress = upPress)
        val pagerState = rememberPagerState()

        HorizontalPager(
            state = pagerState,
            count = 4,
            userScrollEnabled = false
        ) {
            RegisterItems(it) { pagerState.animateScrollToPage(it + 1) }
        }
    }

}

@Composable
fun RegisterItems(index: Int, toNext: suspend () -> Unit) {
    when (index) {
        0 -> EmailFiled(toNext)
        else -> Text("hi")
    }
}

@Composable
fun TermsOfService() {

}


@Composable
fun EmailFiled(toNext: suspend () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(horizontal = KeyLine, vertical = 30.dp)
    ) {
        val scope = rememberCoroutineScope()
        Column {
            var email by remember { mutableStateOf("") }

            Spacer(Modifier.height(10.dp))
            Text("아이디로 사용할 이메일을 적어주세요", color = lm_gray800, style = TayAppTheme.typo.typography.h3)
            Spacer(Modifier.height(33.dp))
            EmailField(email) { email = it }
        }
        TayButton(
            onClick = { scope.launch { toNext() } },
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
                    "Tay@gmail.com",
                    fontSize = 16.textDp,
                    color = lm_gray300
                )
            },
            onValueChange = onValueChange
        )
    }
}


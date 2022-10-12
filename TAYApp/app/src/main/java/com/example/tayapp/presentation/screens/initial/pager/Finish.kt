package com.example.tayapp.presentation.screens.initial.pager

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.ButtonLargeHeight
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray100
import com.example.tayapp.presentation.ui.theme.lm_gray400
import com.example.tayapp.presentation.ui.theme.lm_gray800
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun BoxScope.Finish(
    onClick: () -> Unit
) {

    Column {
        Spacer(Modifier.height(10.dp))
        Text(
            "이메일로 확인 메일을 보냈습니다 \n" +
                    "가입을 완료해주세요.",
            color = TayAppTheme.colors.headText,
            style = TayAppTheme.typo.typography.h3
        )
    }
    TayButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonLargeHeight)
            .align(Alignment.BottomCenter),
        contentColor = TayAppTheme.colors.background,
        backgroundColor = TayAppTheme.colors.headText,
    ) {
        Text("완료", style = TayAppTheme.typo.typography.button)
    }
}
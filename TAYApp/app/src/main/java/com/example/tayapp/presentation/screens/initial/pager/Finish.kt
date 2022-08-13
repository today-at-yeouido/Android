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
import kotlinx.coroutines.launch

@Composable
fun BoxScope.Finish(onClick: ()-> Unit) {

    val scope = rememberCoroutineScope()
    Column {
        Spacer(Modifier.height(10.dp))
        Text(
            "축하합니다. 가입이 완료되었습니다.",
            color = lm_gray800,
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
        contentColor = lm_gray400,
        backgroundColor = lm_gray100,
    ) {
        Text("완료", style = TayAppTheme.typo.typography.button)
    }
}
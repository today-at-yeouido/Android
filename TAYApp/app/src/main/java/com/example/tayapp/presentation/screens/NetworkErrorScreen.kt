package com.example.tayapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun NetworkErrorScreen(tryGetData: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().clickable { tryGetData() }
    ) {
        Image(
            modifier = Modifier
                .padding(top = 138.dp)
                .size(100.dp)
                .align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.ic_tay_ch_emoji_gray_default),
            contentDescription = "main_title_image"
        )

        Text(
            text = "통신에 문제가 있어요.\n" +
                    "네트워크를 확인해주세요!",
            modifier = Modifier
                .padding(top = 258.dp)
                .align(Alignment.TopCenter),
            textAlign = TextAlign.Center,
            color = TayAppTheme.colors.disableText
        )
    }

}
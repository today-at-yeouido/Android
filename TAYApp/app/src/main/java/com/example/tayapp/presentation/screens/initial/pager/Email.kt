package com.example.tayapp.presentation.screens.initial.pager

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.ButtonLargeHeight
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.components.TayTextField
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.textDp
import kotlinx.coroutines.launch

@Composable
fun BoxScope.Email(
    onClick: () -> Unit
) {
    Column {
        var email by remember { mutableStateOf("") }

        Spacer(Modifier.height(10.dp))
        Text("아이디로 사용할 이메일을 적어주세요", color = TayAppTheme.colors.headText, style = TayAppTheme.typo.typography.h3)
        Spacer(Modifier.height(33.dp))
        EmailField(email) { email = it }
    }
    TayButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonLargeHeight)
            .align(Alignment.BottomCenter),
        contentColor = TayAppTheme.colors.disableText,
        backgroundColor = TayAppTheme.colors.layer3,
    ) {
        Text("이메일 인증하기", style = TayAppTheme.typo.typography.button)
    }

}

@Composable
private fun EmailField(value: String, onValueChange: (String) -> Unit) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("이메일", color = TayAppTheme.colors.subduedText, fontWeight = FontWeight.Normal)
        TayTextField(
            value = value,
            placeholder = {
                Text(
                    "Tay@gmail.com", fontSize = 16.textDp, color = TayAppTheme.colors.disableIcon
                )
            },
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = TayAppTheme.colors.background,
                focusedBorderColor = TayAppTheme.colors.primary,
                unfocusedBorderColor = TayAppTheme.colors.layer3
            )
        )
    }
}
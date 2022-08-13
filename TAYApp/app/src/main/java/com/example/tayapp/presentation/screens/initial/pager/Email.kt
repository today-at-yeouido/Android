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
import com.example.tayapp.utils.textDp
import kotlinx.coroutines.launch

@Composable
fun BoxScope.Email(onClick: ()-> Unit) {
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
            onClick()
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
            value = value,
            placeholder = {
                Text(
                    "Tay@gmail.com", fontSize = 16.textDp, color = lm_gray300
                )
            },
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = lm_gray000,
                focusedBorderColor = lm_primary50,
                unfocusedBorderColor = lm_gray100
            )
        )
    }
}
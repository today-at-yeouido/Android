package com.example.tayapp.presentation.screens.initial.pager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.ButtonLargeHeight
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.components.TayTextField
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.textDp


@Composable
fun BoxScope.BasicInformation(
    viewModel: LoginViewModel,
    onClick: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var pass1 by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }

    val regex1 = Regex(pattern = "[a-zA-Z\\d._+-]+@[a-zA-Z\\d]+\\.[a-zA-Z\\d.]+")
    val regex2 = Regex(pattern = "(?=.*\\d)(?=.*[a-z]).{8,}")
    val bool1 = regex1.matches(email)
    val bool2 = regex2.matches(pass1)
    val bool3 = if (pass2.isNotBlank()) pass1 == pass2 else false

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
                TayTextField(
                    value = email, onValueChange = { email = it },
                    colors =
                    TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = lm_gray000,
                        focusedBorderColor = if (bool1) lm_sementic_green2 else lm_gray100,
                        unfocusedBorderColor = if (bool1) lm_sementic_green2 else lm_gray100
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconText(text = "사용가능", color = if (bool1) lm_sementic_green2 else lm_gray400)
                    IconText(text = "이메일 인증", color = if (bool1) lm_sementic_green2 else lm_gray400)
                }
            }
            Spacer(modifier = Modifier.height(31.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("비밀번호")
                TayTextField(
                    value = pass1,
                    onValueChange = { pass1 = it },
                    colors =
                    TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = lm_gray000,
                        focusedBorderColor = if (bool2) lm_sementic_green2 else lm_gray100,
                        unfocusedBorderColor = if (bool2) lm_sementic_green2 else lm_gray100
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconText(
                        text = "8~20자 이내",
                        color = if (bool2) lm_sementic_green2 else lm_gray400
                    )
                    IconText(text = "영문 포함", color = if (bool2) lm_sementic_green2 else lm_gray400)
                    IconText(text = "숫자 포함", color = if (bool2) lm_sementic_green2 else lm_gray400)
                }
                TayTextField(
                    value = pass2,
                    onValueChange = { pass2 = it },
                    colors =
                    TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = lm_gray000,
                        focusedBorderColor = if (bool3) lm_sementic_green2 else lm_gray100,
                        unfocusedBorderColor = if (bool3) lm_sementic_green2 else lm_gray100
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconText(
                        text = "비밀번호 일치",
                        color = if (bool3) lm_sementic_green2 else lm_gray400
                    )
                }
            }
        }
    }

    val bool4 = bool1 && bool2 && bool3

    TayButton(
        onClick = {
            viewModel.requestRegister(email, pass1, pass2)
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonLargeHeight)
            .align(Alignment.BottomCenter),
        enabled = bool4,
        contentColor = if (bool4) lm_gray000 else lm_gray400,
        backgroundColor = if (bool4) lm_gray800 else lm_gray100,
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
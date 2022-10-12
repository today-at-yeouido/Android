package com.example.tayapp.presentation.screens.initial.pager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
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
    requestRegister: (String, String, String) -> Unit,
    onClick: () -> Unit,
    email: String
) {
    val focusManager = LocalFocusManager.current
    var pass1 by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }

    val regex2 = Regex(pattern = "(?=.*\\d)(?=.*[a-z]).{8,}")
    val bool2 = regex2.matches(pass1)
    val bool3 = if (pass2.isNotBlank()) pass1 == pass2 else false

    Column {
        Spacer(Modifier.height(10.dp))
        Text(
            "기본정보를 입력해주세요.",
            color = TayAppTheme.colors.headText,
            style = TayAppTheme.typo.typography.h3
        )
        Spacer(modifier = Modifier.height(33.dp))
        Column {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("비밀번호", color = TayAppTheme.colors.bodyText)
                TayTextField(
                    value = pass1,
                    onValueChange = { pass1 = it },
                    colors =
                    TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = TayAppTheme.colors.background,
                        focusedBorderColor = if (bool2) TayAppTheme.colors.primary else TayAppTheme.colors.layer3,
                        unfocusedBorderColor = if (bool2) TayAppTheme.colors.primary else TayAppTheme.colors.layer3
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        //엔터를 눌렀을 때 이벤트 정의
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconText(
                        text = "8~20자 이내",
                        color = if (bool2) TayAppTheme.colors.primary else TayAppTheme.colors.subduedIcon
                    )
                    IconText(text = "영문 포함", color = if (bool2) TayAppTheme.colors.primary else TayAppTheme.colors.subduedIcon)
                    IconText(text = "숫자 포함", color = if (bool2) TayAppTheme.colors.primary else TayAppTheme.colors.subduedIcon)
                }
                Spacer(Modifier.height(10.dp))
                Text("비밀번호 확인", color = TayAppTheme.colors.bodyText)
                TayTextField(
                    value = pass2,
                    onValueChange = { pass2 = it },
                    colors =
                    TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = TayAppTheme.colors.background,
                        focusedBorderColor = if (bool3) TayAppTheme.colors.primary else TayAppTheme.colors.layer3,
                        unfocusedBorderColor = if (bool3) TayAppTheme.colors.primary else TayAppTheme.colors.layer3
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconText(
                        text = "비밀번호 일치",
                        color = if (bool3) TayAppTheme.colors.primary else TayAppTheme.colors.subduedIcon
                    )
                }
                Spacer(Modifier.height(10.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("이메일", color = TayAppTheme.colors.bodyText)
                    Text(email, fontSize = 16.textDp, color = TayAppTheme.colors.subduedText )
                }
            }
        }
    }

    val bool4 = bool2 && bool3

    TayButton(
        onClick = {
            requestRegister(email, pass1, pass2)
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonLargeHeight)
            .align(Alignment.BottomCenter),
        enabled = bool4,
        contentColor = if (bool4) TayAppTheme.colors.background else TayAppTheme.colors.subduedIcon,
        backgroundColor = if (bool4) TayAppTheme.colors.headText else TayAppTheme.colors.layer3,
    ) {
        Text("확인", style = TayAppTheme.typo.typography.button)
    }
}

@Composable fun IconText(color: Color = TayAppTheme.colors.primary, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = TayIcons.check, contentDescription = null, tint = color)
        Text(text, color = color, fontSize = 14.textDp)
    }
}
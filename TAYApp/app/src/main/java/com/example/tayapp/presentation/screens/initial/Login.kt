package com.example.tayapp.presentation.screens.initial

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tayapp.presentation.components.ButtonLargeHeight
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.components.TayTextField
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.navigation.AppGraph
import com.example.tayapp.presentation.navigation.Destinations
import com.example.tayapp.presentation.states.LoginState
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.textDp

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    upPress: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TayTopAppBarWithBack(string = "로그인", upPress)
        Login(navController, viewModel)
    }
}

@Composable
private fun Login(navController: NavController, viewModel: LoginViewModel) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = KeyLine)
    ) {
        Spacer(Modifier.height(40.dp))
        InputField(viewModel::requestLogin) { navController.navigate(AppGraph.HOME_GRAPH) }
        Spacer(Modifier.height(50.dp))
        SocialField(viewModel::kakaoLogin, viewModel::naverLogin) { navController.navigate(AppGraph.HOME_GRAPH) }
        Spacer(Modifier.height(80.dp))
        RegisterField { navController.navigate(it) }
    }
}

@Composable
private fun InputField(
    requestLogin: (String, String, () -> Unit) -> Unit,
    navigate: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val regex1 = Regex(pattern = "[a-zA-Z\\d._+-]+@[a-zA-Z\\d]+\\.[a-zA-Z\\d.]+")
    val regex2 = Regex(pattern = "(?=.*\\d)(?=.*[a-z]).{8,}")
    val bool1 = regex1.matches(email)
    val bool2 = regex2.matches(password)
    val bool3 = bool1 && bool2


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TayTextField(
            placeholder = {
                Text(
                    "이메일",
                    fontSize = 16.textDp,
                    color = lm_gray300
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = lm_gray000,
                focusedBorderColor = if (bool1) lm_sementic_green2 else lm_gray100,
                unfocusedBorderColor = if (bool1) lm_sementic_green2 else lm_gray100
            ),
            value = email,
        ) { email = it }

        TayTextField(
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = {
                Text(
                    "비밀번호",
                    fontSize = 16.textDp,
                    color = lm_gray300
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = lm_gray000,
                focusedBorderColor = if (bool2) lm_sementic_green2 else lm_gray100,
                unfocusedBorderColor = if (bool2) lm_sementic_green2 else lm_gray100
            ),
            value = password,
        ) { password = it }

        TayButton(
            onClick = {
                requestLogin(email, password, navigate)
                LoginState.changeState()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(ButtonLargeHeight),
            backgroundColor = if (bool3) lm_gray800 else lm_gray100,
            contentColor = if (bool3) lm_gray100 else lm_gray400,
            enabled = bool3
        ) {
            Text("로그인", style = TayAppTheme.typo.typography.button)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "아이디 찾기", color = lm_gray500, fontSize = 16.textDp)
            Text(text = "비밀번호 찾기", color = lm_gray500, fontSize = 16.textDp)
        }
    }
}

@Composable
private fun SocialField(
    kakaoLogin: (() -> Unit) -> Unit,
    naverLogin: () -> Unit,
    nav: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("소셜 로그인", fontSize = 16.textDp, color = lm_gray700)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Canvas(modifier = Modifier
                .size(50.dp)
                .clickable {
                    kakaoLogin { nav() }
                }) {
                drawCircle(Color.Yellow)
            }
            Canvas(modifier = Modifier.size(50.dp).clickable {
                naverLogin()
            }) {
                drawCircle(Color.Green)
            }
        }
    }
}

@Composable
private fun RegisterField(navigate: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Divider(color = lm_gray100)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("계정이 없으신가요?", color = lm_gray500)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navigate(Destinations.SIGN_UP)
                }
            ) {
                Text("이메일로 가입하기", color = lm_sementic_blue2)
                Icon(TayIcons.navigate_next, contentDescription = null, tint = lm_sementic_blue2)
            }
        }
        Divider(color = lm_gray100)
        Text(
            "로그인 없이 이용하기",
            fontSize = 18.sp,
            modifier = Modifier.clickable { navigate(AppGraph.HOME_GRAPH) })
    }
}
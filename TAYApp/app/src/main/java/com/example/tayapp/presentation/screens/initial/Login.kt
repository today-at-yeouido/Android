package com.example.tayapp.presentation.screens.initial

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tayapp.presentation.components.ButtonLargeHeight
import com.example.tayapp.presentation.components.TayButton
import com.example.tayapp.presentation.components.TayTextField
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.navigation.AppGraph
import com.example.tayapp.presentation.navigation.Destinations
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.activityLauncher
import com.example.tayapp.utils.textDp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    upPress: () -> Unit = {}
) {
    val loginState by viewModel.isLogin

    val navigate = { route: String ->
        navController.popBackStack()
        navController.navigate(route)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TayTopAppBarWithBack(string = "로그인", upPress)
        Login(
            viewModel::requestLogin,
            viewModel::kakaoLogin,
            viewModel::naverLogin,
            viewModel::googleLogin,
            viewModel::getGoogleLoginAuth
        ) { navigate(it) }
    }

    LaunchedEffect(key1 = loginState) {
        Log.d("##88", "런치드 이펙트 $loginState")
        if (loginState) {
            navigate(AppGraph.HOME_GRAPH)
        }
    }
}

@Composable
private fun Login(
    requestLogin: (String, String) -> Unit,
    kakaoLogin: () -> Unit,
    naverLogin: () -> Unit,
    googleLogin: (GoogleSignInAccount) -> Unit,
    googleAuth: () -> GoogleSignInClient,
    navigate: (String) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = KeyLine)
    ) {
        Spacer(Modifier.height(40.dp))
        InputField(requestLogin)
        Spacer(Modifier.height(50.dp))
        SocialField(
            kakaoLogin,
            naverLogin,
            googleLogin,
            googleAuth
        )
        Spacer(Modifier.height(80.dp))
        RegisterField { navigate(it) }
    }
}

@Composable
private fun InputField(
    requestLogin: (String, String) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TayTextField(
            placeholder = {
                Text(
                    "이메일",
                    fontSize = 16.textDp,
                    color = TayAppTheme.colors.fieldBorder
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = lm_gray000,
                focusedBorderColor = TayAppTheme.colors.primary,
                unfocusedBorderColor = TayAppTheme.colors.layer3
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
                focusedBorderColor = TayAppTheme.colors.primary,
                unfocusedBorderColor = TayAppTheme.colors.layer3
            ),
            value = password,
        ) { password = it }

        TayButton(
            onClick = {
                requestLogin(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(ButtonLargeHeight),
            backgroundColor = TayAppTheme.colors.bodyText,
            contentColor = TayAppTheme.colors.layer3
        ) {
            Text(
                "로그인",
                style = TayAppTheme.typo.typography.button,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "아이디 찾기", color = TayAppTheme.colors.gray500, fontSize = 16.textDp)
            Text(text = "비밀번호 찾기", color = TayAppTheme.colors.gray500, fontSize = 16.textDp)
        }
    }
}

@Composable
private fun SocialField(
    kakaoLogin: () -> Unit,
    naverLogin: () -> Unit,
    googleLogin: (GoogleSignInAccount) -> Unit,
    googleAuth: () -> GoogleSignInClient,
) {
    val resultLauncher = activityLauncher(
        onSuccess = { intent ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
            googleLogin(task.result)
        },
        onError = { Log.d("##99", "오류 발생") }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("소셜 로그인", fontSize = 16.textDp, color = TayAppTheme.colors.bodyText)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Canvas(modifier = Modifier
                .size(50.dp)
                .clickable {
                    kakaoLogin()
                }) {
                drawCircle(Color.Yellow)
            }

            Canvas(modifier = Modifier
                .size(50.dp)
                .clickable {
                    naverLogin()
                }) {
                drawCircle(Color.Green)
            }

            Canvas(modifier = Modifier
                .size(50.dp)
                .clickable {
                    val client = googleAuth()
                    resultLauncher.launch(client.signInIntent)
                }) {
                drawCircle(Color.Black)
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
        Divider(color = TayAppTheme.colors.layer3)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("계정이 없으신가요?", color = TayAppTheme.colors.gray500)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navigate(Destinations.SIGN_UP)
                }
            ) {
                Text("이메일로 가입하기", color = TayAppTheme.colors.information2)
                Icon(
                    TayIcons.navigate_next,
                    contentDescription = null,
                    tint = TayAppTheme.colors.information2
                )
            }
        }
        Divider(color = TayAppTheme.colors.layer3)
        Text(
            "로그인 없이 이용하기",
            color = TayAppTheme.colors.headText,
            fontSize = 18.sp,
            modifier = Modifier.clickable { navigate(AppGraph.HOME_GRAPH) })
    }
}
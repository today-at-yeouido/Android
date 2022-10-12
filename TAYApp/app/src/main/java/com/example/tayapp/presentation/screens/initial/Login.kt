package com.example.tayapp.presentation.screens.initial

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tayapp.R
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
    val isTryLogin by viewModel.isTryLogin

    val navigate = { route: String ->
        navController.popBackStack()
        navController.navigate(route)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TayTopAppBarWithBack(string = "로그인", upPress)

        if(!loginState && isTryLogin) {
            Text(
                text = "아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다. " +
                    "입력하신 내용을 다시 확인해주세요.",
                color = TayAppTheme.colors.danger2,
                fontSize = 14.textDp,
                modifier = Modifier.padding(horizontal = KeyLine)
            )
        }
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
        if (loginState == true) {
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

    val focusManager = LocalFocusManager.current

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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                //엔터를 눌렀을 때 이벤트 정의
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
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
            contentColor = TayAppTheme.colors.layer1
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
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.btn_kakao_login),
                contentDescription = "kakao_login",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(250.dp)
                    .clickable { kakaoLogin() }
            )

            Image(
                painter = painterResource(id = R.drawable.btn_naver_login),
                contentDescription = "naver_login",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(250.dp)
                    .clickable { naverLogin() }
            )

            Image(
                painter = painterResource(id = R.drawable.btn_google_login),
                contentDescription = "google_login",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(250.dp)
                    .clickable {
                        val client = googleAuth()
                        resultLauncher.launch(client.signInIntent)
                    }
            )


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
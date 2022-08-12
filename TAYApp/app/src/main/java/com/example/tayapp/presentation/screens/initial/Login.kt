package com.example.tayapp.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.navigation.Destinations
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.getActivity
import com.example.tayapp.utils.textDp

@Composable
fun LoginScreen(navController: NavController, upPress: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TayTopAppBarWithBack(string = "로그인", upPress)
        Login(navController)
    }
}

@Composable
private fun Login(navController: NavController) {

    val viewModel = hiltViewModel<LoginViewModel>(getActivity())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = KeyLine)
            .width(328.dp)
    ) {
        Spacer(Modifier.height(40.dp))
        InputField(viewModel::requestLogin)
        Spacer(Modifier.height(50.dp))
        SocialField()
        Spacer(Modifier.height(80.dp))
        RegisterField(navController)
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
        TayTextField(value = email) { email = it }
        TayTextField(
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            value = password,
        ) { password = it }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = lm_gray100
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                requestLogin(email, password)

            }
        ) {
            Text("로그인")
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
private fun SocialField() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("소셜 로그인", fontSize = 16.textDp, color = lm_gray700)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                drawCircle(Color.Yellow)
            }
            Canvas(modifier = Modifier.size(50.dp)) {
                drawCircle(Color.Green)
            }
        }
    }
}

@Composable
private fun RegisterField(navController: NavController) {
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
                    navController.navigate(Destinations.SIGN_UP)
                }
            ) {
                Text("이메일로 가입하기", color = lm_sementic_blue2)
                Icon(TayIcons.navigate_next, contentDescription = null, tint = lm_sementic_blue2)
            }
        }
        Divider(color = lm_gray100)
    }
}

@Composable
private fun TayTextField(
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                "비밀번호",
                fontSize = 16.textDp,
                color = lm_gray300
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = lm_gray000,
            focusedBorderColor = lm_gray100,
            unfocusedBorderColor = lm_gray100
        ),
        shape = RoundedCornerShape(8.dp),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(fontSize = 16.textDp),
        modifier = Modifier
            .fillMaxWidth()
    )
}


@Preview
@Composable
fun PreviewLogin() {
    LoginScreen(navController = rememberNavController()) {}
}
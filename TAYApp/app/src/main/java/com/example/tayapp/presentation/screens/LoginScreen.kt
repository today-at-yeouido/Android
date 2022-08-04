package com.example.tayapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tayapp.presentation.navigation.MainDestination
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.getActivity

@Composable
fun LoginScreen(navController: NavController) {

    val viewModel = hiltViewModel<LoginViewModel>(getActivity())

    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }


    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = KeyLine)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("이메일")
            TextField(
                value = emailState,
                onValueChange = { emailState = it },
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
            Text("비밀번호")
            TextField(
                value = passwordState,
                onValueChange = { passwordState = it },
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
            )

            Row {
                Button(onClick = {
                    viewModel.requestLogin(emailState, passwordState) {
                        navController.navigate(
                            MainDestination.HOME
                        )
                    }
                }) {
                    Text(" 확인", color = Color.Black)
                }
                Spacer(modifier = Modifier.width(100.dp))
                Button(onClick = {
                    navController.navigate(MainDestination.SIGN_UP)
                }) {
                    Text("회원가입", color = Color.Black)
                }
            }
        }
    }
}


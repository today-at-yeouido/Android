package com.example.tayapp.presentation.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.getActivity

@Composable
fun SignUpScreen(navController: NavController) {

    val viewModel = hiltViewModel<LoginViewModel>(getActivity())

    var emailState by remember { mutableStateOf("") }
    var passState1 by remember { mutableStateOf("") }
    var passState2 by remember { mutableStateOf("") }

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
            Text(text = "이메일")
            TextField(
                value = emailState, onValueChange = { emailState = it },
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
            Text(text = "비밀번호")
            TextField(
                value = passState1, onValueChange = { passState1 = it },
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
            Text(text = "비밀번호 확인")
            TextField(
                value = passState2, onValueChange = { passState2 = it },
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
            )

            Row {
                Button(onClick = {
                    viewModel.requestRegister(
                        emailState,
                        passState1,
                        passState2
                    ) { navController.popBackStack() }
                }) {
                    Text(" 확인", color = Color.Black)
                }
            }
        }
    }
}


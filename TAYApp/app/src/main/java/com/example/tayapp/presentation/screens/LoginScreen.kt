package com.example.tayapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen() {
    val viewModel = hiltViewModel<LoginViewModel>()

    var email by remember { mutableStateOf("") }
    var pas1 by remember { mutableStateOf("") }
    var pas2 by remember { mutableStateOf("") }

    Surface(
        color = Color.White,
        contentColor = Color.Gray,
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = KeyLine)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = email, onValueChange = { email = it }, Modifier.fillMaxWidth())
            TextField(value = pas1, onValueChange = { pas1 = it }, Modifier.fillMaxWidth())
            TextField(value = pas2, onValueChange = { pas2 = it }, Modifier.fillMaxWidth())

            Button(onClick = { /*TODO*/ }) {
                Text(" 확인")
            }
        }
    }
}


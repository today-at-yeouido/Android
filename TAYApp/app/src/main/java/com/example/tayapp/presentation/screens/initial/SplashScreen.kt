package com.example.tayapp.presentation.screens.initial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tayapp.presentation.navigation.AppGraph
import com.example.tayapp.presentation.navigation.Destinations
import com.example.tayapp.presentation.ui.theme.lm_gray000
import com.example.tayapp.presentation.ui.theme.lm_gray700
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.getActivity
import com.example.tayapp.utils.textDp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val viewModel = hiltViewModel<LoginViewModel>(getActivity())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = lm_gray700
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(170.dp))
            Text(
                text = "내 손 안의 작은 국회",
                color = lm_gray000,
                fontSize = 16.textDp,
                lineHeight = 1.em,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.2).textDp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "오늘 여의도",
                color = lm_gray000,
                fontSize = 26.textDp,
                fontWeight = FontWeight.Bold,
                lineHeight = 1.em,
                letterSpacing = (-0.2).textDp
            )
        }
    }

    LaunchedEffect(key1 = true) {
        delay(2000L)
        if (viewModel.isLogin) {
            navController.navigate(AppGraph.HOME_GRAPH)
        } else navController.navigate(Destinations.LOGIN)
    }
}


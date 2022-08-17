package com.example.tayapp.presentation.screens.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.example.tayapp.R
import com.example.tayapp.presentation.navigation.AppGraph
import com.example.tayapp.presentation.navigation.Destinations
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray000
import com.example.tayapp.presentation.ui.theme.lm_gray700
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.example.tayapp.utils.textDp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, viewModel: LoginViewModel) {

    val isLogin by viewModel.isLogin

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = TayAppTheme.colors.bodyText
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "내 손 안의 작은 국회",
                color = TayAppTheme.colors.background,
                fontSize = 16.textDp,
                lineHeight = 1.em,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.2).textDp,
                modifier = Modifier.padding(top = 170.dp).align(Alignment.TopCenter)
            )
            Text(
                text = "오늘 여의도",
                color = TayAppTheme.colors.background,
                fontSize = 26.textDp,
                fontWeight = FontWeight.Bold,
                lineHeight = 1.em,
                letterSpacing = (-0.2).textDp,
                modifier = Modifier.padding(top = 196.dp).align(Alignment.TopCenter)
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                painter = painterResource(id = R.drawable.ic_tay_ch_default),
                contentDescription = "main_title_image"
            )
        }
    }

    LaunchedEffect(key1 = true) {
        delay(2000L)
        if (isLogin) {
            navController.navigate(AppGraph.HOME_GRAPH)
        } else navController.navigate(Destinations.LOGIN)
    }
}


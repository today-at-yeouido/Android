package com.todayeouido.tayapp.presentation.screens.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.todayeouido.tayapp.R
import com.todayeouido.tayapp.presentation.navigation.AppGraph
import com.todayeouido.tayapp.presentation.navigation.Destinations
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.presentation.ui.theme.lm_gray000
import com.todayeouido.tayapp.presentation.ui.theme.lm_gray700
import com.todayeouido.tayapp.presentation.viewmodels.LoginViewModel
import com.todayeouido.tayapp.utils.textDp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController, viewModel: LoginViewModel
) {

    val isLogin by viewModel.isLogin

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = lm_gray700
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "내 손 안의 작은 국회",
                color = lm_gray000,
                fontSize = 16.textDp,
                lineHeight = 1.em,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.2).textDp,
                modifier = Modifier
            )
            Text(
                text = "오늘 여의도",
                color = lm_gray000,
                fontSize = 26.textDp,
                fontWeight = FontWeight.Bold,
                lineHeight = 1.em,
                letterSpacing = (-0.2).textDp,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.size(48.dp))
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .size(100.dp),
                painter = painterResource(id = R.drawable.ic_tay_logo_app),
                contentDescription = "main_title_image",

                )
            Spacer(modifier = Modifier.size(48.dp))
        }
    }

    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.popBackStack()
        navController.navigate(AppGraph.HOME_GRAPH)
    }
}


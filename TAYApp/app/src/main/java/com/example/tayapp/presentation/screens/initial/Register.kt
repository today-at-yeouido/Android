package com.example.tayapp.presentation.screens.initial


import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.navigation.Destinations
import com.example.tayapp.presentation.screens.initial.pager.BasicInformation
import com.example.tayapp.presentation.screens.initial.pager.Email
import com.example.tayapp.presentation.screens.initial.pager.Finish
import com.example.tayapp.presentation.screens.initial.pager.TermsOfService
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.dm_primary50
import com.example.tayapp.presentation.ui.theme.lm_primary50
import com.example.tayapp.presentation.viewmodels.LoginViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: LoginViewModel,
) {
    val pagerState = rememberPagerState()
    val float = remember { Animatable(270f) }
    var current by remember { mutableStateOf(0) }
    var currentFloat by remember { mutableStateOf(270f) }

    Column {
        TayTopAppBarWithBack(string = "회원가입", upPress = {
            if (current >= 0) {
                current -= 1
                currentFloat -= 270f
            }
        })
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                color = if (TayAppTheme.isDark) lm_primary50 else dm_primary50,
                start = Offset(0f, 0f),
                end = Offset(float.value, 0f)
            )
        }

        HorizontalPager(
            state = pagerState, count = 4, userScrollEnabled = false
        ) { index ->
            RegisterItems(index, viewModel) {
                currentFloat += 270f
                current += 1
            }
        }
    }

    LaunchedEffect(key1 = current) {
        Log.d("##88", "current $current")
        if (current in 0..3) {
            launch {
                float.animateTo(currentFloat)
            }
            pagerState.animateScrollToPage(current)
        } else {
            navController.navigate(Destinations.LOGIN)
        }
    }

}

@Composable
fun RegisterItems(
    index: Int,
    viewModel: LoginViewModel,
    onClick: () -> Unit
) {
    var email = viewModel.user.email

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(horizontal = KeyLine, vertical = 30.dp)
    ) {
        when (index) {
            0 -> Email(onClick = onClick, getEmail = viewModel::getUserEmail)
            1 -> BasicInformation(viewModel::requestRegister, onClick = onClick, email)
            2 -> TermsOfService(onClick = onClick)
            3 -> Finish(onClick = onClick)
        }
    }
}







package com.example.tayapp.presentation.screens.initial


import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.screens.initial.pager.BasicInformation
import com.example.tayapp.presentation.screens.initial.pager.Email
import com.example.tayapp.presentation.screens.initial.pager.Finish
import com.example.tayapp.presentation.screens.initial.pager.TermsOfService
import com.example.tayapp.presentation.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen(navController: NavController, upPress: () -> Unit = {}) {
    Column {
        TayTopAppBarWithBack(string = "회원가입", upPress = upPress)
        val pagerState = rememberPagerState()
        val float = remember { Animatable(270f) }
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(color = lm_primary50, start = Offset(0f, 0f), end = Offset(float.value, 0f))
        }

        HorizontalPager(
            state = pagerState, count = 4, userScrollEnabled = false
        ) {
            RegisterItems(it, { float.animateTo(float.value + 270f) }) {
                pagerState.animateScrollToPage(it + 1)
            }
        }
    }

}

@Composable
fun RegisterItems(index: Int, animation: suspend () -> Unit, toNext: suspend () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(horizontal = KeyLine, vertical = 30.dp)
    ) {
        when (index) {
            0 -> Email(toNext = toNext, animation = animation)
            1 -> TermsOfService(toNext = toNext, animation = animation)
            2 -> BasicInformation(toNext = toNext,animation = animation)
            3 -> Finish(toNext = toNext, animation = animation)
        }
    }
}







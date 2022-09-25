package com.example.tayapp.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.viewmodels.SlotMachineViewModel

private val numdata = listOf("최신 이슈", "  맞춤형  ", "최근 발의","최신 이슈", "  맞춤형  ", "최근 발의")

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlotMachine(){

    val viewModel  = hiltViewModel<SlotMachineViewModel>()
    val second by viewModel.seconds.collectAsState(initial = 0)

    Box(){

        Surface(
            Modifier
                .align(Alignment.Center)
                .height(40.dp)
                .width(80.dp)
                .padding(bottom = 5.dp)
                .border(1.dp,TayAppTheme.colors.border, RoundedCornerShape(5.dp)),
            color = TayAppTheme.colors.background,
        ){

        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = second,
                transitionSpec = {
                    addAnimation().using(
                        SizeTransform(clip = false)
                    )
                }
            ) { targetCount ->
                Column(Modifier) {
                    Text(
                        text = "${numdata[targetCount % numdata.size]}",
                        color = TayAppTheme.colors.bodyText.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        text = "${numdata[(targetCount+1) % numdata.size]}",
                        color = TayAppTheme.colors.bodyText.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        text = "${numdata[(targetCount+2) % numdata.size]}",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        text = "${numdata[(targetCount+3) % numdata.size]}",
                        color = TayAppTheme.colors.bodyText.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        GradientView(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .height(50.dp),
            colors = intArrayOf(
                Color.Transparent.toArgb(),
                (TayAppTheme.colors.background).toArgb(),
            )
        )
        Surface(
            Modifier
                .align(Alignment.TopCenter)
                .height(20.dp)
                .fillMaxWidth(),
            color = TayAppTheme.colors.background
        ){

        }
        GradientView(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 15.dp)
                .height(55.dp),
            colors = intArrayOf(
                (TayAppTheme.colors.background).toArgb(),
                Color.Transparent.toArgb()
            )
        )
        Surface(
            Modifier
                .align(Alignment.BottomCenter)
                .height(20.dp)
                .fillMaxWidth(),
            color = TayAppTheme.colors.background
        ){

        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 500): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height/4 } + fadeIn(
        animationSpec = tween(durationMillis = 0 )
    ) with slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -(height/4) } + fadeOut(
        animationSpec = tween(durationMillis = 0)
    )
}
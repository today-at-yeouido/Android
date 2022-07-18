package com.example.tayapp.presentation

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/** Destination 임시로 HOME, DETAIL 설정 */
object MainDestination {
    const val HOME = "HOME"
    const val DETAIL = "DETAIL"
}

@Composable
fun rememberTAYAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, coroutineScope) {
    TAYAppState(scaffoldState, navController, coroutineScope)
}

class TAYAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    scope : CoroutineScope
) {
//    로그인 체크
//    init {
//        scope.launch {
//
//        }
//    }
}
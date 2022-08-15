package com.example.tayapp.presentation.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.TayApplication
import com.example.tayapp.data.remote.dto.bill.LoginDto
import com.example.tayapp.data.remote.dto.login.RegistrationDto
import com.example.tayapp.domain.use_case.LoginUseCases
import com.example.tayapp.presentation.states.LoginUserUiState
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    application: Application
) : AndroidViewModel(application) {

    init {
        checkLogin()
    }

    var user by mutableStateOf(LoginUserUiState())
        private set

    var isLogin by mutableStateOf(false)
        private set

    private val context = TayApplication.cxt()

    fun requestLogin(e: String, p1: String, nav: () -> Unit) {
        viewModelScope.launch {
            user = LoginUserUiState(e, p1)
            val response = loginUseCases.requestLoginUseCase(
                LoginDto(
                    user.email,
                    user.password
                )
            )
            if (response) nav()
        }
    }

    fun requestRegister(e: String, p1: String, p2: String) {
        viewModelScope.launch {
            val res = loginUseCases.requestRegisterUseCase(
                RegistrationDto(email = e, password1 = p1, password2 = p2)
            )
        }
    }

    fun kakaoLogin(nav: () -> Unit){
        viewModelScope.launch {
            if(handleKakaoLogin()){
                nav()
            }
        }
    }

    private suspend fun handleKakaoLogin(): Boolean = suspendCoroutine<Boolean> { continuation ->
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("##77", "카카오계정으로 로그인 실패99", error)
                continuation.resume(false)
            } else if (token != null) {
                Log.i("##77", "카카오계정으로 로그인 성공 ${token.accessToken}")
                continuation.resume(true)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("##77", "카카오톡으로 로그인 실패55", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i("##77", "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


    fun getUser() {
        viewModelScope.launch {
            loginUseCases.getUserUseCase()
        }
    }

    private fun checkLogin() =
        viewModelScope.launch {
            isLogin = loginUseCases.checkLoginUseCase()
        }
}

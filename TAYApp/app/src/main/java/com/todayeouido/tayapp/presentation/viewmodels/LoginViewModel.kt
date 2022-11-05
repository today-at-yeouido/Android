package com.todayeouido.tayapp.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.todayeouido.tayapp.R
import com.todayeouido.tayapp.data.pref.model.toState
import com.todayeouido.tayapp.data.remote.dto.login.RegistrationDto
import com.todayeouido.tayapp.domain.use_case.AuthGoogleUseCase
import com.todayeouido.tayapp.domain.use_case.LoginUseCases
import com.todayeouido.tayapp.domain.use_case.pref.GetThemeModeUseCase
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.presentation.states.LoginUserUiState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.todayeouido.tayapp.BuildConfig
import com.todayeouido.tayapp.domain.use_case.pref.GetTextSizeModelUseCae
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    private val authGoogleUseCase: AuthGoogleUseCase,
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val getTextSizeModelUseCae: GetTextSizeModelUseCae,
    application: Application
) : AndroidViewModel(application) {

    var user by mutableStateOf(LoginUserUiState())
        private set

    var isLogin = mutableStateOf(false)
        private set

    /**
     * 로그인을 한번이라도 시도했는가?
     */
    var isTryLogin = mutableStateOf(false)
        private set

    init {
        checkLogin()
        getPref()
    }

    private val context = application.applicationContext

    private fun getPref() {
        viewModelScope.launch {
            UserState.mode = getThemeModeUseCase()
            UserState.textSize.value = getTextSizeModelUseCae()
        }
    }

    fun requestLogin(e: String, p1: String) {
        viewModelScope.launch {
            isTryLogin.value = true
            user = user.copy(email = e, password = p1)
            val response = loginUseCases.requestLoginUseCase(
                user.email, user.password
            )
            if (response) {
                isLogin.value = true
            }
        }
    }

    fun requestRegister(e: String, p1: String, p2: String) {
        viewModelScope.launch {
            val res = loginUseCases.requestRegisterUseCase(
                RegistrationDto(email = e, password1 = p1, password2 = p2)
            )
        }
    }

    fun getUserEmail(e: String) {
        user = LoginUserUiState(e, "")
    }

    private fun checkLogin() =
        viewModelScope.launch {
            isLogin.value = loginUseCases.checkLoginUseCase()
            if (isLogin.value) {
                val user = loginUseCases.getUserUseCase()
                UserState.user = user.toState()
            }
        }

    fun kakaoLogin(context: Context) {
        viewModelScope.launch {
            val accessToken = handleKakaoLogin(context)
            val res = loginUseCases.requestSnsUseCase("kakao", accessToken!!)
            if (res) isLogin.value = true
            Log.d("##12", "카카오 $res, 로그인스테이트 ${isLogin.value}")
        }
    }

    fun naverLogin() {
        viewModelScope.launch {
            val accessToken = handleNaverLogin()
            val res = loginUseCases.requestSnsUseCase("naver", accessToken)
            if (res) isLogin.value = true
        }
    }


    fun googleLogin(account: GoogleSignInAccount) {
        viewModelScope.launch {
            val serverAuthCode = account.serverAuthCode
            val accessToken = authGoogleUseCase(
                authCode = serverAuthCode!!,
                clientId = BuildConfig.GOOGLE_CLIENT_ID,
                clientSecret = BuildConfig.GOOGLE_CLIENT_SECRET
            )

            val res = loginUseCases.requestSnsUseCase("google", accessToken)
            if (res) isLogin.value = true
        }
    }

    fun getGoogleLoginAuth(): GoogleSignInClient {
        val clientId = BuildConfig.GOOGLE_CLIENT_ID

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestServerAuthCode(clientId)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    private suspend fun handleKakaoLogin(context: Context): String? =
        suspendCancellableCoroutine { continuation ->
            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("##88", "카카오계정으로 로그인 실패 $error")

                } else if (token != null) {
                    continuation.resume(token.accessToken)
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e("##88", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Log.d("##88","카카오톡 권한 거부?")
                            return@loginWithKakaoTalk
                        }
                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        continuation.resume(token.accessToken)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }

    /**
     * 로그인
     * authenticate() 메서드를 이용한 로그인 */
    suspend fun handleNaverLogin(): String = suspendCoroutine<String> { continuation ->

        var naverToken: String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        /** OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
                continuation.resume(naverToken ?: "")

            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()

            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }

}

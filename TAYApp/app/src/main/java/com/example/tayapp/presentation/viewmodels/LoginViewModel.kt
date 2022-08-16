package com.example.tayapp.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.R
import com.example.tayapp.TayApplication
import com.example.tayapp.data.remote.dto.login.LoginDto
import com.example.tayapp.data.remote.dto.login.RegistrationDto
import com.example.tayapp.domain.use_case.AuthGoogleUseCase
import com.example.tayapp.domain.use_case.LoginUseCases
import com.example.tayapp.presentation.states.LoginState
import com.example.tayapp.presentation.states.LoginUserUiState
import com.example.tayapp.presentation.states.UserInfo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    private val authGoogleUseCase: AuthGoogleUseCase,
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

    private fun checkLogin() =
        viewModelScope.launch {
            isLogin = loginUseCases.checkLoginUseCase()
            if (isLogin) {
                loginUseCases.getUserUseCase()
            }
        }

    fun kakaoLogin(nav: () -> Unit) {
        viewModelScope.launch {
            val s = handleKakaoLogin()
            val a = loginUseCases.requestSnsUseCase("kakao", s!!)
            Log.d("##99", "viewModel token $s")
            if (a) {
                nav()
            }
        }
    }

    fun naverLogin(nav: () -> Unit) {
        viewModelScope.launch {
            val s = startNaverLogin()
            Log.d("##99", "viewModel token $s")
            val a = loginUseCases.requestSnsUseCase("naver", s)
            if (a) {
                nav()
            }
        }
    }



    fun googleLogin(account: GoogleSignInAccount, nav: () -> Unit){
        viewModelScope.launch {
            val user = account.serverAuthCode
            val a = authGoogleUseCase(
                authCode = user!!,
                clientId = context.getString(R.string.google_client_ID),
                clientSecret = context.getString(R.string.google_client_secret)
            )


            Log.d("##88", "구글 로그인 실패 $user")
            val b = loginUseCases.requestSnsUseCase("google", a)
            if (b) {
                nav()
            }
        }
    }

    fun getGoogleLoginAuth(): GoogleSignInClient {
        val clientId = context.getString(R.string.google_client_ID)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            .requestServerAuthCode(clientId)
            .requestIdToken(clientId)
            .requestId()
            .requestProfile()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    private suspend fun handleKakaoLogin(): String? =
        suspendCancellableCoroutine { continuation ->
            // 카카오계정으로 로그인 공통 callback 구성
// 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("##88", "카카오계정으로 로그인 실패 $error")

                } else if (token != null) {
                    Log.i("##88", "카카오계정으로 로그인 성공 ${token.accessToken}")
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
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i("##88", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }

    /**
     * 로그인
     * authenticate() 메서드를 이용한 로그인 */
    suspend fun startNaverLogin(): String = suspendCoroutine<String> { continuation ->

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

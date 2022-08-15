package com.example.tayapp.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.TayApplication
import com.example.tayapp.data.remote.dto.login.LoginDto
import com.example.tayapp.data.remote.dto.login.RegistrationDto
import com.example.tayapp.domain.use_case.LoginUseCases
import com.example.tayapp.presentation.states.LoginUserUiState
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
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.log

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

    fun kakaoLogin(nav: () -> Unit) {
        viewModelScope.launch {
            if (handleKakaoLogin()) {
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


    fun naverLogin() {
        startNaverLogin()
    }

    /**
     * 로그인
     * authenticate() 메서드를 이용한 로그인 */
    fun startNaverLogin() {

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
                Log.d("##99","token $naverToken")
//                viewModelScope.launch{
//                    loginUseCases.requestSnsUseCase(naverToken ?: "")
//                }
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
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

    /**
     * 로그아웃
     * 애플리케이션에서 로그아웃할 때는 다음과 같이 NaverIdLoginSDK.logout() 메서드를 호출합니다. */
    private fun startNaverLogout() {
        NaverIdLoginSDK.logout()
    }

    /**
     * 연동해제
     * 네이버 아이디와 애플리케이션의 연동을 해제하는 기능은 다음과 같이 NidOAuthLogin().callDeleteTokenApi() 메서드로 구현합니다.
    연동을 해제하면 클라이언트에 저장된 토큰과 서버에 저장된 토큰이 모두 삭제됩니다.
     */
    private fun startNaverDeleteToken() {
        NidOAuthLogin().callDeleteTokenApi(context, object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.

            }

            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }

            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
            }
        })
    }
}

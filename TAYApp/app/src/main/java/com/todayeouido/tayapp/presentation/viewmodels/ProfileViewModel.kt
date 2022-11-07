package com.todayeouido.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todayeouido.tayapp.domain.use_case.login.RequestLogoutUseCase
import com.todayeouido.tayapp.domain.use_case.pref.SaveThemeModeUseCase
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.presentation.states.UserInfo
import com.todayeouido.tayapp.utils.NoConnectivityException
import com.todayeouido.tayapp.utils.SocialConstants.KAKAO
import com.todayeouido.tayapp.utils.SocialConstants.NAVER
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.todayeouido.tayapp.domain.use_case.login.DeleteUserInfoUseCase
import com.todayeouido.tayapp.domain.use_case.pref.SaveTextSizeModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: RequestLogoutUseCase,
    private val saveThemeModeUseCase: SaveThemeModeUseCase,
    private val saveTextSizeUseCase: SaveTextSizeModelUseCase,
    private val deleteUserInfoUseCase: DeleteUserInfoUseCase
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            try {
                if (logoutUseCase(UserState.user.refreshToken).last()) {
                    when (UserState.user.sns) {
                        NAVER -> {
                            NaverIdLoginSDK.logout()
                        }
                        KAKAO -> {
                            UserApiClient.instance.logout { error ->
                                if (error != null) {
                                    Log.e("##88", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                                } else {
                                    Log.i("##88", "로그아웃 성공. SDK에서 토큰 삭제됨")
                                }
                            }
                        }
                    }
                    UserState.user = UserInfo()
                }
            } catch (e: NoConnectivityException) {
                if (UserState.user.sns.isEmpty() && UserState.user.id != "Guest") UserState.user =
                    UserInfo()
                Log.e("##88", "로그아웃 실패. SDK에서 토큰 삭제됨")
            }
        }
    }

    /**
     * 회원탈퇴
     */
    fun withdraw() {
        viewModelScope.launch {
            try {
                if (deleteUserInfoUseCase(UserState.user.refreshToken).last()) {
                    when (UserState.user.sns) {
                        NAVER -> {
                            NaverIdLoginSDK.logout()
                        }
                        KAKAO -> {
                            UserApiClient.instance.logout { error ->
                                if (error != null) {
                                    Log.e("##88", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                                } else {
                                    Log.i("##88", "로그아웃 성공. SDK에서 토큰 삭제됨")
                                }
                            }
                        }
                    }
                    UserState.user = UserInfo()
                }
            } catch (e: NoConnectivityException) {
                if (UserState.user.sns.isEmpty() && UserState.user.id != "Guest") UserState.user =
                    UserInfo()
                Log.e("##88", "로그아웃 실패. SDK에서 토큰 삭제됨")
            }
        }

    }

    fun saveTextSize(size: Float){
        viewModelScope.launch {
            saveTextSizeUseCase(size)
        }
    }

    fun saveThemeMode(mode: String) {
        viewModelScope.launch {
            saveThemeModeUseCase(mode)
        }
    }
}
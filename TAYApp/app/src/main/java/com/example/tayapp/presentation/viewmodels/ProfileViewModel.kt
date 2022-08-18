package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.login.RequestLogoutUseCase
import com.example.tayapp.domain.use_case.mode.SaveThemeModeUseCase
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.states.UserInfo
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: RequestLogoutUseCase,
    private val saveThemeModeUseCase: SaveThemeModeUseCase
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            if (logoutUseCase(UserState.user.refreshToken).last()) {
                when (UserState.user.sns) {
                    "naver" -> {
                        NaverIdLoginSDK.logout()
                    }
                    "kakao" -> {
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
        }
    }

    fun saveThemeMode(mode: String) {
        viewModelScope.launch {
            saveThemeModeUseCase(mode)
        }
    }
}
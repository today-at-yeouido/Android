package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.login.RequestLogoutUseCase
import com.example.tayapp.presentation.states.LoginState
import com.example.tayapp.presentation.states.UserInfo
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val logoutUseCase: RequestLogoutUseCase) :
    ViewModel() {

    fun logout() {
        viewModelScope.launch {
            Log.d("##99","리프래시 ${LoginState.user.refreshToken}")
            if (logoutUseCase(LoginState.user.refreshToken)) {
                LoginState.user = UserInfo()
                NaverIdLoginSDK.logout()
            }
        }
    }
}
package com.todayeouido.tayapp

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TayApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        val naverClientId = BuildConfig.NAVER_CLIENT_ID
        val naverClientSecret = BuildConfig.NAVER_CLIENT_SECRET
        val naverClientName = getString(R.string.social_login_info_naver_client_name)

        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret, naverClientName)
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    companion object {
        lateinit var instance: Application
        fun getApplication() = instance
    }
}

package com.example.tayapp

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TayApplication:Application(){
    init {
        instance = this
    }

    companion object {
        lateinit var instance: Application
        fun cxt() : Context = instance.applicationContext
    }
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들


        val naverClientId = getString(R.string.social_login_info_naver_client_id)
        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
        val naverClientName = getString(R.string.social_login_info_naver_client_name)


        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)
        // Kakao SDK 초기화
        KakaoSdk.init(this, "c323d5d48d4d822eaa2a0fbd6e3ca567")
    }
}

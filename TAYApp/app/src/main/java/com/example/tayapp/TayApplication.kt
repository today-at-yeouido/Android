package com.example.tayapp

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
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

        // Kakao SDK 초기화
        KakaoSdk.init(this, "46348017a67389ced8975f0a92251cdd")
    }
}

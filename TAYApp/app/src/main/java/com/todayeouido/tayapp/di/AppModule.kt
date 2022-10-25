package com.todayeouido.tayapp.di

import android.content.Context
import android.util.Log
import com.todayeouido.tayapp.BuildConfig
import com.todayeouido.tayapp.data.pref.PrefDataSource
import com.todayeouido.tayapp.data.remote.*
import com.todayeouido.tayapp.data.remote.Constants.BASE_URL_DEBUG
import com.todayeouido.tayapp.data.remote.Constants.BASE_URL_RELEASE
import com.todayeouido.tayapp.data.remote.Constants.GOOGLE_URL
import com.todayeouido.tayapp.utils.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @AuthInterceptorOkHttpClient
    @Provides
    fun providesAuthInterceptor(
        pref: PrefDataSource
    ): Interceptor = Interceptor { chain ->

        val token = runBlocking(Dispatchers.IO) {
            pref.getAccessToken().first().let {
                if (it.isNotEmpty()) "Bearer $it" else ""
            }
        }

        val newRequest = chain
            .request()
            .newBuilder()
            .apply {
                if (token.isNotBlank()) {
                    addHeader(Constants.AUTHORIZATION, token)
                    Log.d("##99", "di 토큰 ${token}")
                }
            }
            .build()

        return@Interceptor chain
            .proceed(newRequest)
    }

    @NetworkInterceptor
    @Provides
    fun provideNetworkConnectionInterceptor(
        @ApplicationContext context: Context
    ): Interceptor = NetworkInterceptor(context)

    @LoggingInterceptor
    @Provides
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor {
            Log.d("Loggin", "Logging $it")
        }.setLevel(HttpLoggingInterceptor.Level.HEADERS)



    @Singleton
    @Provides
    fun providesOkHttpClient(
        @AuthInterceptorOkHttpClient interceptor: Interceptor,
        @NetworkInterceptor networkInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(interceptor)
        .addInterceptor(loggingInterceptor)
        .build()


    //Release MODE : 포트번호 81
    //Debug MODE : 포트번호 80
    @TayRetrofit
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(if(BuildConfig.DEBUG) BASE_URL_DEBUG else BASE_URL_RELEASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @GoogleAuthRetrofit
    @Provides
    fun provideGoogleRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GOOGLE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    @Provides
    fun provideGoogleApi(
        @GoogleAuthRetrofit retrofit: Retrofit
    ): GoogleApi {
        return retrofit.create(GoogleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRegistrationApi(
        @TayRetrofit retrofit: Retrofit
    ): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGetBillApi(
        @TayRetrofit retrofit: Retrofit
    ): BillApi {
        return retrofit.create(BillApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserSettingApi(
        @TayRetrofit retrofit: Retrofit
    ): UserSettingApi {
        return retrofit.create(UserSettingApi::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptorOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NetworkInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LoggingInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GoogleAuthRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TayRetrofit
}
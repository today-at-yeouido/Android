package com.example.tayapp.di

import android.util.Log
import com.example.tayapp.data.pref.PrefDataSource
import com.example.tayapp.data.remote.BillApi
import com.example.tayapp.data.remote.Constants
import com.example.tayapp.data.remote.Constants.BASE_URL
import com.example.tayapp.data.remote.Constants.GOOGLE_URL
import com.example.tayapp.data.remote.GoogleApi
import com.example.tayapp.data.remote.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
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

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor {
            Log.d("Loggin", "Logging $it")
        }.setLevel(HttpLoggingInterceptor.Level.HEADERS)

    @Singleton
    @Provides
    fun providesOkHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    @Named("normal")
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named("google")
    fun provideGoogleRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GOOGLE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideGoogleApi(
        @Named("google") retrofit: Retrofit
    ): GoogleApi {
        return retrofit.create(GoogleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRegistrationApi(
        @Named("normal") retrofit: Retrofit
    ): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGetBillApi(
        @Named("normal") retrofit: Retrofit
    ): BillApi {
        return retrofit.create(BillApi::class.java)
    }
}
package com.example.tayapp.di

import android.app.Application
import androidx.room.Room
import com.example.tayapp.data.local.TayDatabase
import com.example.tayapp.data.pref.LoginPref
import com.example.tayapp.data.remote.RegisterApi
import com.example.tayapp.utils.Constants
import com.example.tayapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): TayDatabase {
        return Room.databaseBuilder(
            app,
            TayDatabase::class.java,
            "tay_db"
        )
//            .addTypeConverter()// TypeConverter 추가 예정
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthInterceptor(
        pref : LoginPref
    ): Interceptor = Interceptor { chain ->
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(Constants.AUTHORIZATION, LoginPref.TOKEN)
            .build()

        pref.getLoginUser()
        return@Interceptor chain
            .proceed(newRequest)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


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
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideRegisterApi(
        retrofit: Retrofit
    ): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

}
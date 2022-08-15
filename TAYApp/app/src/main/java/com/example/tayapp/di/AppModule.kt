package com.example.tayapp.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.tayapp.TayApplication
import com.example.tayapp.data.local.TayDatabase
import com.example.tayapp.data.pref.PrefDataSource
import com.example.tayapp.data.remote.BillApi
import com.example.tayapp.data.remote.Constants
import com.example.tayapp.data.remote.Constants.BASE_URL
import com.example.tayapp.data.remote.LoginApi
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
        pref: PrefDataSource
    ): Interceptor = Interceptor { chain ->

        val token = runBlocking(Dispatchers.IO) {
            pref.getAccessToken().first()?.let {
                "Bearer $it"
            } ?: ""
        }

        val newRequest = chain
            .request()
            .newBuilder()
            .apply {
                if(token.isNotBlank())addHeader(Constants.AUTHORIZATION, token)
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
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideRegistrationApi(
        retrofit: Retrofit
    ): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGetBillApi(
        retrofit: Retrofit
    ): BillApi {
        return retrofit.create(BillApi::class.java)
    }
}
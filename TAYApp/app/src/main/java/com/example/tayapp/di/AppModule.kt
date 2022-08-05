package com.example.tayapp.di

import android.app.Application
import androidx.room.Room
import com.example.tayapp.data.local.TAYDatabase
import com.example.tayapp.data.remote.RegisterApi
import com.example.tayapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TAYDatabase {
        return Room.databaseBuilder(
            app,
            TAYDatabase::class.java,
            "tay+db"
        )
//            .addTypeConverter()// TypeConverter 추가 예정
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory() 추가예정
            .build()
    }

    @Provides
    @Singleton
    fun provideRegisterApi(
        retrofit: Retrofit
    ): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

}
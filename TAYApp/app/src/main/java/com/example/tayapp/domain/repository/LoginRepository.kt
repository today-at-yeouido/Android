package com.example.tayapp.domain.repository

import com.example.tayapp.data.repository.LoginRepositoryImpl
import com.example.tayapp.domain.model.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

interface LoginRepository {

    suspend fun getLoginUser(): User
    suspend fun setLoginUser(user: User)
}

/** 인터페이스 주입을 위한 모듈 */
@InstallIn(SingletonComponent::class)
@Module
abstract class LoginRepoModule {

    @Binds
    abstract fun bindLoginRepo(
        loginImpl: LoginRepositoryImpl
    ): LoginRepository
}
package com.example.tayapp.data.repository

import com.example.tayapp.domain.model.User
import com.example.tayapp.domain.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

interface LoginRepository {

    suspend fun getLoginUser(): User
    suspend fun setLoginUser(user: User)
}


@InstallIn(SingletonComponent::class)
@Module
abstract class LoginRepoModule {

    @Binds
    abstract fun bindLoginRepo(
        loginImpl: LoginRepositoryImpl
    ): LoginRepository
}
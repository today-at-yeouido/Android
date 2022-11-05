package com.todayeouido.tayapp.di

import com.todayeouido.tayapp.data.repository.*
import com.todayeouido.tayapp.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
abstract class InterfaceModule {

    @Binds
    abstract fun provideGetBillRepository(
        getBillRepoImpl: GetBillRepositoryImpl
    ): GetBillRepository

    @Binds
    abstract fun bindLoginRepo(
        loginImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun provideRecentSearchImpl(
        repository: RecentSearchRepositoryImpl
    ) : RecentSearchRepository

    @Binds
    abstract fun provideThemeModeImpl(
        repository: UserPrefRepositoryImpl
    ): UserPrefRepository
}
package com.todayeouido.tayapp.domain.repository

import com.todayeouido.tayapp.data.repository.RecentSearchRepositoryImpl
import com.todayeouido.tayapp.data.repository.ThemeModeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface ThemeModeRepository {

    suspend fun getThemeMode(): String

    suspend fun saveThemeMode(mode: String)
}
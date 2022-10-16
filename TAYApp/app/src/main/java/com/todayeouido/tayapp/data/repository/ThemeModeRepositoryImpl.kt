package com.todayeouido.tayapp.data.repository

import com.todayeouido.tayapp.data.pref.PrefDataSource
import com.todayeouido.tayapp.domain.repository.ThemeModeRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ThemeModeRepositoryImpl @Inject constructor(private val pref: PrefDataSource) :
    ThemeModeRepository {

    override suspend fun getThemeMode(): String =
        pref.getThemeMode().first()

    override suspend fun saveThemeMode(mode: String) {
        pref.saveThemeMode(mode)
    }
}
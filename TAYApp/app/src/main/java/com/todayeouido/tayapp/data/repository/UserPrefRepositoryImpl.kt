package com.todayeouido.tayapp.data.repository

import com.todayeouido.tayapp.data.pref.PrefDataSource
import com.todayeouido.tayapp.domain.repository.UserPrefRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPrefRepositoryImpl @Inject constructor(private val pref: PrefDataSource) :
    UserPrefRepository {
    override suspend fun getTextSize(): Float = pref.getTextSize().first()

    override suspend fun saveTextSize(size: Float) {
        pref.saveTextSize(size)
    }

    override suspend fun getThemeMode(): String =
        pref.getThemeMode().first()

    override suspend fun saveThemeMode(mode: String) {
        pref.saveThemeMode(mode)
    }
}
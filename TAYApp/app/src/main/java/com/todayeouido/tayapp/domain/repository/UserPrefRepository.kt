package com.todayeouido.tayapp.domain.repository

interface UserPrefRepository {
    suspend fun getTextSize(): Float

    suspend fun saveTextSize(size: Float)

    suspend fun getThemeMode(): String

    suspend fun saveThemeMode(mode: String)
}
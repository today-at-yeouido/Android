package com.example.tayapp.domain.repository

import com.example.tayapp.data.repository.RecentSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {
    suspend fun saveRecentSearchTerm(query: String)
//    suspend fun recentSearchTerm(): String
    val recentSearchTerm :Flow<String>
}
package com.example.tayapp.data.repository

import com.example.tayapp.data.pref.PrefDataSource
import com.example.tayapp.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val pref: PrefDataSource
) : RecentSearchRepository {

    //    override suspend fun recentSearchTerm(): String {
//        return pref.getRecentSearchTerm().first()
//    }
    override val recentSearchTerm: Flow<String>
        get() = pref.getRecentSearchTerm()

    override suspend fun saveRecentSearchTerm(query: String) {
        pref.saveRecentSearchTerm(query)
    }
}
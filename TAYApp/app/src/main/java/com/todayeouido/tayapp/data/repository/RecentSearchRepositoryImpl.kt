package com.todayeouido.tayapp.data.repository

import com.todayeouido.tayapp.data.pref.PrefDataSource
import com.todayeouido.tayapp.domain.repository.RecentSearchRepository
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
package com.example.tayapp.domain.use_case.recent

import android.util.Log
import com.example.tayapp.domain.repository.RecentSearchRepository
import javax.inject.Inject

class SaveRecentSearchUseCase @Inject constructor(private val repository: RecentSearchRepository) {
    suspend operator fun invoke(query: String, index: Int) {
        val list = query.split("&").toMutableList()
        list.removeAt(index)
        val saveSearchTerm =
            list.joinToString("&").replace("[\\[+,\\]]".toRegex(), "")
        repository.saveRecentSearchTerm(saveSearchTerm)
    }

    suspend operator fun invoke(recentTerm: String, query: String) {
        val list = recentTerm.split("&").toMutableList()

        if (list.contains(query)) {
            list.remove(query)
        }

        val recentSearchString =
            list.joinToString("&").replace("[\\[+,\\]]".toRegex(), "")
        val saveSearchTerm =
            if (recentSearchString.isBlank()) query else "$query&$recentSearchString".trim()

        Log.d("##44", "saveSearch $saveSearchTerm")
        Log.d("##44", "recentSearch $recentSearchString")
        repository.saveRecentSearchTerm(saveSearchTerm)
    }
}
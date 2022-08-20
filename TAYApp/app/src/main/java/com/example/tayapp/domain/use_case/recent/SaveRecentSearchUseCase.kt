package com.example.tayapp.domain.use_case.recent

import com.example.tayapp.domain.repository.RecentSearchRepository
import javax.inject.Inject

class SaveRecentSearchUseCase @Inject constructor(private val repository: RecentSearchRepository) {
    suspend operator fun invoke(query: String, index: Int) {
        val list = query.split(" ").toMutableList()
        list.removeAt(index)
        val saveSearchTerm = list.toString().replace("[\\[+,\\]]".toRegex(), "")
        repository.saveRecentSearchTerm(saveSearchTerm)
    }

    suspend operator fun invoke(recentTerm: String, query: String) {
        val list = recentTerm.split(" ").toMutableList()

        if (list.contains(query)) {
            list.remove(query)
        }

        val recentSearchString = list.toString().replace("[\\[+,\\]]".toRegex(), "")
        val saveSearchTerm = "$query $recentSearchString".trim()

        repository.saveRecentSearchTerm(saveSearchTerm)
    }
}
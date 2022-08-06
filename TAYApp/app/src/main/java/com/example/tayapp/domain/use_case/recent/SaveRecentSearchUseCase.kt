package com.example.tayapp.domain.use_case.recent

import com.example.tayapp.domain.repository.RecentSearchRepository
import javax.inject.Inject

class SaveRecentSearchUseCase @Inject constructor(private val repository: RecentSearchRepository) {
    suspend operator fun invoke(query: String) {
        repository.saveRecentSearchTerm(
            query
        )
    }
}
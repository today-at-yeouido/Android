package com.example.tayapp.domain.use_case.recent

import com.example.tayapp.domain.repository.RecentSearchRepository
import javax.inject.Inject

class RemoveRecentSearchUseCase @Inject constructor(private val repository: RecentSearchRepository) {

    suspend operator fun invoke(query: String) {
        repository.saveRecentSearchTerm(
            query
                .substring(1, query.length - 1)
                .replace(",", "")
        )
    }
}

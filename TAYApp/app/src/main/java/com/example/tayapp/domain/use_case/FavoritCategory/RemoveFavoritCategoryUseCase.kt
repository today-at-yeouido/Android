package com.example.tayapp.domain.use_case.FavoritCategory

import com.example.tayapp.domain.repository.FavoritCategoryRepository
import javax.inject.Inject


class RemoveFavoritCategoryUseCase @Inject constructor(private val repository: FavoritCategoryRepository) {
    suspend operator fun invoke(favoritCategorySet: Set<String>) {
        repository.saveFavoritCategory(favoritCategorySet)
    }
}

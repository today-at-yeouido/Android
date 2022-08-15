package com.example.tayapp.domain.use_case.FavoritCategory


import com.example.tayapp.domain.repository.FavoritCategoryRepository
import com.example.tayapp.domain.repository.RecentSearchRepository
import javax.inject.Inject


class GetFavoritCategoryUseCase @Inject constructor(private val repository: FavoritCategoryRepository) {
    operator fun invoke() = repository.favoritCategory
}
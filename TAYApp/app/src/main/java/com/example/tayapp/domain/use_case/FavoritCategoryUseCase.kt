package com.example.tayapp.domain.use_case

import com.example.tayapp.domain.use_case.FavoritCategory.GetFavoritCategoryUseCase
import com.example.tayapp.domain.use_case.FavoritCategory.RemoveFavoritCategoryUseCase
import com.example.tayapp.domain.use_case.FavoritCategory.SaveFavoritCategoryUseCase
import javax.inject.Inject

data class FavoritCategoryUseCase @Inject constructor(
    val getFavoritCategoryUseCase: GetFavoritCategoryUseCase,
    val saveFavoritCategoryUseCase: SaveFavoritCategoryUseCase,
    val removeFavoritCategoryUseCase: RemoveFavoritCategoryUseCase
)
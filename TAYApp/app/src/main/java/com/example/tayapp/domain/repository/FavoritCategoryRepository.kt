package com.example.tayapp.domain.repository

import com.example.tayapp.data.repository.FavoritCategoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow


interface FavoritCategoryRepository {
    suspend fun saveFavoritCategory(favoritCategorySet: Set<String>)
    val favoritCategory : Flow<Set<String>>
}



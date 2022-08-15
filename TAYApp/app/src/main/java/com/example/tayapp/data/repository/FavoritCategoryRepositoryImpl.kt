package com.example.tayapp.data.repository


import com.example.tayapp.data.pref.PrefDataSource
import com.example.tayapp.domain.repository.FavoritCategoryRepository
import com.example.tayapp.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritCategoryRepositoryImpl @Inject constructor(
    private val pref: PrefDataSource
) : FavoritCategoryRepository {

    override val favoritCategory: Flow<Set<String>>
        get() = pref.getFavoritCategory()

    override suspend fun saveFavoritCategory(favoritCategorySet: Set<String>) {
        pref.saveFavoritCategory(favoritCategorySet)
    }
}
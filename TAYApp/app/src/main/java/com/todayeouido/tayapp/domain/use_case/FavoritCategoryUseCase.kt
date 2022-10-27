package com.todayeouido.tayapp.domain.use_case

import com.todayeouido.tayapp.domain.use_case.favoritCategory.*
import javax.inject.Inject

data class FavoritCategoryUseCase @Inject constructor(
    val getUserFavoriteCommitteeUseCase: GetUserFavoriteCommitteeUseCase,
    val postUserFavoriteCommitteeUseCase: PostUserFavoriteCommitteeUseCase
)
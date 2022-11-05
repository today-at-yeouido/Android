package com.todayeouido.tayapp.domain.use_case.pref

import com.todayeouido.tayapp.domain.repository.UserPrefRepository
import javax.inject.Inject

class GetTextSizeModelUseCae @Inject constructor(private val repository: UserPrefRepository) {
    suspend operator fun invoke(): Float{
        return repository.getTextSize()
    }
}
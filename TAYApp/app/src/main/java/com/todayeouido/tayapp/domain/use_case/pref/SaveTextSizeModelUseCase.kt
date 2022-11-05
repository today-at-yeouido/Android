package com.todayeouido.tayapp.domain.use_case.pref

import com.todayeouido.tayapp.domain.repository.UserPrefRepository
import javax.inject.Inject

class SaveTextSizeModelUseCase @Inject constructor(private val repository: UserPrefRepository) {
    suspend operator fun invoke(mode: Float) {
        repository.saveTextSize(mode)
    }
}
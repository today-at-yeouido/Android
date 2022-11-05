package com.todayeouido.tayapp.domain.use_case.pref

import com.todayeouido.tayapp.domain.repository.UserPrefRepository
import javax.inject.Inject

class SaveThemeModeUseCase @Inject constructor(private val repository: UserPrefRepository) {
    suspend operator fun invoke(mode: String) {
        repository.saveThemeMode(mode)
    }
}
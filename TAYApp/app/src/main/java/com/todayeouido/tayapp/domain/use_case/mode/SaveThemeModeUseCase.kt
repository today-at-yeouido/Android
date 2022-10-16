package com.todayeouido.tayapp.domain.use_case.mode

import com.todayeouido.tayapp.domain.repository.ThemeModeRepository
import javax.inject.Inject

class SaveThemeModeUseCase @Inject constructor(private val repository: ThemeModeRepository) {
    suspend operator fun invoke(mode: String) {
        repository.saveThemeMode(mode)
    }
}
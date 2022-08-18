package com.example.tayapp.domain.use_case.mode

import com.example.tayapp.domain.repository.ThemeModeRepository
import javax.inject.Inject

class SaveThemeModeUseCase @Inject constructor(private val repository: ThemeModeRepository) {
    suspend operator fun invoke(mode: String) {
        repository.saveThemeMode(mode)
    }
}
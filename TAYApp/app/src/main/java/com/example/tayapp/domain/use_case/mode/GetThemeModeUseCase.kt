package com.example.tayapp.domain.use_case.mode

import com.example.tayapp.domain.repository.ThemeModeRepository
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(private val repository: ThemeModeRepository) {
    suspend operator fun invoke(): String{
        return repository.getThemeMode()
    }
}
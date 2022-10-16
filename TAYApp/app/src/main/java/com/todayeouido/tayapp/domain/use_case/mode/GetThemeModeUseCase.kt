package com.todayeouido.tayapp.domain.use_case.mode

import com.todayeouido.tayapp.domain.repository.ThemeModeRepository
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(private val repository: ThemeModeRepository) {
    suspend operator fun invoke(): String{
        return repository.getThemeMode()
    }
}
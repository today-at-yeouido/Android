package com.example.tayapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.FavoritCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritCategoryViewModel @Inject constructor(private val useCase: FavoritCategoryUseCase) :
    ViewModel()  {

    var favoritCategoryState = MutableStateFlow(FavoritCategoryState(isLoading = true))
        private set


    init {
        getfavoritCategory()
    }

    fun getfavoritCategory() {
        viewModelScope.launch {
            val result = useCase.getFavoritCategoryUseCase()
            favoritCategoryState.update {
                it.copy(favoritCategory = result.first(), isLoading = false )
            }
        }
    }


    fun clickCategory(string: String) {
        favoritCategoryState.update {
            if(favoritCategoryState.value.favoritCategory.contains(string)){
                it.copy(favoritCategory = favoritCategoryState.value.favoritCategory.minusElement(string), isLoading = false )
            }else{
                it.copy(favoritCategory = favoritCategoryState.value.favoritCategory.plus(string), isLoading = false )
            }
        }
        viewModelScope.launch{
            useCase.saveFavoritCategoryUseCase(favoritCategoryState.value.favoritCategory)
        }
    }

}

data class FavoritCategoryState(
    val favoritCategory: Set<String> = emptySet(),
    val isLoading: Boolean
)
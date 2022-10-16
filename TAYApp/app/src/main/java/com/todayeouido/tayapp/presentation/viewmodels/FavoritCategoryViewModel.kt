package com.todayeouido.tayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todayeouido.tayapp.domain.use_case.FavoritCategoryUseCase
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private fun getfavoritCategory() {
        if (UserState.isLogin()) {
            useCase.getUserFavoriteCommitteeUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        favoritCategoryState.update {
                            it.copy(favoritCategory = result.data!!, isLoading = false )
                        }

                    }
                    is Resource.Error -> {
                        favoritCategoryState.update {
                            it.copy(isLoading = false, error = true )
                        }
                    }
                    is Resource.Loading -> {
                        favoritCategoryState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.NetworkConnectionError -> {
                        UserState.network = false
                    }
                }
            }.launchIn(viewModelScope)
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
    }

    fun saveCategory(){
        viewModelScope.launch {
            useCase.postUserFavoriteCommitteeUseCase(favoritCategoryState.value.favoritCategory).collect() { it ->
                when (it) {
                    is Resource.Success -> Log.d("관심법안", "관심법안 성공")
                    is Resource.Error -> Log.d("관심법안", "관심법안 실패")
                    is Resource.Loading -> Log.d("관심법안", "관심법안 올리는중")
                }
            }
        }
    }


}

data class FavoritCategoryState(
    val favoritCategory: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val error: Boolean = false,
)
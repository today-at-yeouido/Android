package com.example.tayapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.use_case.GetBillGroupUseCase
import com.example.tayapp.domain.use_case.search.GetSearchResultUseCase
import com.example.tayapp.presentation.navigation.Destinations
import com.example.tayapp.presentation.navigation.GROUP_BILL
import com.example.tayapp.presentation.navigation.GroupBillParcelableModel
import com.example.tayapp.presentation.states.GroupUiState
import com.example.tayapp.presentation.states.SearchState
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getBillGroupUseCase: GetBillGroupUseCase,
    private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

    var groupState = MutableStateFlow(GroupUiState(isLoading = true))
        private set
    val groupId: Int = savedStateHandle.get<Int>(Destinations.GROUP_ID)!!

    init {
        getBillGroup()
    }

    private fun getBillGroup() {
        if (UserState.isLogin()) {
            getBillGroupUseCase(groupId.toString()).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        groupState.update {
                            it.copy(
                                billName = result.data!!.billName,
                                bills = result.data!!.bills,
                                isLoading = false
                            )
                        }

                    }
                    is Resource.Error -> {
                        groupState.update {
                            it.copy(
                                error = result.message ?: "An unexpected error",
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        groupState.update {
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
}
package com.example.tayapp.data.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tayapp.domain.model.Bill
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.NoConnectivityException
import com.example.tayapp.utils.UnAuthorizationError
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RecentBillPagingResource @Inject constructor(
    private val getBillRepository: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) : PagingSource<Int, Bill>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Bill> {

        return flowLoad(params).last()
    }

    override fun getRefreshKey(state: PagingState<Int, Bill>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun flowLoad(params: LoadParams<Int>): Flow<LoadResult<Int, Bill>> = flow {
        val nextPage = params.key ?: 1
        try {
            val billListResponse = getBillRepository.getBillRecent(nextPage)
            when (billListResponse.code()) {
                200 -> {
                    val billList = billListResponse.body()!!.map { it.toDomain() }
                    emit(
                        LoadResult.Page(
                            data = billList,
                            prevKey = if (nextPage == 1) null else nextPage - 1,
                            nextKey = nextPage.plus(1)
                        )
                    )
                }
                401 -> {
                    checkLoginUseCase()
                    throw UnAuthorizationError()
                }
                else -> {
                    emit(LoadResult.Error(Throwable(billListResponse.errorBody().toString())))
                }
            }
        } catch (e: NoConnectivityException) {
            emit(LoadResult.Error(Throwable(e.message)))
        }
    }.retryWhen { cause, attempt ->
        Log.d("##88", "401 Error, unAuthorization token")
        cause is UnAuthorizationError || attempt < 3
    }
}
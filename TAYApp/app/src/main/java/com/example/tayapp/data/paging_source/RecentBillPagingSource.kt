package com.example.tayapp.data.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tayapp.domain.model.Bill
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import javax.inject.Inject

class RecentBillPagingResource @Inject constructor(
    private val getBillRepository: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) : PagingSource<Int, Bill>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Bill> {

        val nextPage = params.key ?: 1
        val p = suspend { val a = getBillRepository.getBillRecent(nextPage)
            val b = a.body()!!.map { it.toDomain() }
            b
        }
        val billListResponse = getBillRepository.getBillRecent(nextPage)

        return when (billListResponse.code()) {
            200 -> {
                val billList = billListResponse.body()!!.map { it.toDomain() }
                LoadResult.Page(
                    data = billList,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage.plus(1)
                )
            }
            401 -> {
                checkLoginUseCase()
                val billListResponseE = getBillRepository.getBillRecent(nextPage)
                val billList = billListResponseE.body()!!.map { it.toDomain() }
                Log.d("##88", "에러에러")
                LoadResult.Page(
                    data = billList,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage.plus(1)
                )
            }
            else -> {
                LoadResult.Error(Throwable(billListResponse.errorBody().toString()))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Bill>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
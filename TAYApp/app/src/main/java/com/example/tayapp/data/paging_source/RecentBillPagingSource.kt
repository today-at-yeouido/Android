package com.example.tayapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.domain.repository.GetBillRepository

class RecentBillPagingResource(
    private val getBillRepository: GetBillRepository
) : PagingSource<Int, BillDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BillDto> {
        return try {
            val nextPage = params.key ?: 1
            val billListResponse = getBillRepository.getBillRecent(nextPage)
            LoadResult.Page(
                data = billListResponse,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BillDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
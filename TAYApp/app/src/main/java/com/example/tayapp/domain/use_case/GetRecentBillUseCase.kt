package com.example.tayapp.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tayapp.data.paging_source.RecentBillPagingResource
import com.example.tayapp.domain.model.Bill
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentBillUseCase
    @Inject constructor(private val recentBillPagingResource: RecentBillPagingResource) {

    operator fun invoke(): Flow<PagingData<Bill>> = Pager(PagingConfig(pageSize = 1)) {
        recentBillPagingResource
    }.flow

}
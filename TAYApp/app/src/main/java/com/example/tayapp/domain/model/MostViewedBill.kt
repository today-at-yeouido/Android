package com.example.tayapp.domain.model

import android.util.Log
import com.example.tayapp.data.remote.dto.bill.MostViewedBillDto

data class MostViewedBill(
    val bill: Bill,
    val news: List<News>
) {
    data class Bill(
        val billName: String,
        val billType: Int,
        val committee: List<CommitteeInfo?>,
        val id: Int,
        val proposer: String,
        val status: String,
    )
}

fun MostViewedBillDto.toDomain(): MostViewedBill = MostViewedBill(
    bill = MostViewedBill.Bill(
        billName = this.billSummary.billName,
        billType = this.billSummary.billType,
        committee = listOf(
            CommitteeInfo(
                "a", "b", "c", "d"
            )
        ),
        id = this.billSummary.id,
        proposer = this.billSummary.proposer,
        status = this.billSummary.status
    ),
    news = if (news.isNotEmpty()) news.map {
        Log.d("##88", "뉴스 뉴스 ${it}")
        it.toDomain()
    } else emptyList()
)
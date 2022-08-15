package com.example.tayapp.domain.model

import com.example.tayapp.data.remote.dto.bill.MostViewedBillDto

data class MostViewedBill(
    val bill: Bill,
    val news: List<New>
) {
    data class Bill(
        val billName: String,
        val billType: Int,
        val committee: List<Committee?>,
        val id: Int,
        val proposer: String,
        val status: String,
    )

    data class New(
        val newsFrom: String,
        val newsLink: String,
        val newsName: String,
        val pubDate: String
    )
}

fun MostViewedBillDto.toDomain(): MostViewedBill = MostViewedBill(
    bill = MostViewedBill.Bill(
        billName = this.billSummary.billName,
        billType = this.billSummary.billType,
        committee =  listOf(Committee(
            "a","b","c","d"
        )),
        id = this.billSummary.id,
        proposer = this.billSummary.proposer,
        status = this.billSummary.status
    ),
    news = this.news.map {
        MostViewedBill.New(
            newsFrom = it.newsFrom,
            newsLink = it.newsLink,
            newsName = it.newsName,
            pubDate = it.pubDate
        )
    }
)
package com.example.tayapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HomeBillDto(
    @SerializedName("mostViewedBill")
    val mostViewedBill: List<BillDto>,
    @SerializedName("recentCreatedBill")
    val recentCreatedBill: List<BillDto>,
    @SerializedName("recommendBill")
    val recommendBill: List<BillDto>,
    @SerializedName("userRecentViewedBill")
    val userRecentViewedBill: List<BillDto>
)

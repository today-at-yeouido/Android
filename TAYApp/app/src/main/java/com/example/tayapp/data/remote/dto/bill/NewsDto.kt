package com.example.tayapp.data.remote.dto.bill

data class NewsDto(
    val pubDate: String,
    val newsFrom: String,
    val newsName: String,
    val newsLink: String,
    val imgUrl: List<String>
)

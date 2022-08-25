package com.example.tayapp.domain.model

import com.example.tayapp.data.remote.dto.bill.NewsDto

data class News(
    val newsFrom: String,
    val newsLink: String,
    val newsName: String,
    val pubDate: String,
    val imgUrl: List<String>?
)

fun NewsDto.toDomain() = News(
    newsFrom = newsFrom,
    newsLink = newsLink,
    newsName = newsName,
    pubDate = pubDate,
    imgUrl = imgUrl
)
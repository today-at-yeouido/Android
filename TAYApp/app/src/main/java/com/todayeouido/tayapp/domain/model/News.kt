package com.todayeouido.tayapp.domain.model

import com.todayeouido.tayapp.data.remote.dto.bill.NewsDto

data class News(
    val newsFrom: String,
    val newsLink: String,
    val newsName: String,
    val pubDate: String,
    val imgUrl: String?
)

fun NewsDto.toDomain() = News(
    newsFrom = newsFrom,
    newsLink = newsLink,
    newsName = newsName,
    pubDate = pubDate,
    imgUrl = imgUrl
)
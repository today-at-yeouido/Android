package com.example.tayapp.data.remote.dto

data class TableDto(
    val amendment: List<CurrentDto>,
    val current: List<CurrentDto>
)
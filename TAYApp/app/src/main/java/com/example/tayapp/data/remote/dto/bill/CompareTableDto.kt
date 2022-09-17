package com.example.tayapp.data.remote.dto.bill

data class CompareTableDto(
    val amendment: List<AmendmentDto>,
    val current: List<CurrentDto>
)
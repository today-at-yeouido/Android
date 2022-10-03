package com.example.tayapp.data.remote.dto.bill

data class CompareTableDto(
    val amendment: List<AmendmentDto>,
    val current: List<CurrentDto>
)

interface RowDto {
    val status: String
    val text: String
    val underline: Boolean
    val table: List<String>
}
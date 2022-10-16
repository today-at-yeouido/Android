package com.todayeouido.tayapp.data.remote.dto.bill

data class CompareTableDto(
    val amendment: List<AmendmentDto>,
    val current: List<CurrentDto>
)

interface RowDto {
    val status: String
    val text: String
    val underline: Boolean
    val table: List<TableDto>
}

data class TableDto(val row: String, val col: String, val text: String)
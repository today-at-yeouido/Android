package com.todayeouido.tayapp.data.remote.dto.bill

data class AmendmentDto(
    override val status: String = "",
    override val text: String = "",
    override val underline: Boolean = false,
    override val table: List<TableDto> = emptyList()
) : RowDto
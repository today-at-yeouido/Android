package com.example.tayapp.data.remote.dto.bill

data class CurrentDto(
    override val status: String = "",
    override val text: String = "",
    override val underline: Boolean = false,
    override val table: List<String> = emptyList()
) : RowDto
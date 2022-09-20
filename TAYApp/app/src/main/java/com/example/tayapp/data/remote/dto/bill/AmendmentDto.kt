package com.example.tayapp.data.remote.dto.bill

data class AmendmentDto(
    override val text: String = "",
    override val underline: Boolean = false
) : RowDto
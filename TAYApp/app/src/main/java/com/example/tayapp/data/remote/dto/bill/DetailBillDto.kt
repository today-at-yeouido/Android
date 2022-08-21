package com.example.tayapp.data.remote.dto.bill

data class DetailBillDto(
    val billNum: String = "",
    val billName: String = "",
    val billType: Int = 0,
    val isReflect: Boolean = false,
    val proposeDt: String = "",
    val proposerKind: String = "",
    val proposer: String = "",
    val status: String = "",
    val summary: String? = "",
    val hwpurl: String? = "",
    val pdfurl: String? = "",
    val views: Int = 0,
    val isScrapped: Boolean = false,
    val committeeInfo: List<CommitteeDto?> = emptyList(),
    val jurisdictionInfo: List<JurisdictionInfoDto> = emptyList(),
    val plenaryInfo: List<PlenaryInfoDto> = emptyList(),
    val transferInfo: List<TransferInfoDto> = emptyList(),
    val announceInfo: List<AnnounceInfoDto> = emptyList(),
    val reflect_info: List<ReflectInfoDto> = emptyList(),
    val news: List<NewsDto> = emptyList()
)
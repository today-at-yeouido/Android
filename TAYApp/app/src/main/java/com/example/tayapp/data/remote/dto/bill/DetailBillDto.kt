package com.example.tayapp.data.remote.dto.bill

data class DetailBillDto(
    val billNum: String,
    val billName: String,
    val billType: Int,
    val isReflect: Boolean,
    val proposeDt: String,
    val proposerKind: String,
    val proposer: String,
    val committeeID: Int,
    val status: String,
    val summary: String,
    val hwpurl: String,
    val pdfurl: String,
    val views: Int,
    val committeeInfo: List<CommitteeDto?>,
    val jurisdictionInfo: List<JurisdictionInfoDto>,
    val plenaryInfo: List<PlenaryInfoDto>,
    val transferInfo: List<TransferInfoDto>,
    val announceInfo: List<AnnounceInfoDto>,
    val reflect_info: List<ReflectInfoDto>
    
)
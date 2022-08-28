package com.example.tayapp.domain.model

import com.example.tayapp.data.remote.dto.bill.*

data class DetailBill(
    val billName: String = "",
    val billType: Int = 0,
    val isReflect: Boolean = false,
    val proposeDt: String = "",
    val proposer: String = "",
    val status: String = "",
    val summary: String? = "",
    val hwpurl: String? = "",
    val pdfurl: String? = "",
    val views: Int = 0,
    val isScrapped: Boolean = false,
    val isPlenary: Boolean = false,
    val plenaryInfo: PlenaryInfo = PlenaryInfo(),
    val committeeInfo: CommitteeInfo = CommitteeInfo(),
    val progressItems: List<BillProgressItem> = emptyList(),
    val reflect_info: List<ReflectInfoDto> = emptyList(),
    val news: List<News> = emptyList()
)

fun DetailBillDto.toDomain(): DetailBill =
    DetailBill(
        billName = billName,
        billType = billType,
        isReflect = isReflect,
        proposeDt = proposeDt,
        proposer = proposer,
        status = status,
        summary = summary,
        hwpurl = hwpurl,
        pdfurl = pdfurl,
        views = views,
        plenaryInfo = plenaryInfo.firstOrNull()?.toDomain() ?: PlenaryInfo(),
        committeeInfo = committeeInfo.firstOrNull()?.toDomain() ?: CommitteeInfo(),
        isScrapped = isScrapped,
        isPlenary = plenaryInfo.isNotEmpty(),
        progressItems = getProgress(this),
        reflect_info = reflect_info,
        news = news.map { it.toDomain() }
    )


private fun getProgress(billDto: DetailBillDto): List<BillProgressItem> {
    val progressList: MutableList<BillProgressItem> =
        mutableListOf(BillProgressItem("발의", billDto.proposeDt))

    if (billDto.committeeInfo.firstOrNull()?.procResult == "회송") {
        progressList.add(BillProgressItem("심사", billDto.committeeInfo.firstOrNull()?.submitDt))
        progressList.add(BillProgressItem("회송", billDto.committeeInfo.firstOrNull()?.procDt))
    } else {
        when (billDto.status) {
            "철회", "본회의불부의" -> {
                progressList.add(
                    BillProgressItem(
                        "철회",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
            }
            "대안반영폐기" -> {
                progressList.add(
                    BillProgressItem(
                        "심사",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
                progressList.add(
                    BillProgressItem(
                        "대안",
                        billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
            }
            "부결" -> {
                progressList.add(
                    BillProgressItem(
                        "심사",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
                progressList.add(BillProgressItem("심의", billDto.plenaryInfo.firstOrNull()?.prsntDt))
                progressList.add(BillProgressItem("부결", billDto.plenaryInfo.firstOrNull()?.prsntDt))
            }
            "본회의의결" -> {
                progressList.add(
                    BillProgressItem(
                        "심사",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
                progressList.add(BillProgressItem("가결", billDto.plenaryInfo.firstOrNull()?.prsntDt))
            }
            else -> {
                progressList.add(
                    BillProgressItem(
                        "심사",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
                progressList.add(BillProgressItem("심의", billDto.plenaryInfo.firstOrNull()?.prsntDt))
                progressList.add(BillProgressItem("가결", billDto.plenaryInfo.firstOrNull()?.prsntDt))
                progressList.add(
                    BillProgressItem(
                        "정부이송",
                        billDto.transferInfo.firstOrNull()?.transDt
                    )
                )
                progressList.add(
                    BillProgressItem(
                        "공포",
                        billDto.announceInfo.firstOrNull()?.announceDt
                    )
                )
            }
        }
    }

    return progressList
}

data class BillProgressItem(
    val title: String,
    val date: String?
)
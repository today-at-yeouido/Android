package com.example.tayapp.domain.use_case

import android.util.Log
import com.example.tayapp.data.remote.dto.bill.AmendmentDto
import com.example.tayapp.data.remote.dto.bill.ComparisonTableDto
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.NoConnectivityException
import com.example.tayapp.utils.Resource
import com.example.tayapp.utils.UnAuthorizationError
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

class GetBillTableUseCase @Inject constructor(
    private val repo: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    suspend operator fun invoke(id: Int) = flow {
        emit(Resource.Loading())
        try {
            val response = repo.getBillTable(id)
            when (response.code()) {
                200 -> {
                    val tableDto = response.body()!!
                    val table = getBillTable(tableDto)
                    emit(Resource.Success(table))
                }
                401 -> {
                    checkLoginUseCase()
                    throw UnAuthorizationError()
                }
                400 -> emit(
                    Resource.Error(
                        response.errorBody().toString() ?: "An unexpected error occurred"
                    )
                )
                else -> emit(Resource.Error("Couldn't reach server"))
            }
        } catch (e: NoConnectivityException) {
            emit(Resource.NetworkConnectionError(("네트워크 에러")))
        }
    }.retryWhen { cause, attempt ->
        Log.d("##88", "401 Error, unAuthorization token")
        cause is UnAuthorizationError || attempt < 3
    }
}
private fun getBillTable(table: ComparisonTableDto): BillTable {

    val articleRevisionRegex = Regex("^[제第]\\d+[조條](?!제)")

    /** 법률 이름 저장 */
    var cBillTitle = ""
    var aBillTitle = ""

    /** 문자열, 조 단위로 끊김 */
    var str = ""

    /** 처음 저장되는 조인지 확인 */
    var toggle = false

    /** 조를 담을 리스트 */
    val articleList = arrayListOf<Article>()

    /** 개정 내용 담을 리스트 */
    var rowList = arrayListOf<Row>()

    /** 조 단위로 저장하고 초기화 */
    fun addArticle() {
        if (toggle) {
            val article = setRevisionRow(parseArticle(str), rowList)
            articleList.add(article)
            rowList = arrayListOf()
            str = ""
        } else {
            toggle = true
        }
    }

    /** 개정 정보 체크하고 저장 */
    fun checkRevisionType(
        amendment: AmendmentDto,
        amendmentT: String,
        currentT: String
    ) {
        if (amendment.underline) {
            when {
                /** 항으로 하나만 추가되는 경우 예외처리 */
                (amendmentT.contains(Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")) && amendmentT.length < 2)
                -> {}
                currentT.contains("<신  설>") || currentT.contains("<단서 신설>") -> {
                    rowList.add(Row(amendmentT, currentT, str.length, "신설"))
                }
                amendmentT.contains("<삭  제>") -> {
                    rowList.add(Row(amendmentT, currentT, str.length, "삭제"))
                }
                else -> rowList.add(Row(amendmentT, currentT, str.length, "수정"))
            }
        }
    }

    /** 법률 이름 바뀌었는지 검사 */
    val firstTable = table.compareTable[0]
    val firstCurrent = firstTable.current.filter { it.text.isNotBlank() }
    val firstAmendment = firstTable.amendment.filter { it.text.isNotBlank() }

    for (i in firstAmendment.indices) {
        val current = firstCurrent.getOrNull(i)
        val amendment = firstAmendment[i]
        val currentT = current?.text ?: ""
        val amendmentT = amendment.text
        val s = if (!amendmentT.contains("--")) amendmentT else currentT

        /** 첫 Amendment가 조 형식일 때 */
        if (s.contains(articleRevisionRegex)) {
            addArticle()
            str += s
        }

        /** 첫 Amendment가 조 형식 아닐 때 -> 법률명 바뀐 경우 */
        if (i == 0 && !s.contains(articleRevisionRegex)) {
            cBillTitle = currentT
            aBillTitle = amendmentT
        }


        // underline true 인 애들 위치랑 같이 저장
        checkRevisionType(amendment, amendmentT, currentT)
    }

    for (idx in 1 until table.compareTable.size) {

        val compareTable = table.compareTable[idx]
        val amendmentTable = compareTable.amendment.filter { it.text.isNotBlank() }
        val currentTable = compareTable.current.filter { it.text.isNotBlank() }

        for (aIdx in amendmentTable.indices) {

            val current = currentTable.getOrNull(aIdx)
            val amendment = amendmentTable[aIdx]
            val currentT = current?.text ?: ""
            val amendmentT = amendment.text

            /** 조 단위 신설 예외처리 */
            if (currentT.contains("<신  설>") && amendmentT.contains(articleRevisionRegex)) {
                if (str.isNotBlank()) {
                    addArticle()
                }

                /** 조 단위로 신설되기에 바로 저장한다. */
                articleList.add(parseArticle(amendmentT).copy(type = setOf("신설")))
                continue
            }

            /** 조 단위 삭제 예외처리 */
            if (amendmentT.contains("<삭  제>") && currentT.contains(articleRevisionRegex)) {
                if (str.isNotBlank()) {
                    addArticle()
                }

                /** 조 단위로 삭제되기에 바로 저장한다. */
                articleList.add(parseArticle(currentT).copy(type = setOf("삭제")))
                continue
            }

            val lump = if (!amendmentT.contains("--")) amendmentT else currentT

            /** lump가 조 형식이면 지금까지 저장한 str이 하나의 조 라는 뜻 */
            if (lump.contains(articleRevisionRegex) && aIdx == 0 && str.isNotBlank()) {
                // 법률 제17815호 관세사법 일부개정법률 예외
                if (!str.contains(articleRevisionRegex)) {
                    str = lump
                    continue
                }
                addArticle()
            } else toggle = true
            str += lump

            // underline true 인 애들 위치랑 같이 저장
            checkRevisionType(amendment, amendmentT, currentT)
        }
    }

    /** 마지막 조는 반복문이 끝난 뒤 저장한다. */
    articleList.add(setRevisionRow(parseArticle(str), rowList))

    return BillTable(currentTitle = cBillTitle, amendmentTitle = aBillTitle, article = articleList)
}

/** 개정된 부분들을 적절한 위치(조, 항, 호, 목)에 저장해준다. */
/** textRow가 null인 경우 있나? */
fun setRevisionRow(article: Article, rowArray: ArrayList<Row>): Article {
    // 문자열 길이
    var strSize = 0
    // 인덱스
    var idx = 0
    // 해당 조에서 개정된 부분의 사이즈
    val rowArraySize = rowArray.size

    /**
     *  조 내에서 개정된 타입들을 저장한다.
     *  일부 신설, 일부 삭제, 일부 수정 등등..
     * */
    val revisionTypeSet = mutableSetOf<String>()

    /** 문자열을 추가하고 */
    fun plusLength(textRow: TextRow) {
        val textRowLength = textRow.text.length
        while (idx < rowArraySize) {
            val row = rowArray[idx]
            val rowLocation = row.location
            if (strSize < rowLocation && rowLocation <= strSize + textRowLength) {
                if (idx + 1 <= rowArraySize) {
                    when (row.type) {
                        "신설" -> {
                            revisionTypeSet.add("일부 신설")
                        }
                        "삭제" -> {
                            revisionTypeSet.add("일부 삭제")
                        }
                        "수정" -> {
                            revisionTypeSet.add("일부 수정")
                        }
                    }

                    textRow.row.add(row)
                    idx++
                } else break
            } else break
        }
        strSize += textRowLength
    }

    plusLength(article.title)

    plusLength(article.text)

    article.subParagraph?.forEach { sub ->
        plusLength(sub.text)

        sub.subParagraph?.forEach { item ->
            plusLength(item.text)
        }
    }

    article.paragraph?.forEach { par ->
        plusLength(par.text)

        par.subParagraph?.forEach { sub ->
            plusLength(sub.text)

            sub.subParagraph?.forEach { item ->
                plusLength(item.text)
            }
        }
    }

    return article.copy(type = revisionTypeSet)
}

/** 조를 분석해서 문자열을 적절한 위치에 잘라서 저장한다. */
fun parseArticle(str: String): Article {

    val articleRegex = Regex("^[제第]\\d+[조條].+\\)\\s")

    // 항을 저장할 리스트
    var articleParagraph: List<Paragraph>? = null
    // 목을 저장할 리스트
    var articleSubParagraph: List<SubParagraph>? = null

    /** 조에서 항 전 부분 */
    val beforeParagraph = str.substringBefore('①')

    /** 조에서 목 전 부분 */
    val beforeSubParagraph = str.substringBefore("1.")

    /** 항 전 부분과 목 전 부분 중에서 작은 문자열이 순수 조에대한 문자열이다. */
    val articlePart =
        if (beforeSubParagraph.length < beforeParagraph.length) beforeSubParagraph else beforeParagraph

    /** 조 부분에서 조 이름 정규표현식에 맞는 부분 추출 */
    val title = articleRegex.find(articlePart)!!.value

    val articleTitle = TextRow(title)
    val articleText = TextRow(articlePart.replace(title, ""))

    /** 조 이름을 뺀 나머지 부분 */
    var s = str.replace(title, "")

    /** 항이 있는 경우 */
    if (s.contains('①')) {
        /** 항 분석해서 얻음 */
        articleParagraph = parseParagraph(s.substring(s.indexOf('①')))

        /** 항 이전 부분만 추출 */
        s = s.substringBefore('①')
    }

    /** 호가 있는 경우 */
    if (s.contains("1.")) {
        /** 예외 때문에 index - 1을 넘긴다 */
        articleSubParagraph = parseSubParagraph(s.substring(s.indexOf("1.") - 1))
    }

    return Article(
        title = articleTitle,
        text = articleText,
        paragraph = articleParagraph,
        subParagraph = articleSubParagraph
    )
}

// 항
fun parseParagraph(str: String): MutableList<Paragraph> {
    val paragraphRegex = Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")

    /** 해당 문자열에서 항 접두사 추출 */
    val prefixList = paragraphRegex.findAll(str).map { it.value }.toList()

    /** 반환할 항 리스트 */
    val paragraphList = arrayListOf<Paragraph>()

    var paragraphChunk: String

    var idx = 0
    while (idx < prefixList.size) {
        /**  */
        paragraphChunk = if (idx < prefixList.size - 1) str.substring(
            str.indexOf(prefixList[idx]),
            str.indexOf(prefixList[idx + 1])
        ) else str.substring(str.indexOf(prefixList[idx]))

        if (paragraphChunk.isEmpty()) continue

        if ((paragraphChunk.contains('∼') || paragraphChunk.contains('⋅')) && paragraphChunk.length < 10) {
            if (idx < prefixList.size - 2) {
                paragraphChunk =
                    str.substring(str.indexOf(prefixList[idx]), str.indexOf(prefixList[idx + 2]))
                idx++
            } else {
                paragraphChunk = str.substring(str.indexOf(prefixList[idx]))
                idx++
            }
        }

        var subParagraph: List<SubParagraph>? = null

        /** 항에 호가 있는지 검사 */
        if (paragraphChunk.contains("1.")) {
            /** 호가 있으면 호 저장 */
            subParagraph = parseSubParagraph(paragraphChunk)
            paragraphChunk = paragraphChunk.substringBefore("1.")
        }
        val text = TextRow(paragraphChunk)
        paragraphList.add(Paragraph(text = text, subParagraph = subParagraph))

        idx++
    }

    return paragraphList
}

//호
fun parseSubParagraph(str: String): List<SubParagraph> {
    val subParagraphRegex = Regex("\\s[123456890]+\\.")
    val subParagraphList = arrayListOf<SubParagraph>()

    val kkk = subParagraphRegex.findAll(str).map { it.value }.toList()

    var before: String

    var k = 0
    while (k < kkk.size) {
        before = if (k < kkk.size - 1) str.substring(
            str.indexOf(kkk[k]),
            str.indexOf(kkk[k + 1])
        ) else str.substring(str.indexOf(kkk[k]))

        if (before.isEmpty()) continue

        if ((before.contains('∼') || before.contains('⋅')) && before.length < 10) {
            if (k < kkk.size - 2) {
                before = str.substring(str.indexOf(kkk[k]), str.indexOf(kkk[k + 2]))
                k++
            } else {
                before = str.substring(str.indexOf(kkk[k]))
                k++
            }
        }

        var subParagraph: List<SubParagraph>? = null

        if (before.contains("가.")) {
            subParagraph = parseItem(before)
            before = before.substringBefore("가.")
        }

        val text = TextRow(before)
        subParagraphList.add(SubParagraph(subParagraph = subParagraph, text = text))

        k++
    }
    return subParagraphList
}

//목
fun parseItem(str: String): List<SubParagraph> {
    val itemRegex = Regex("[가나다라마바사아자차카타]\\.")
    val itemList = arrayListOf<SubParagraph>()

    val kkk = itemRegex.findAll(str).map { it.value }.toList()

    var before: String

    var k = 0
    while (k < kkk.size) {
        before = if (k < kkk.size - 1) str.substring(
            str.indexOf(kkk[k]),
            str.indexOf(kkk[k + 1])
        ) else str.substring(str.indexOf(kkk[k]))

        if (before.isEmpty()) continue

        if ((before.contains('∼') || before.contains('⋅')) && before.length < 10) {
            if (k < kkk.size - 2) {
                before = str.substring(str.indexOf(kkk[k]), str.indexOf(kkk[k + 2]))
                k++
            } else {
                before = str.substring(str.indexOf(kkk[k]))
                k++
            }
        }

        val text = TextRow(before)
        itemList.add(SubParagraph(text = text))
        k++
    }

    return itemList
}

data class Chapter(
    val currentTitle: String? = null,
    val amendmentTitle: String? = null,
    val articles: List<Article>
)

data class BillTable(
    val currentTitle: String,
    val amendmentTitle: String,
    val article: List<Article>
)

data class Article(
    val title: TextRow,
    val text: TextRow,
    val type: Set<String> = emptySet(),
    val paragraph: List<Paragraph>? = null,
    val subParagraph: List<SubParagraph>? = null,
)

data class Paragraph(
    val text: TextRow,
    val subParagraph: List<SubParagraph>? = null,
)

// 호, 목
data class SubParagraph(
    val text: TextRow,
    val subParagraph: List<SubParagraph>? = null,
)

data class TextRow(
    val text: String,
    val row: MutableList<Row> = mutableListOf()
)

data class Row(
    val text: String,
    val cText: String,
    val location: Int,
    val type: String
)
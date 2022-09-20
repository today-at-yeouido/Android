package com.example.tayapp.domain.use_case

import android.util.Log
import com.example.tayapp.data.remote.dto.bill.ComparisonTableDto
import com.example.tayapp.data.remote.dto.bill.RowDto
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


fun getBillTable(table: ComparisonTableDto): BillTable {

    val articleRevisionRegex = Regex("^[제第]\\d+[조條](?!제)")

    /** 법률 이름 저장 */
    var cBillTitle = ""
    var aBillTitle = ""

    /** 문자열, 조 단위로 끊김 */
    var str = ""

    /** 처음 저장되는 조인지 확인 */
    var toggle = false

    /** 조를 담을 리스트 */
    val articleList = arrayListOf<Condolences>()

    /** 개정 내용 담을 리스트 */
    var rowList = arrayListOf<Row>()

    /** 조 단위로 저장하고 초기화 */
    fun addArticle() {
        if (toggle) {
            val article = setRevision(parseCondolences(str), rowList)
            articleList.add(article)
            rowList = arrayListOf()
            str = ""
        } else {
            toggle = true
        }
    }

    fun checkArticleCase(currentT: String, amendmentT: String): Boolean {
        /** 조 단위 신설 예외처리 */
        if (currentT.contains("<신  설>") && amendmentT.contains(articleRevisionRegex)) {
            if (str.isNotBlank()) {
                addArticle()
            }
            /** 조 단위로 신설되기에 바로 저장한다. */
            articleList.add(parseArticle(amendmentT).copy(type = setOf("신설")))
            return true
        }
        /** 조 단위 삭제 예외처리 */
        if (amendmentT.contains("<삭  제>") && currentT.contains(articleRevisionRegex)) {
            if (str.isNotBlank()) {
                addArticle()
            }
            /** 조 단위로 삭제되기에 바로 저장한다. */
            articleList.add(parseArticle(currentT).copy(type = setOf("삭제")))
            return true
        }
        return false
    }

    fun getLump(amendmentT: String, currentT: String): String {
        var lump =
            when {
                amendmentT.contains("<삭  제>") -> currentT
                amendmentT.isBlank() -> currentT
                !amendmentT.contains("--") -> amendmentT
                else -> currentT
            }

        if (lump.contains('-')) {
            lump = lump.substringBefore('-') + "⋯⋯ " + lump.substringAfterLast('-')
        }
        return lump
    }

    /** 개정 정보 체크하고 저장 */
    fun checkRevisionType(
        amendment: RowDto,
        amendmentT: String,
        currentT: String
    ) {
        if (amendment.underline) {
            when {
                /** 항으로 하나만 추가되는 경우 예외처리 */
                (amendmentT.contains(Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")) && amendmentT.length < 2)
                -> {
                }
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

    for (i in 0 until table.compareTable.size) {

        val compareTable = table.compareTable[i]
        val amendmentTable = compareTable.amendment.filter { it.text.isNotBlank() }
        val currentTable = compareTable.current.filter { it.text.isNotBlank() }

        val size = amendmentTable.size.coerceAtLeast(currentTable.size)
        for (j in 0 until size) {

            val current = currentTable.getOrNull(j)
            val amendment = amendmentTable.getOrNull(j)
            val currentT = current?.text ?: ""
            val amendmentT = amendment?.text ?: ""

//            if (checkArticleCase(currentT, amendmentT)) continue 조단위 필요없나?

            var lump = getLump(amendmentT, currentT)
            when {
                lump.contains("-  -") -> break
                lump.contains(Regex("^\\d+\\.")) -> lump = " $lump"
            }

            if (i > 0) {
                /** lump가 조 형식이면 지금까지 저장한 str이 하나의 조 라는 뜻 */
                if ((lump.contains(articleRevisionRegex) || lump.contains(legislationRegex) || lump.contains(
                        chapterRegex
                    )) && j == 0 && str.isNotBlank()
                ) {
                    addArticle()
                }
            }

            // underline true 인 애들 위치랑 같이 저장
            checkRevisionType(amendment ?: current!!, amendmentT, currentT)

            if (i > 0) {
                str += lump
            } else {
                /** 첫 Amendment가 조 형식 아닐 때 -> 법률명 바뀐 경우 */
                if (j == 0 && !lump.contains(articleRevisionRegex)) {
                    cBillTitle = currentT
                    aBillTitle = amendmentT
                } else if (lump.contains(articleRevisionRegex)) {
                    /** 첫 Amendment가 조 형식일 때 */
                    addArticle()
                    str += lump
                } else {
                    str += lump
                }
            }
        }
    }

    /** 마지막 조는 반복문이 끝난 뒤 저장한다. */
    articleList.add(setRevision(parseCondolences(str), rowList))

    return BillTable(currentTitle = cBillTitle, amendmentTitle = aBillTitle, article = articleList)
}

fun parseCondolences(str: String): Condolences {
    return if (str.contains(Regex("^[제第]\\d+[조條](?!제)"))) {
        parseArticle(str)
    } else {
        parseBigCondolences(str)
    }
}

fun parseBigCondolences(str: String): BigCondolences {
    return BigCondolences(TextRow(str))
}

fun setRevision(condolences: Condolences, rowArray: ArrayList<Row>): Condolences {
    return if (condolences is Article) {
        setRowArticle(condolences, rowArray)
    } else setRowBigCondolences(condolences as BigCondolences, rowArray)
}

fun setRowBigCondolences(condolences: BigCondolences, rowArray: ArrayList<Row>): BigCondolences {
    for (r in rowArray) {
        condolences.title.row.add(r)
    }
    return condolences
}

/** 개정된 부분들을 적절한 위치(조, 항, 호, 목)에 저장해준다. */
/** textRow가 null인 경우 있나? */
fun setRowArticle(article: Article, rowArray: ArrayList<Row>): Article {
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
            if (strSize <= rowLocation && rowLocation < strSize + textRowLength) {
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

    article.subClause?.forEach { sub ->
        plusLength(sub.text)

        sub.subClause?.forEach { item ->
            plusLength(item.text)
        }
    }

    article.paragraph?.forEach { par ->
        plusLength(par.text)

        par.subClause?.forEach { sub ->
            plusLength(sub.text)

            sub.subClause?.forEach { item ->
                plusLength(item.text)
            }
        }
    }

    return article.copy(type = revisionTypeSet)
}

/** 조를 분석해서 문자열을 적절한 위치에 잘라서 저장한다. */
fun parseArticle(str: String): Article {

    val articleRegex = Regex("(^|.[^\\s])([제第])\\s?\\d{1,4}([조條])[^()]*\\([^)]*\\)")

    // 항을 저장할 리스트
    var articleParagraph: List<SubClause>? = null
    // 목을 저장할 리스트
    var articleSubClause: List<SubClause>? = null

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
    if (s.contains(subParagraphRegex)) {
        /** 예외 때문에 index - 1을 넘긴다 */
        articleSubClause =
            parseSubParagraph(s.substring(s.indexOf(subParagraphRegex.find(s)!!.value) - 1))
    }

    return Article(
        title = articleTitle,
        text = articleText,
        paragraph = articleParagraph,
        subClause = articleSubClause
    )
}


private fun parse(
    prefixList: List<String>,
    str: String,
    subClauseList: ArrayList<SubClause>,
    regex: Regex? = null,
) {
    var chunk: String
    var idx = 0
    var temp = ""
    while (idx < prefixList.size) {
        chunk = if (idx < prefixList.size - 1) {
            val firstIndex = str.indexOf(prefixList[idx])
            str.substring(
                firstIndex,
                str.indexOf(prefixList[idx + 1], firstIndex + 1)
            )
        } else str.substring(str.indexOf(prefixList[idx]))

        chunk = chunk.replace(temp, "")
        temp = chunk

        if (chunk.isEmpty()) continue

        if (chunk.contains(connectiveRegex) && chunk.length < 10) {
            if (idx < prefixList.size - 2) {
                chunk =
                    str.substring(str.indexOf(prefixList[idx]), str.indexOf(prefixList[idx + 2]))
                idx++
            } else {
                chunk = str.substring(str.indexOf(prefixList[idx]))
                idx++
            }
        }

        var subClause: List<SubClause>? = null

        when (regex) {
            itemRegex ->
                if (chunk.contains(regex)) {
                    subClause = parseItem(chunk)
                    chunk = chunk.substringBefore(itemRegex.find(chunk)!!.value)
                }
            subParagraphRegex -> if (chunk.contains(regex)) {
                val subIndex =
                    chunk.indexOf(subParagraphRegex.find(chunk)!!.value) - 1
                /** 호가 있으면 호 저장 */
                subClause =
                    parseSubParagraph(chunk.substring(subIndex))
                chunk = chunk.substring(0, subIndex)
            }
        }

        val text = TextRow(chunk)
        subClauseList.add(SubClause(subClause = subClause, text = text))

        idx++
    }
}

// 항
fun parseParagraph(str: String): MutableList<SubClause> {
    val paragraphRegex = Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")

    /** 해당 문자열에서 항 접두사 추출 */
    val prefixList = paragraphRegex.findAll(str).map { it.value }.toList()

    /** 반환할 항 리스트 */
    val paragraphList = arrayListOf<SubClause>()

    parse(prefixList, str, paragraphList, subParagraphRegex)

    return paragraphList
}

//호
fun parseSubParagraph(str: String): List<SubClause> {
    val subClauseList = arrayListOf<SubClause>()
    val prefix = subParagraphRegex.findAll(str).map { it.value }.toList()
    parse(prefix, str, subClauseList, itemRegex)
    return subClauseList
}

//목
fun parseItem(str: String): List<SubClause> {
    val itemList = arrayListOf<SubClause>()
    val prefixList = itemRegex.findAll(str).map { it.value }.toList()
    parse(prefixList, str, itemList)
    return itemList
}

val itemRegex = Regex("[가나다라마바사아자차카타파하]\\.")
val connectiveRegex = Regex("[∼⋅ㆍ]")
val subParagraphRegex = Regex("(\\s\\d+의\\d+\\.)|(\\s\\d+\\.)")
val legislationRegex = Regex("^법률.+법률\\s?(?!.+)")
val chapterRegex = Regex("^[제第]\\d+[장]")

data class BillTable(
    val currentTitle: String,
    val amendmentTitle: String,
    val article: List<Condolences>
)

interface Condolences{
    val title: TextRow
}


data class BigCondolences(
    override val title: TextRow,
) : Condolences

data class Article(
    override val title: TextRow,
    val text: TextRow,
    val type: Set<String> = emptySet(),
    val paragraph: List<SubClause>? = null,
    val subClause: List<SubClause>? = null,
) : Condolences

// 호, 목
data class SubClause(
    val text: TextRow,
    val subClause: List<SubClause>? = null,
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
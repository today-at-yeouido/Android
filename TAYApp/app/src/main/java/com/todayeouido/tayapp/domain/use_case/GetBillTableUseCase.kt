package com.todayeouido.tayapp.domain.use_case

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.todayeouido.tayapp.data.remote.dto.bill.*
import com.todayeouido.tayapp.domain.repository.GetBillRepository
import com.todayeouido.tayapp.domain.use_case.login.CheckLoginUseCase
import com.todayeouido.tayapp.utils.NoConnectivityException
import com.todayeouido.tayapp.utils.Resource
import com.todayeouido.tayapp.utils.UnAuthorizationError
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
                    val tableDto = response.body()
                    val table = getBillTable(tableDto!!)
                    emit(Resource.Success(table))
                }
                401 -> {
                    checkLoginUseCase()
                    throw UnAuthorizationError()
                }
                400 -> emit(
                    Resource.Error(
                        response.errorBody().toString()
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


/**
 * 신구조문 처리는 3단계로 나눠서 한다.
 *      1. Dto 객체에서 current와 amendment를 비교해서 표시할 편ㆍ장ㆍ절ㆍ관ㆍ조 단위로 문자열을 생성, 바뀌는 부분 체크하는 단계
 *      2. parseCondolences : 문자열을 세분화해 항ㆍ호ㆍ목으로 나누는 단계
 *      3. setRevision : 바뀐 부분을 해당하는 항ㆍ호ㆍ목에 적절하게 넣는 단계
 *
 */
fun getBillTable(table: ComparisonTableDto): BillTable? {

    if (table.compareTable == null) return null

    val articleRevisionRegex = Regex("^[제第]\\d+[조條](?!제)")

    /** 법률 이름 저장 */
    var cBillTitle = ""
    var aBillTitle = ""

    /**
     *  편ㆍ장ㆍ절ㆍ관ㆍ조 단위로 합치는 문자열
     *  개정안을 기준으로 합친다.
     * */
    var str = ""

    /** 처음 저장되는 조인지 확인 */
    var toggle = false

    /** 조를 담을 리스트 */
    val articleList = arrayListOf<Condolences>()

    /** 개정 내용 담을 리스트 */
    var rowList = arrayListOf<Row>()
    var tableList = arrayListOf<TableRow>()

    /**
     * articleList에 Condolences 객체를 추가하는 함수
     *
     * 편ㆍ장ㆍ절ㆍ관ㆍ조 단위로 저장하고 초기화한다.
     * 다음번 문자열이 편ㆍ장ㆍ절ㆍ관ㆍ조 형식이면 이전까지 합친 str을 저장하는 방식
     *
     * 처음 편ㆍ장ㆍ절ㆍ관ㆍ조 형식이 나오면 이전까지 문자열을 합친게 없기에 toggle을 사용해 분기
     *
     * ex)
     * 1번 문자열이
     * "제63조(현금보상 등) ① 손실보상은 다른 법률에 특별한 규정이 있는 경우를 제외하고는"
     * 인 경우 toggle 값을 true로 바꾸고 str에 더해준다.
     *
     * 2번 문자열이
     * "현금으로 지급하여야 한다."
     * 이면 str에 더한다.
     *
     * 3번 문자열이
     * "제78조(이주대책의 수립 등) ① ∼ ④ (생  략)"
     * 라면 편ㆍ장ㆍ절ㆍ관ㆍ조 형식이기에 이전까지 더한 str
     * "제63조(현금보상 등) ① 손실보상은 다른 법률에 특별한 규정이 있는 경우를 제외하고는 현금으로 지급하여야 한다."를
     * 항ㆍ호ㆍ목으로 분해하고 바뀐부분을 저장, 해당 객체를 articleList에 추가한다.
     *
     * */
    fun addArticle() {
        if (toggle) {
            val article = setRevision(parseCondolences(str), rowList, tableList)
            articleList.add(article)
            rowList = arrayListOf()
            tableList = arrayListOf()
            str = ""
        } else {
            toggle = true
        }
    }

    /**
     * str에 더할 문자열(lump)을 얻는 함수
     * 개정안 문자열(amenmentT)가 "--"를 포함하지 않으면(바뀐 부분이 있을 수 있는 경우) amendmentT를 반환하고
     * 포함하는 경우(바뀐 부분이 없는 경우) currentT를 반환하는 것을 기본으로 한다.
     *
     * lump의 경우
     *      1. amendmentT가 "<삭  제>"를 포함하는 경우 -> 삭제 or 일부 삭제
     *          current : [
     *              {
     *                  "underline": true,
     *                  "text": "③ 정보통신서비스 제공자등은 정보통신망을 통보하여야 한다."
     *              }
     *            ]
     *          amendment : [
     *              {
     *                  "underline": true,
     *                  "text": "<삭  제>"
     *               }
     *            ]
     *         "③ 정보통신서비스 제공자등은 정보통신망을 통보하여야 한다." 을 반환해 str에 더해줌
     *
     *      2. amendmentT가 비어있는 경우 -> current의 수와 amendment의 수가 안맞는 경우에 발생 (current가 더 많은 경우)
     *      3. amendmentT에 --가 포함되어있지만 그것이 개정 내용 중 일부라면 -> amendmentT 반환 (아직 해당 경우 못봄)
     *      4. amendmentT에 --가 포함안된 경우 -> 바뀌었을 가능성이 있음, str은 개정안을 기준으로 합치기에 amendmentT를 반환
     *      5. 이외 경우(amendmentT에 --가 포함된 경우) -> 바뀐게 없기에 currentT를 반환
     *
     * 만약 lump에 --가 포함되어 있다면 말줄임표로 대신한다
     * ex) https://www.notion.so/8a56ce9be62244d7bebdf38466aba030 신구조문대비표 둘 다 점선인 경우
     * "제2항에 따른 모니터링 결과 위반사항 확인 시 ------------ 때에는 ---------- 따른 정보통신서비스 제공자"
     * "제2항에 따른 모니터링 결과 위반사항 확인 시 ⋯⋯  때에는 ⋯⋯ 따른 정보통신서비스 제공자"
     */
    fun getLump(amendment: AmendmentDto?, current: CurrentDto?): String {
        val amendmentT = amendment?.text ?: ""
        val currentT = current?.text ?: ""
        var lump =
            when {
                amendmentT.contains("<삭  제>") -> currentT
                amendmentT.isBlank() -> currentT
                amendmentT.contains("--") && amendment?.underline ?: false -> amendmentT
                !amendmentT.contains("--") -> amendmentT
                else -> currentT
            }

        if (lump.contains('-')) {
            lump = lump.substringBefore('-') + "⋯⋯ " + lump.substringAfterLast('-')
        }
        return lump
    }

    /**
     * 바뀐 정보를 체크하고 바뀐 부분이 있다면 Row객체를 생성해서 rowList에 저장하는 함수
     * @param rowDto 기본적으로 amendment가 넘어오지만, 만약 current가 더 긴 경우 current가 넘어옴
     *
     * rowDto에 바뀐 부분이 있다면 바뀐 부분을 저장하는 Row 객체를 생성해서 rowList에 추가한다.
     *
     *      1. 항(① 같은 동그라미 아라비아 숫자)이 추가되면서 true 갯수 불일치 -> current와 amendment 수 맞지않음, 저장안함
     *      예시 https://www.notion.so/Q-A-29651b0b058a436c813cacf1d9b2f83b
     *      후단 신설 예외처리 해야함
     *      2. currentT가 <신  설> 포함 -> 신설 or 일부 신설, Row의 type에 "신설"로 저장
     *      일부 신설인지 조 신설인지는 바뀐 부분을 해당하는 항ㆍ호ㆍ목에 적절하게 넣는 단계(setRevision 함수)에서 판단
     *      3. amendmentT가 <삭  제> 포함 -> 삭제 or 일부 삭제
     *      4. 이외의 경우 -> 일부 수정
     * */
    fun checkRevisionType(
        rowDto: RowDto,
        amendmentT: String,
        currentT: String
    ) {
        if (rowDto.underline) {
            when {
                /** 항으로 하나만 추가되는 경우 예외처리 */
                (amendmentT.contains(Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")) && amendmentT.length < 2)
                -> {
                    return
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
        val amendmentTable =
            compareTable.amendment.filter { it.text.isNotBlank() || it.table.isNotEmpty() }
        val currentTable =
            compareTable.current.filter { it.text.isNotBlank() || it.table.isNotEmpty() }

        /** currentTable 크기와 amendmentTable 크기 중 더 큰 것을 기준으로 반복*/
        val size = amendmentTable.size.coerceAtLeast(currentTable.size)

        for (j in 0 until size) {

            val current = currentTable.getOrNull(j)
            val amendment = amendmentTable.getOrNull(j)
            val currentT = current?.text ?: ""
            val amendmentT = amendment?.text ?: ""

            /** lump는 str에 합칠 Dto에서 얻은 문자열이다. */
            if (current?.status == "table" || amendment?.status == "table") {
                val t = if (current?.status == "table") current else amendment
                val row = t!!.table.maxOf { it.row }
                val col = t.table.maxOf { it.col }
                val data = t.table.map { it.text }
                tableList.add(TableRow(data, row.toInt(), col.toInt(), str.length))
            } else {
                var lump = getLump(amendment, current)

                when {
                    /** 가끔씩 마지막 부분에 -  -이 포함되는 경우 있음 -> 반복문 중단하고 lump를 str에 합치지 않음  */
                    lump.contains("-  -") -> break
                    /** lump가 호로 시작하는 경우 앞에 공백을 추가해준다.
                     * 정규표현식 수정 필요할 수 있음, 앞에 공백이 없고 호로 시작하는 경우에만 공백을 추가하는 것이 좋음.
                     * */
                    lump.contains(Regex("^\\s*((\\d+의\\d+\\.)|(\\d+\\.)|(^\\d+의\\d(?!.+)))")) -> lump =
                        " $lump"
                }

                if (i > 0) {
                    /** lump가 편ㆍ장ㆍ절ㆍ관ㆍ조 형식이면 지금까지 저장한 str을 분해해 articleList에 추가한다.
                     *
                     * 새로운 편ㆍ장ㆍ절ㆍ관ㆍ조는 항상 j == 0 에서만 나온다.
                     * j가 0이 아닌경우는 조의 본문 내용임
                     *
                     * str이 빈 경우 : 연달아서 편ㆍ장ㆍ절ㆍ관ㆍ조 형식인 경우 예외처리 위함.. // 다른 곳에서 처리해서 이제 없어도 될거같기도 하고..?
                     * */
                    if ((lump.contains(articleRevisionRegex)
                                || lump.contains(legislationRegex)
                                || lump.contains(chapterRegex))
                        && j == 0 && str.isNotBlank()
                    ) {
                        addArticle()
                    }
                }

                /** 첫 Amendment가 편ㆍ장ㆍ절ㆍ관ㆍ조 형식 아닐 때 -> 법률명 바뀐 경우 */
                if (i == 0 && str.isBlank() && !(lump.contains(articleRevisionRegex)
                            || lump.contains(legislationRegex)
                            || lump.contains(chapterRegex))
                ) {
                    cBillTitle = currentT
                    aBillTitle = amendmentT
                    continue
                }

                /** 바뀐 사항이 있는지 체크하고, 있다면 rowList에 추가 */
                checkRevisionType(amendment ?: current!!, amendmentT, currentT)

                /** str을 더할 때 첫 Dto인지 아닌지에 따라 다르게 처리한다. */
                str += if (i > 0) {
                    lump
                } else {
                    if (lump.contains(articleRevisionRegex)
                        || lump.contains(legislationRegex)
                        || lump.contains(chapterRegex)
                    ) {
                        /** 첫 Amendment가 편ㆍ장ㆍ절ㆍ관ㆍ조 형식일 때  */
                        addArticle()
                        lump
                    } else {
                        lump
                    }
                }
            }
        }
    }

    /** 마지막 조는 반복문이 끝난 뒤 저장한다. */
    articleList.add(setRevision(parseCondolences(str), rowList, tableList))

    return BillTable(
        currentTitle = cBillTitle,
        amendmentTitle = aBillTitle,
        condolences = articleList
    )
}

/** 2단계 : 문자열을 세분화해 항ㆍ호ㆍ목으로 나누는 단계
 * 조이면 조항호목으로 분리해주고
 * 그 이외(편장절관)라면 BigCondolences 객체로 저장
 * */
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

fun setRevision(
    condolences: Condolences,
    rowArray: ArrayList<Row>,
    tableArray: ArrayList<TableRow>
): Condolences {
    return if (condolences is Article) {
        setRowArticle(condolences, rowArray, tableArray)
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
fun setRowArticle(
    article: Article,
    rowArray: ArrayList<Row>,
    tableArray: ArrayList<TableRow>
): Article {
    // 문자열 길이
    var strSize = 0
    // 인덱스
    var idx = 0
    var tableIdx = 0
    // 해당 조에서 개정된 부분의 사이즈
    val rowArraySize = rowArray.size

    /**
     *  조 내에서 개정된 타입들을 저장한다.
     *  일부 신설, 일부 삭제, 일부 수정 등등..
     * */
    val revisionTypeSet = mutableSetOf<String>()

    /** 문자열을 추가하고 */
    fun plusLength(textRow: TextRow, flag: String = "") {
        val textRowLength = textRow.text.length
        while (idx < rowArraySize) {

            val row = rowArray[idx]
            val rowLocation = row.location

            if (tableArray.isNotEmpty() && tableIdx < tableArray.size) {
                val table = tableArray[tableIdx]
                if (strSize < table.location && table.location <= strSize + textRowLength) {
                    textRow.table.add(table)
                    tableIdx++
                }
            }

            if (strSize <= rowLocation && rowLocation < strSize + textRowLength) {
                if (idx + 1 <= rowArraySize) {
                    when (row.type) {
                        "신설" -> {
                            if (flag == "article") revisionTypeSet.add("신설") else
                                revisionTypeSet.add("일부 신설")
                        }
                        "삭제" -> {
                            if (flag == "article") revisionTypeSet.add("삭제") else
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

    plusLength(article.title, "article")

    plusLength(article.text)

    article.subCondolence?.forEach { sub ->
        plusLength(sub.text)

        sub.subCondolence?.forEach { item ->
            plusLength(item.text)
        }
    }

    article.paragraph?.forEach { par ->
        plusLength(par.text)

        par.subCondolence?.forEach { sub ->
            plusLength(sub.text)

            sub.subCondolence?.forEach { item ->
                plusLength(item.text)
            }
        }
    }

    return article.copy(type = revisionTypeSet)
}

/** 조를 분석해서 문자열을 적절한 위치에 잘라서 저장한다. */
fun parseArticle(str: String): Article {

    val articleRegex = Regex("(^|.\\S)([제第])\\s?\\d{1,4}([조條])[^()]*\\([^)]*\\)")

    // 항을 저장할 리스트
    var articleParagraph: List<SubCondolence>? = null
    // 목을 저장할 리스트
    var articleSubCondolence: List<SubCondolence>? = null

    /** 조에서 항 전 부분 */
    val beforeParagraph = str.substringBefore('①')

    /** 조에서 목 전 부분 */
    val beforeSubParagraph = str.substringBefore("1.")

    /** 항 전 부분과 목 전 부분 중에서 작은 문자열이 순수 조에대한 문자열이다. */
    val articlePart =
        if (beforeSubParagraph.length < beforeParagraph.length) beforeSubParagraph else beforeParagraph

    /** 조 부분에서 조 이름 정규표현식에 맞는 부분 추출 */
    val title = articleRegex.find(articlePart)?.value ?: ""

    val articleTitle = TextRow(title)


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
        articleSubCondolence =
            parseSubParagraph(s.substring(s.indexOf(" 1.")))
        s = s.substringBefore(" 1.")
    }

    val articleText = TextRow(s)

    return Article(
        title = articleTitle,
        text = articleText,
        paragraph = articleParagraph,
        subCondolence = articleSubCondolence
    )
}

/** 분해 함수
 * @param prefixList 항, 호, 목 접두사를 담은 리스트
 * @param str 항, 호, 목의 본문 문자열
 * @param subClauseList
 * @param regex 해당하는 정규표현식
 * */
private fun parse(
    prefixList: List<String>,
    str: String,
    subClauseList: ArrayList<SubCondolence>,
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

        /** (현행과 같음) 이 아니라 본문 내용중 10이하이고 connectiveRegex 포함되면 어떻게..? */
        if (chunk.contains(connectiveRegex) && chunk.length < 10) {
            if (idx < prefixList.size - 2) {
                chunk =
                    str.substring(str.indexOf(prefixList[idx]), str.indexOf(prefixList[idx + 2], str.indexOf(prefixList[idx]) ))
                idx++
            } else {
                chunk = str.substring(str.indexOf(prefixList[idx]))
                idx++
            }
        }

        var subCondolence: List<SubCondolence>? = null

        when (regex) {
            itemRegex ->
                if (chunk.contains("가.")) {
                    subCondolence = parseItem(chunk.substring(chunk.indexOf("가.") - 1))
                    chunk = chunk.substringBefore("가.")
                }
            subParagraphRegex -> if (chunk.contains(regex)) {
                val subIndex =
                    chunk.indexOf(subParagraphRegex.find(chunk)!!.value)
                /** 호가 있으면 호 저장 */
                subCondolence =
                    parseSubParagraph(chunk.substring(subIndex))
                chunk = chunk.substring(0, subIndex)
            }
        }

        val text = TextRow(chunk)
        subClauseList.add(SubCondolence(subCondolence = subCondolence, text = text))

        idx++
    }
}

/** 항 분해 함수 항에 해당하는 부분만 저장한다. */
fun parseParagraph(str: String): MutableList<SubCondolence> {
    val paragraphRegex = Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")

    /** 해당 문자열에서 항 접두사 추출 */
    val prefixList = paragraphRegex.findAll(str).map { it.value }.toList()

    /** 반환할 항 리스트 */
    val paragraphList = arrayListOf<SubCondolence>()

    parse(prefixList, str, paragraphList, subParagraphRegex)

    return paragraphList
}

/** 호 분해 함수 항에 해당하는 부분만 저장한다. */
fun parseSubParagraph(str: String): List<SubCondolence> {
    val subCondolenceList = arrayListOf<SubCondolence>()
    val prefix = subParagraphRegex.findAll(str).map { it.value }.toList()
    parse(prefix, str, subCondolenceList, itemRegex)
    return subCondolenceList
}

/** 목 분해 함수 항에 해당하는 부분만 저장한다. */
fun parseItem(str: String): List<SubCondolence> {
    val itemList = arrayListOf<SubCondolence>()
    val prefixList = itemRegex.findAll(str).map { it.value }.toList()
    parse(prefixList, str, itemList)
    return itemList
}

// 목 정규표현식, 하 이후 부분 추가 필요
private val itemRegex = Regex("[가나다라마바사자아차카타파하]\\.")

// 연결사들 ex) 1. ∼ 4. (현행과 같음) 에서 ∼
private val connectiveRegex = Regex("[∼⋅ㆍ～]")

// 호 정규표현식
private val subParagraphRegex = Regex("(\\s\\d+의\\d+\\.)|(\\s\\d+\\.)")

// 법률 정규표현식
private val legislationRegex = Regex("^법률.+법률\\s?(?!.+)")

// 장 정규표현식
private val chapterRegex = Regex("^[제第]\\d+장")

/**
 * @param currentTitle 바뀌기 전 법률 명, 만약 바뀌지 않았으면 빈 스트링 ""
 * @param amendmentTitle 바뀐 법률 명, 만약 바뀌지 않았으면 빈 스트링 ""
 * @param condolences 해당 법률에서 편ㆍ장ㆍ절ㆍ관ㆍ조 단위의 리스트
 * */
data class BillTable(
    val currentTitle: String,
    val amendmentTitle: String,
    val condolences: List<Condolences>
)

/**
 * 편ㆍ장ㆍ절ㆍ관ㆍ조 명세
 * @param title 편ㆍ장ㆍ절ㆍ관ㆍ조 이름
 * @param type 신설ㆍ삭제ㆍ수정ㆍ정보 저장
 */
interface Condolences {
    val title: TextRow
    val type: Set<String>
}

/**
 * 편ㆍ장ㆍ절ㆍ관 클래스
 */
data class BigCondolences(
    override val title: TextRow,
    override val type: Set<String> = emptySet()
) : Condolences

/**
 * 조 클래스
 * @param title 조 이름
 * @param text 항ㆍ호ㆍ목에 속하지 않는 문자열
 * @param type 신설ㆍ수정ㆍ삭제 여부
 * @param paragraph 항
 * @param subCondolence 호
 * @param showNormal 개정안만 보기 / 현행안과 같이 보기 기능을 위함, true일 때 현행안과 같이 봄
 */
data class Article(
    override val title: TextRow,
    val text: TextRow,
    override val type: Set<String> = emptySet(),
    val paragraph: List<SubCondolence>? = null,
    val subCondolence: List<SubCondolence>? = null,
    val showNormal: MutableState<Boolean> = mutableStateOf(true)
) : Condolences

/**
 *  항ㆍ호ㆍ목 클래스
 * */
data class SubCondolence(
    val text: TextRow,
    val subCondolence: List<SubCondolence>? = null,
)

/**
 * 내용을 저장하는 클래스
 * @param text 개정안 문자열
 * @param row 바뀐 부분 저장함
 * @sample TextRow(text= 5의17. (현행 제5호의7부터 제5호의13까지와 같음) , row=[Row(text=5의17, cText=5의13, location=832, type=수정)])
 * UI에서 개정안만 보여줄 때는 text를 보여주고, 현행안과 같이 보여줘야 할 때 row를 사용한다.
 */
data class TextRow(
    val text: String,
    val row: MutableList<Row> = mutableListOf(),
    val table: MutableList<TableRow> = mutableListOf(),
)

data class TableRow(
    val data: List<String>,
    val row: Int,
    val col: Int,
    val location: Int
)

/**
 * TextRow에서 바뀐 부분을 저장한다.
 * Dto 클래스에서 underline true인 부분 저장
 * @param text 개정안 내용
 * @param cText 현행안 내용
 * @param location 문자열 위치, 개정안 내용 넣을 때 사용
 * @param type 신설, 수정, 삭제 정보 저장
 */
data class Row(
    val text: String,
    val cText: String,
    val location: Int,
    val type: String
)
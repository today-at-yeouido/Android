package com.example.tayapp.domain.use_case

import android.util.Log
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
                    val table = getgeteget(tableDto)
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

fun getgeteget(table: ComparisonTableDto): ArrayList<Article> {


    val articleRevisionRegex = Regex("^[제第]\\d+[조條](?!제)")

    var chapterC = ""
    var chapterA = ""
    var str = ""

    var toggle = false

    val articleList = arrayListOf<Article>()
    var rowList = arrayListOf<Row>()

    /** 법률 이름 바뀌었는지 검사 */
    val firstS = table.compareTable[0]
    val amC = firstS.current.filter { it.text.isNotBlank() }
    val amA = firstS.amendment.filter { it.text.isNotBlank() }

    for (i in amA.indices) {
        val current = amC.getOrNull(i)
        val amendment = amA[i]
        val currentT = current?.text ?: ""
        val amendmentT = amendment.text
        val s = if (!amendmentT.contains("--")) amendmentT else currentT

        if (s.contains(articleRevisionRegex)) {
            if (toggle) {
                val article = setAmendment(str)
                setset(article, rowList)
                articleList.add(article)
                rowList = arrayListOf()
                str = ""
            } else {
                toggle = true
                str = ""
            }
        }

        if (i == 0 && !s.contains(articleRevisionRegex)) {
            chapterC = currentT
            chapterA = amendmentT
        }
        str += s

        // underline true 인 애들 위치랑 같이 저장
//        if (amendment.underline) {
////                    항으로 하나만 추가되는 경우 예외처리
////                    if(amendmentT.contains(Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")))
//            rowList.add(Row(amendmentT, currentT, str.length))
//        }
    }

    for (idx in 1 until table.compareTable.size) {

        val t = table.compareTable[idx]
        val am = t.amendment.filter { it.text.isNotBlank() }
        val ac = t.current.filter { it.text.isNotBlank() }

        var cIdx = 0
        var aIdx = 0

        while (aIdx < am.size) {

            val current = ac.getOrNull(cIdx)
            val amendment = am[aIdx]
            val currentT = current?.text ?: ""
            val amendmentT = amendment.text

            val s = if (!amendmentT.contains("--")) amendmentT else currentT
            if (s.contains(articleRevisionRegex)) {
                if (toggle) {
                    val article = setAmendment(str)
                    setset(article, rowList)
                    articleList.add(article)
                    rowList = arrayListOf()
                    str = ""
                } else {
                    toggle = true
                    str = ""
                }
            }

            str += s

            // underline true 인 애들 위치랑 같이 저장
            if (amendment.underline) {
                /** 항으로 하나만 추가되는 경우 예외처리 */
                when {
                    (amendmentT.contains(Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")) && amendmentT.length < 2)
                    -> str += amendmentT
                    else -> rowList.add(Row(amendmentT, currentT, str.length))
                }
            }
            cIdx ++
            aIdx ++
        }
    }


    val article2 = setAmendment(str)
    setset(article2, rowList)
    articleList.add(article2)

    return articleList
}

/** 바뀐부분 삽입 */
/** textRow가 null인 경우 있나? */
fun setset(article: Article, rowArray: ArrayList<Row>) {
    var strSize = 0
    var idx = 0
    val rowArraySize = rowArray.size

    fun plusLength(textRow: TextRow?) {
        if (textRow == null) {
            return
        }
        val textRowLength = textRow.text.lengthOrZero
        while (idx < rowArraySize) {
            val row = rowArray[idx]
            val rowLocation = row.location
            if (strSize < rowLocation && rowLocation < strSize + textRowLength) {
                if (idx + 1 <= rowArraySize) {
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
}

val String?.lengthOrZero
    get(): Int = this?.length ?: 0


/** 조 ~ 다음 조 시작 전까지 다 넘김 */
// 조
fun setAmendment(str: String): Article {


    val articleRegex = Regex("^[제第]\\d+[조條].+\\)\\s")

    /** 조 이름 */
    val k = str.substringBefore("1.")
    val p = str.substringBefore('①')
    val title = if (k.length < p.length) {
        articleRegex.find(k)!!.value
    } else {
        articleRegex.find(p)!!.value
    }

    val articleTitle = TextRow(title)

    var articleParagraph: List<Paragraph>? = null
    var articleSubParagraph: List<SubParagraph>? = null

    /** 조 이름을 뺀 나머지 부분 */
    var s = str.replace(title, "")

    /** 항이 있는 경우 */
    if (s.contains('①')) {
        /** 항 분석해서 얻음 */
        articleParagraph = getParagraph(s.substring(s.indexOf('①')))

        /** 항 이전 부분만 추출 */
        s = s.substringBefore('①')
    }

    /** 호가 있는 경우 */
    if (s.contains("1.")) {
        articleSubParagraph = getSubParagraph(s.substring(s.indexOf("1.") - 1))
        /** 항, 호 아닌 문자열 */
        s = s.substringBefore("1.")
    }

    /** 항, 호 아닌 문자열 저장 */
    val articleText = TextRow(s)

    return Article(
        title = articleTitle,
        text = articleText,
        paragraph = articleParagraph,
        subParagraph = articleSubParagraph
    )
}

// 항
fun getParagraph(str: String): MutableList<Paragraph> {
    val paragraphRegex = Regex("[①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳]")
    val kkk = paragraphRegex.findAll(str).map { it.value }.toList()

    /** 반환할 항 리스트 */
    val paragraphList = arrayListOf<Paragraph>()

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

        /** 항에 호가 있는지 검사 */
        if (before.contains("1.")) {
            /** 호가 있으면 목 저장 */
            subParagraph = getSubParagraph(before)
            before = before.substringBefore("1.")
        }
        val text = TextRow(before)
        paragraphList.add(Paragraph(text = text, subParagraph = subParagraph))

        k++
    }

    return paragraphList
}

//호
fun getSubParagraph(str: String): List<SubParagraph> {
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
            subParagraph = getItem(before)
            before = before.substringBefore("가.")
        }

        val text = TextRow(before)
        subParagraphList.add(SubParagraph(subParagraph = subParagraph, text = text))

        k++
    }
    return subParagraphList
}

//목
fun getItem(str: String): List<SubParagraph> {
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

data class Article(
    val title: TextRow? = null,
    val text: TextRow? = null,
    val isRemoved: Boolean = false,
    val paragraph: List<Paragraph>? = null,
    val subParagraph: List<SubParagraph>? = null,
)

data class Paragraph(
    val text: TextRow? = null,
    val subParagraph: List<SubParagraph>? = null,
    val isRemoved: Boolean = false,
)

data class TextRow(
    val text: String,
    val row: MutableList<Row> = mutableListOf()
)

data class Row(
    val text: String,
    val cText: String,
    val location: Int
)

// 호, 목
data class SubParagraph(
    val text: TextRow? = null,
    val subParagraph: List<SubParagraph>? = null,
    val isRemoved: Boolean = false,
)
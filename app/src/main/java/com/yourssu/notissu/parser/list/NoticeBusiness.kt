package com.yourssu.notissu.parser.list

import android.util.Log
import com.yourssu.notissu.data.NoticeURL
import com.yourssu.notissu.model.Notice
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.URLEncoder

object NoticeBusiness {

    @JvmStatic
    fun parseListBiz(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.businessBizURL}$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList = ArrayList<String>()
        val urlList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch =
                URLEncoder.encode(keyword, "UTF-8")
            val searchUrl =
                "http://biz.ssu.ac.kr/bbs/list.do?&bId=BBS_03_NOTICE&sc_title=$keywordSearch&page=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            val titleContainers = doc.select("ul[id='bList01']")
            for (product in titleContainers.select("div")) {
                val isContent = product.select("a").first()?.text()?.trim()
                val content = if (isContent.isNullOrEmpty()) "" else isContent

                when (index % 3) {
                    0 -> {
                        // Title
                        Log.d("Notice", content)
                        if (product.select("a").first()?.className() == "fixedPost") {
                            // isNotice
                            isNoticeList.add(true)
                        } else {
                            // normal
                            isNoticeList.add(false)
                        }
                        titleList.add(content)
                    }
                    else -> {}
                }
                index += 1
            }

            index = 0
            val dateContainers = doc.select("ul[id='bList01']")
            for (product in dateContainers.select("div")) {
                val isContent = product.select("span").first()?.text()?.trim()
                val content = if (isContent.isNullOrEmpty()) "" else isContent

                when (index % 3) {
                    0 -> {}
                    1 -> {
                        // Date
                        Log.d("Notice", content)
                        dateStringList.add(content.split("/")[0].trim())
                        authorList.add(content.split("/")[1].trim())
                    }
                    2 -> {}
                    else -> {}
                }
                index += 1
            }
            for (product in doc.select("ul[id='bList01'] div a"))
            {
                Log.d("Notice", product.attr("href").toString())
                urlList.add("http://biz.ssu.ac.kr${product.attr("href")}")
            }

            index = 0
            for (num in urlList) {
                if (!(page > 1 && isNoticeList[index])) {
                    val noticeItem = Notice(
                        authorList[index],
                        titleList[index],
                        urlList[index],
                        dateStringList[index],
                        isNoticeList[index]
                    )
                    noticeList.add(noticeItem)
                }
                index += 1
            }
        } catch (error: Exception) {
            Log.d("Notice", "Error : $error")
        }

        completion(noticeList)
    }

    @JvmStatic
    fun parseListVenture(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.businessVentureURL}$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch =
                URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://ensb.ssu.ac.kr/web/ensb/23?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=2&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            val table = doc.select("table[class='bbs-list']")
            for (product in table.select("td")) {
                //Log.d("Notice", "***")
                val content = product.text().trim()
                Log.d("Notice", content)
                when (index % 6) {
                    0 -> {
                        if (product.select("img").isNotEmpty()) {
                            // isNotice
                            isNoticeList.add(true)
                        } else {
                            isNoticeList.add(false)
                        }
                    }
                    1 -> {
                        // Title
                        titleList.add(content)
                    }
                    2 -> {}
                    3 -> {
                        // Author
                        authorList.add(content)
                    }
                    4 -> {
                        // Date
                        dateStringList.add(content)
                    }
                    5 -> {}
                    else -> {}
                }
                index += 1
            }

            val bbsList = doc.select("table[class='bbs-list']")
            val hrefs = bbsList.select("a")
            hrefs.map {
                Log.d("Notice", it.attr("href"))
                urlList.add(it.attr("href"))
            }

            index = 0
            for (num in urlList) {
                if (!(page > 1 && isNoticeList[index])) {
                    val noticeItem = Notice(
                        authorList[index],
                        titleList[index],
                        urlList[index],
                        dateStringList[index],
                        isNoticeList[index]
                    )
                    noticeList.add(noticeItem)
                }
                index += 1
            }

        } catch (error: Exception) {
            Log.d("Notice", "Error : $error")
        }

        completion(noticeList)
    }

    @JvmStatic
    fun parseListAccount(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.businessAccountURL}$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch =
                URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://accounting.ssu.ac.kr/web/accounting/3?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=2&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            val table = doc.select("table[class='bbs-list']")
            for (product in table.select("td")) {
                //Log.d("Notice", "***")
                val content = product.text().trim()
                Log.d("Notice", content)
                when (index % 6) {
                    0 -> {
                        if (product.select("img").isNotEmpty()) {
                            // isNotice
                            isNoticeList.add(true)
                        } else {
                            isNoticeList.add(false)
                        }
                    }
                    1 -> {
                        // Title
                        titleList.add(content)
                    }
                    2 -> {}
                    3 -> {
                        // Author
                        authorList.add(content)
                    }
                    4 -> {
                        // Date
                        dateStringList.add(content)
                    }
                    5 -> {}
                    else -> {}
                }
                index += 1
            }

            val bbsList = doc.select("table[class='bbs-list']")
            val hrefs = bbsList.select("a")
            hrefs.map {
                Log.d("Notice", it.attr("href"))
                urlList.add(it.attr("href"))
            }

            index = 0
            for (num in urlList) {
                if (!(page > 1 && isNoticeList[index])) {
                    val noticeItem = Notice(
                        authorList[index],
                        titleList[index],
                        urlList[index],
                        dateStringList[index],
                        isNoticeList[index]
                    )
                    noticeList.add(noticeItem)
                }
                index += 1
            }
        } catch (error: Exception) {
            Log.d("Notice", "Error : $error")
        }

        completion(noticeList)
    }

    @JvmStatic
    fun parseListFinance(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.businessFinanceURL}$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch =
                URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://finance.ssu.ac.kr/web/finance/menu5_1?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            val table = doc.select("table[class='bbs-list']")
            for (product in table.select("td")) {
                //Log.d("Notice", "***")
                val content = product.text().trim()
                Log.d("Notice", content)
                when (index % 5) {
                    0 -> {
                        if (product.select("img").isNotEmpty()) {
                            // isNotice
                            isNoticeList.add(true)
                        } else {
                            isNoticeList.add(false)
                        }
                    }
                    1 -> {
                        // Title
                        titleList.add(content)
                    }
                    2 -> {
                        // Author
                        authorList.add("")
                    }
                    3 -> {
                        // Date
                        dateStringList.add(content)
                    }
                    4 -> {}
                    5 -> {}
                    else -> {}
                }
                index += 1
            }

            val bbsList = doc.select("table[class='bbs-list']")
            val hrefs = bbsList.select("a")
            hrefs.map {
                Log.d("Notice", it.attr("href"))
                urlList.add(it.attr("href"))
            }

            index = 0
            for (num in urlList) {
                if (!(page > 1 && isNoticeList[index])) {
                    val noticeItem = Notice(
                        authorList[index],
                        titleList[index],
                        urlList[index],
                        dateStringList[index],
                        isNoticeList[index]
                    )
                    noticeList.add(noticeItem)
                }
                index += 1
            }
        } catch (error: Exception) {
            Log.d("Notice", "Error : $error")
        }

        completion(noticeList)
    }
}
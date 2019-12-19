package com.yourssu.notissu.parser

import com.yourssu.notissu.data.NoticeURL
import com.yourssu.notissu.model.Notice
import org.jsoup.Jsoup
import java.net.URLEncoder

object NoticeConvergence {
    @JvmStatic
    fun parseListConvergence(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.mixURL}$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://pre.ssu.ac.kr/web/convergence/32?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
            searchUrl
        } else {
            noticeUrl
        }

        print("$noticeUrl : $page")

        try {
            val doc = Jsoup.connect(requestURL).get()
                index = 0

                val product = doc.select("table[class='bbs-list']").first()

                for (item in product.select("tbody tr td")) {
                    //print("***")
                    val content = item.text().trim()
                    print(content)
                    when (index % 6) {
                        0 -> {}
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


                for (productA in doc.select("td[class='left'] a")) {
                    print(productA.attr("href") ?: "")
                    urlList.add(productA.attr("href") ?: "")
                }
            } catch (error: Exception) {
                print("Error : $error")
            }

            index = 0
            for (num in urlList) {
                val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], false)
                noticeList.add(noticeItem)
                index += 1
            }

            completion(noticeList)
    }
}

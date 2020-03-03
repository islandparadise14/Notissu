package com.yourssu.notissu.parser.list

import android.util.Log
import com.yourssu.notissu.data.NoticeURL
import com.yourssu.notissu.model.Notice
import org.jsoup.Jsoup
import java.net.URLEncoder

object NoticeSSUCatch {
    @JvmStatic
    fun parseListSSUCatch(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.ssucatchURL}page/$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "https://scatch.ssu.ac.kr/%ea%b3%b5%ec%a7%80%ec%82%ac%ed%95%ad/?category=&f=all&keyword=$keywordSearch"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            for (product in doc.select("div[class='row no-gutters align-items-center']")) {
                dateStringList.add(product.select("div[class^=notice_col1] div")[0].text())
                titleList.add("[${product.select("span[class^=label]")[0].text()}] ${product.select("span[class^=d-inline]")[0].text()}")
                authorList.add(product.select("div[class=notice_col4]")[0].text())
                urlList.add(product.select("a").attr("href"))
            }

            for (num in urlList) {
                val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], false)
                noticeList.add(noticeItem)
                index += 1
            }
        } catch (error: Exception) {
            Log.e("error", "$error")
        }

        completion(noticeList)
    }
}
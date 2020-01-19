package com.yourssu.notissu.parser.detail

import android.util.Log
import com.yourssu.notissu.model.File
import com.yourssu.notissu.model.NoticeDetail
import org.jsoup.Jsoup

object DetailNoticeSSUCatch {
    @JvmStatic
    fun parseSSUCatch(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class^=bg-white]")[0].html()
            for (product in doc.select("ul[class^=download]")[0].select("a"))
                fileList.add(File(product.select("span").text(), "https://scatch.ssu.ac.kr${product.attr("href")}"))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }
}
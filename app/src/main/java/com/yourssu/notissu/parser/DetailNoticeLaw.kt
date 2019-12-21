package com.yourssu.notissu.parser

import android.util.Log
import com.yourssu.notissu.model.File
import com.yourssu.notissu.model.NoticeDetail
import org.jsoup.Jsoup
import java.lang.Exception

object DetailNoticeLaw {
    @JvmStatic
    fun parseLaw(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class=frame-box]").html().replace("src=\"","src=\"http://pre.ssu.ac.kr")
            for (product in doc.select("tbody")[0].select("a"))
                fileList.add(File(product.text(), product.attr("href")))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseIntLaw(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class=frame-box]").html().replace("src=\"","src=\"http://lawyer.ssu.ac.kr")
            for (product in doc.select("tbody")[0].select("a"))
                fileList.add(File(product.text(), product.attr("href")))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }
}
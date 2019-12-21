package com.yourssu.notissu.parser

import android.util.Log
import com.yourssu.notissu.model.File
import com.yourssu.notissu.model.NoticeDetail
import org.jsoup.Jsoup
import java.lang.Exception

object DetailNoticeSocial {
    @JvmStatic
    fun parseWelfare(url: String, completion: (NoticeDetail) -> Unit) {
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
    fun parseAdministration(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class=td_box]").html().replace("src=\"","src=\"https://pubad.ssu.ac.kr")
            for (product in doc.select("td[class^=view] a"))
                fileList.add(File(product.text(), product.attr("href")))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseSociology(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class=view_content]").html().replace("src=\"","src=\"http://inso.ssu.ac.kr")
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseJournalism(url: String, completion: (NoticeDetail) -> Unit) {
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
    fun parseLifeLong(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("span[id=writeContents]").html().replace("src=\"","src=\"http://lifelongedu.ssu.ac.kr")
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parsePolitical(url: String, completion: (NoticeDetail) -> Unit) {
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
}
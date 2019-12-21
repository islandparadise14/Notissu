package com.yourssu.notissu.parser

import android.util.Log
import com.yourssu.notissu.model.File
import com.yourssu.notissu.model.NoticeDetail
import org.jsoup.Jsoup
import java.lang.Exception

object DetailNoticeIT {
    @JvmStatic
    fun parseComputer(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString = doc.select("div[class='smartOutput']").html().replace("src=\"", "src=\"http://cse.ssu.ac.kr")
            for (product in doc.select("span[class='file'] a"))
                fileList.add(File(product.text(), "http://cse.ssu.ac.kr${product.attr("href")}"))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }

        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseMedia(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("td[class=s_default_view_body_2] table tr td").html().replace("src=\"","src=\"http://media.ssu.ac.kr")
            for (product in doc.select("tr td table tbody tr td table tbody tr td a"))
                fileList.add(File(product.text(), "http://media.ssu.ac.kr/${product.attr("href")}"))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseSoftware(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString = doc.select("div[class=bo_view_2]").html().replace("src=\"","src=\"https://sw.ssu.ac.kr")
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseElectric(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[id=vContent]").html().replace("src=\"","src=\"http://infocom.ssu.ac.kr/")
            for (product in doc.select("div[id=vContent] a"))
                fileList.add(File(product.text(), "http://infocom.ssu.ac.kr${product.attr("href")}"))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseSmartSystem(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class='ui container']")[1].html().replace("src=\"","src=\"http://infocom.ssu.ac.kr/")
            for (product in doc.select("div[class='ui container'] p a"))
                fileList.add(File(product.text(), "http://smartsw.ssu.ac.kr${product.attr("href")}"))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }
}
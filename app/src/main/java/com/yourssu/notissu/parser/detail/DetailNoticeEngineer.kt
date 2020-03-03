package com.yourssu.notissu.parser.detail

import android.util.Log
import com.yourssu.notissu.model.File
import com.yourssu.notissu.model.NoticeDetail
import org.jsoup.Jsoup
import java.lang.Exception

object DetailNoticeEngineer {
    @JvmStatic
    fun parseChemistryEngineering(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class=body]").html().replace("src=\"","src=\"http://chemeng.ssu.ac.kr")
            for (product in doc.select("div[class=files] div[class=fileLayer] a")) {
                if (product.attr("class") != "divClose"){
                    val split = product.attr("href").split("'")
                    val boardId = split[1]
                    val bIdx = split[3]
                    val idx = split[5]
                    fileList.add(File(product.attr("title"), "http://chemeng.ssu.ac.kr/module/board/download.php?boardid=$boardId&b_idx=$bIdx&idx=$idx"))
                }
            }
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseMachine(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class='td_box']").html().replace("src=\"","src=\"https://me.ssu.ac.kr")
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
                doc.select("div[class=body]").html().replace("src=\"","src=\"http://ee.ssu.ac.kr")
            for (product in doc.select("div[class=files] div[class=fileLayer] a")) {
                if (product.attr("class") != "divClose"){
                    val split = product.attr("href").split("'")
                    val boardId = split[1]
                    val bIdx = split[3]
                    val idx = split[5]
                    fileList.add(File(product.attr("title"), "http://ee.ssu.ac.kr/module/board/download.php?boardid=$boardId&b_idx=$bIdx&idx=$idx"))
                }
            }
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseIndustry(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("div[class=frame-box]").html().replace("src=\"","src=\"http://iise.ssu.ac.kr")
            for (product in doc.select("tbody").select("a"))
                fileList.add(File(product.text(), product.attr("href")))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseOrganic(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("td[style^=word]").html().replace("src=\"","src=\"http://materials.ssu.ac.kr")
            var index = 0
            for (product in doc.select("tbody tr td").select("a")) {
                if (index < doc.select("tbody tr td").select("a").size - 3) {
                    var url = product.attr("href")
                    url = if (url.contains("http")) "" else "http://materials.ssu.ac.kr$url"
                    fileList.add(File(product.text(), fileURL = url))
                }
                index += 1
            }
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }

    @JvmStatic
    fun parseArchitecture(url: String, completion: (NoticeDetail) -> Unit) {
        var htmlString = ""
        val fileList = ArrayList<File>()

        try {
            val doc = Jsoup.connect(url).get()
            htmlString =
                doc.select("table[class=table]").html().replace("src=\"","src=\"http://soar.ssu.ac.kr")
            for (product in doc.select("table[class=table] a"))
                fileList.add(File(product.text(), "http://soar.ssu.ac.kr"+product.attr("href").replace("/.", "")))
        } catch (error: Exception) {
            Log.e("error", "$error")
        }
        completion(NoticeDetail(htmlString,fileList))
    }
}
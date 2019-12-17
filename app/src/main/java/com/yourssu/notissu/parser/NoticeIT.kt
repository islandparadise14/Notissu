package com.yourssu.notissu.parser

import com.yourssu.notissu.data.INFOCOM_URL_PATTERN
import com.yourssu.notissu.data.MEDIA_NOTICE_ID_PATTERN
import com.yourssu.notissu.model.Notice
import com.yourssu.notissu.utils.isNumeric
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.URLEncoder

object NoticeIT {
    @JvmStatic
    fun parseListComputer(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val pageStringList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val requestURL: String
        val noticeUrl = "http://cse.ssu.ac.kr/03_sub/01_sub.htm?page=$page&key=&keyfield=&category=&bbs_code=Ti_BBS_1"

        var index = 0

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://cse.ssu.ac.kr/03_sub/01_sub.htm?page=$page&key=$keywordSearch&keyfield=subject&category=&bbs_code=Ti_BBS_1"

            searchUrl
        } else {
            noticeUrl
        }

        print(requestURL)

        try {
            val doc = Jsoup.connect(requestURL).get()
                for (product in doc.select("table > tbody > tr")) {
                    if (product.nextSibling()?.attr("class") == "center") {
                        val noticeAuthor = product.nextSibling()?.toString() ?:""
                        val noticeDate = product.nextSibling()?.nextSibling()?.toString() ?:""
                        val pageString = product.select("a")?.attr("href") ?:""

                        when (index % 2) {
                        0 -> {
                            val noticeTitle = product.text()
                            authorList.add(noticeAuthor)
                            titleList.add(noticeTitle)
                            pageStringList.add("http://cse.ssu.ac.kr/03_sub/01_sub.htm$pageString")
                            dateStringList.add(noticeDate)
                            isNoticeList.add(false)
                        }
                        1 -> {}
                        else -> {}
                    }
                    index += 1

                    } else if (product.nextSibling()?.attr("class") ?:"" == "etc") {
                        // 첫 페이지만 보여주기

                        if (page < 2) {
                            val noticeAuthor = product.nextSibling()?.toString() ?:""
                            val noticeDate = product.nextSibling()?.nextSibling()?.toString() ?: ""
                            val pageString = product.select("a").first()?.attr("href") ?: ""
                            pageStringList.add("http://cse.ssu.ac.kr/03_sub/01_sub.htm$pageString")

                            when (index % 2) {
                            0 -> {
                                val noticeTitle = product.text() ?: ""
                                authorList.add(noticeAuthor)
                                titleList.add(noticeTitle)
                                pageStringList.add("http://cse.ssu.ac.kr/03_sub/01_sub.htm$pageString")
                                dateStringList.add(noticeDate)
                                isNoticeList.add(true)
                            }
                            1 -> {}
                            else -> {}
                        }
                            index += 1
                        }
                    }
                }

                index = 0
                var canFetchData = true
                if (authorList.count() < 1) {
                    canFetchData = false
                }

                for (num in authorList) {
                    val noticeItem = Notice(authorList[index], titleList[index], pageStringList[index], dateStringList[index], isNoticeList[index])

                    noticeList.add(noticeItem)
                    index += 1
                }

                if (canFetchData)
                    completion(noticeList)
            } catch (error: Exception) {
                print("Error : $error")
            }
    }

    @JvmStatic
    fun parseListMedia(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList    = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val requestURL : String
        val noticeUrl = "http://media.ssu.ac.kr/sub.php?code=XxH00AXY&mode=&category=1&searchType=&search=&orderType=&orderBy=&page=$page"

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://media.ssu.ac.kr/sub.php?code=XxH00AXY&mode=&category=1&searchType=title&search=$keywordSearch&orderType=&orderBy=&page=$page"

            searchUrl
        } else {
            noticeUrl
        }

        var index: Int
        try {
            val doc = Jsoup.connect(requestURL).get()
            for (product in doc.select("table > tbody > tr > a")) {
                val noticeId = if (product.attr("onclick") == null) ""
                else if (MEDIA_NOTICE_ID_PATTERN.matcher(product.attr("onclick")).matches()) product.attr("onclick")
                else ""
                val url = "http://media.ssu.ac.kr/sub.php?code=XxH00AXY&mode=view&board_num=$noticeId&category=1"
                urlList.add(url)
                titleList.add(product.text() ?: "")
            }

            index = 0
            for (product in doc.select("td[align='center']")) {
                if (index % 4 == 0) {
                    val isNotice = product.text() ?: ""
                    if (!isNumeric(isNotice)) {
                        isNoticeList.add(true)
                    } else {
                        isNoticeList.add(false)
                    }
                }

                if (index % 4 == 1) {
                    authorList.add(product.text() ?: "")
                } else if (index % 4 == 2) {
                    dateStringList.add(product.text() ?: "")
                }
                index += 1
            }

            index = 0
            var canFetchData = true
            if (authorList.count() < 1) {
                canFetchData = false
            }

            for (num in authorList) {
                val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], isNoticeList[index])

                noticeList.add(noticeItem)
                index += 1
            }
            if (canFetchData)
                completion(noticeList)
        } catch (error: Exception) {
            print("Error : $error")
        }
    }

    @JvmStatic
    fun parseListSoftware(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "https://sw.ssu.ac.kr/bbs/board.php?bo_table=sub6_1&page=$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        var index: Int

        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "https://sw.ssu.ac.kr/bbs/board.php?bo_table=sub6_1&sca=&stx=$keywordSearch&sop=and&page=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
                for (product in doc.select("td[class^=num]")) {
                    val num = product.select("b").first()?.text() ?: ""

                    if (num.isEmpty()) {
                        // isNotice
                        isNoticeList.add(false)
                    } else {
                        isNoticeList.add(true)
                    }
                }

                val subject = doc.select("td[class^=subject]")
                for (product in subject.select("a[href]")) {
                    var url = product.text() ?: ""
                    url = url.replace("..", "https://sw.ssu.ac.kr")
                    titleList.add(product.text() ?: "")
                    urlList.add(url)
                }

                for (product in doc.select("td[class^=datetime]")) {
                    dateStringList.add(product.text() ?: "")
                }

                for (product in doc.select("td[class^=name]")) {
                    authorList.add(product.text() ?: "")
                }
            } catch (error: Exception) {
                print("Error : $error")
            }

            index = 0
            var canFetchData = true
            if (authorList.count() < 1) {
                canFetchData = false
            }

            for (num in authorList) {
                val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], isNoticeList[index])

                noticeList.add(noticeItem)
                index += 1
            }

            if (canFetchData)
                completion(noticeList)
    }

    @JvmStatic
    fun parseListElectric(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "http://infocom.ssu.ac.kr/rb/?c=2/38&p=$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://infocom.ssu.ac.kr/rb/?c=2/38&where=subject%7Ctag&keyword=$keywordSearch&p=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
                for (product in doc.select("div[class^='list']")) {
                    val urlhtml = if (INFOCOM_URL_PATTERN.matcher(product.html()).matches()) product.html() else ""
                    val urlsplit = urlhtml.split("'")[0] ?: ""
                    val urlInfo = urlsplit.replace("&amp;", "&")
                    val url = "http://infocom.ssu.ac.kr$urlInfo"

                    val strs = (product.select("div[class^='info']").first()?.text() ?: "").split("|")

                    urlList.add(url)
                    authorList.add(strs[0].trim())
                    dateStringList.add(strs[1].trim())

                    if (product.select("span[class^='subject']").first().text() == "") {
                        titleList.add("(제목없음)")
                    } else {
                        titleList.add(product.select("span[class^='subject']").first().text())
                    }

                    if (product.html().contains("img")) {
                        // isNotice
                        isNoticeList.add(true)
                    } else {
                        isNoticeList.add(false)
                    }
                }
            } catch (error: Exception) {
                print("Error : $error")
            }

            index = 0
            for (num in authorList) {
                val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], isNoticeList[index])
                noticeList.add(noticeItem)
                index += 1
            }

            completion(noticeList)
    }

    @JvmStatic
    fun parseListSmartSystem(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "http://smartsw.ssu.ac.kr/board/notice/$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://smartsw.ssu.ac.kr/board/notice/$page?search=$keywordSearch"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()

            for (product in doc.select("table[class='ui celled padded table'] tbody td")) {
                val content = (product.text() ?:"").trim()
                when (index % 3) {
                    0 -> {
                        //title
                        titleList.add(content)
                    }
                    1 -> {
                        //author
                        authorList.add(content)
                    }
                    2 -> {
                        //date
                        dateStringList.add(content)
                    }
                    else -> {}
                }

                val url = product.select("a").first().attr("href") ?: ""
                val realUrl = "http://smartsw.ssu.ac.kr$url"
                urlList.add(realUrl)


                index += 1
            }
        } catch (error: Exception) {
            print("Error : $error")
        }
        index = 0
        for (num in authorList) {
            val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], false)
            noticeList.add(noticeItem)
            index += 1
        }

        completion(noticeList)
    }

}
package com.yourssu.notissu.parser.list

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
        val urlStringList = ArrayList<String>()
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
            for (product in doc.select("tbody tr")) {
                val tds = product.select("td")
                var tdIndex = 0
                var isNotice = false
                var title = ""
                var author = ""
                var date = ""
                var url = ""

                for (td in tds){
                    when (tdIndex) {
                        0 -> {
                            isNotice = !td.text().isNumeric()
                        }
                        1 -> {
                            title = td.select("a").text()
                            url = "http://cse.ssu.ac.kr/03_sub/01_sub.htm${td.select("a").attr("href")}"
                        }
                        2 -> {
                            author = td.text()
                        }
                        3 -> {
                            date = td.text()
                        }
                        else -> {}
                    }
                    tdIndex += 1
                }
                if (author.isNotEmpty() && title.isNotEmpty() && date.isNotEmpty() && url.isNotEmpty()) {
                    if (isNotice) {
                        if (page < 2) {
                            authorList.add(author)
                            dateStringList.add(date)
                            titleList.add(title)
                            urlStringList.add(url)
                            isNoticeList.add(isNotice)
                        }
                    } else {
                        authorList.add(author)
                        dateStringList.add(date)
                        titleList.add(title)
                        urlStringList.add(url)
                        isNoticeList.add(isNotice)
                    }
                }
            }

            index = 0

            for (num in authorList) {
                val noticeItem = Notice(authorList[index], titleList[index], urlStringList[index], dateStringList[index], isNoticeList[index])

                noticeList.add(noticeItem)
                index += 1
            }

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
            for (product in doc.select("table tbody tr a")) {
                val noticeIdText = product.attr("onclick")
                val noticeId = noticeIdText.split("'")[1]
                val url = "http://media.ssu.ac.kr/sub.php?code=XxH00AXY&mode=view&board_num=$noticeId&category=1"
                urlList.add(url)
                titleList.add(product.text() ?: "")
            }

            index = 0
            for (product in doc.select("td[align='center']")) {
                if (index % 4 == 0) {
                    val isNotice = product.text() ?: ""
                    if (!isNotice.isNumeric()) {
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
            for (product in subject.select("a")) {
                var url = product.attr("href") ?: ""
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

            index = 0
            var canFetchData = true
            if (authorList.count() < 1) {
                canFetchData = false
            }

            for (num in authorList) {
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

            if (canFetchData)
                completion(noticeList)

            } catch (error: Exception) {
                print("Error : $error")
            }
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
                val url =
                    "http://infocom.ssu.ac.kr/rb/?c=2/38&uid=${product.attr("onclick").split("'")[1].split("uid=")[1]}"

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

            index = 0
            for (num in authorList) {
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
            print("Error : $error")
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
                val content = (product.text() ?: "").trim()
                when(index % 4) {
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

                val url = product.select("a").first()
                if (url != null) {
                    val realUrl = "http://smartsw.ssu.ac.kr${url.attr("href")}"
                    urlList.add(realUrl)
                }

                index += 1
            }

            index = 0
            for (num in authorList) {
                val noticeItem = Notice(author= authorList[index], title= titleList[index], url= urlList[index], date= dateStringList[index], isNotice= false)
                noticeList.add(noticeItem)
                index += 1
            }
        } catch (error: Exception) {
            print("Error : $error")
        }

        completion(noticeList)
    }

}
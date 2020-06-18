package com.yourssu.notissu.parser.list

import com.yourssu.notissu.data.NoticeURL
import com.yourssu.notissu.model.NoticeItem
import com.yourssu.notissu.utils.isNumeric
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.URLEncoder

object NoticeEngineer {

    @JvmStatic
    fun parseListMachine(page: Int, keyword: String?, completion: (ArrayList<NoticeItem>) -> Unit) {
        val noticeUrl = "${NoticeURL.engineerMachineURL}$page"
        val noticeList = ArrayList<NoticeItem>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "https://me.ssu.ac.kr/%EA%B2%8C%EC%8B%9C%ED%8C%90-%EC%9E%90%EB%A3%8C%EC%8B%A4/%EA%B3%B5%EC%A7%80%EC%82%AC%ED%95%AD/$page/?select=title&keyword=$keywordSearch"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            for (product in doc.select("tbody tr td")) {
                val content = product.text().trim()
                //                            print(content)
                when (index % 5) {
                    1 -> {
                        // Title
                        titleList.add(content)
                    }
                    2 -> {}
                    3 -> {
                        // Date
                        dateStringList.add(content)
                    }
                    else -> {}
                }
                index += 1
            }

            for (product in doc.select("tbody tr td a")) {
                urlList.add(product.attr("href") ?: "")
            }

            index = 0
            for (num in urlList) {
                val noticeItem = NoticeItem("", titleList[index], urlList[index], dateStringList[index], false)
                noticeList.add(noticeItem)
                index += 1
            }
        } catch (error: Exception) {
            print("Error : $error")
        }

        completion(noticeList)
    }

    @JvmStatic
    fun parseListChemistryEngineering(page: Int, keyword: String?, completion: (ArrayList<NoticeItem>) -> Unit) {
        // offset : 0 / 10 / 20 / etc.
        // offset : 0 * 10 / 1 * 10
        val offset = (page - 1) * 10
        val noticeUrl = "${NoticeURL.engineerChemistryURL}$offset"
        val noticeList = ArrayList<NoticeItem>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://chemeng.ssu.ac.kr/sub/sub03_01.php?boardid=notice1&sk=$keywordSearch&sw=a&category=&offset=$offset"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()

            for (product in doc.select("div[class^='board-list'] tr")) {
                val number = product.select("td[class^='no']").first()?.text()?.trim() ?: ""
                val title = product.select("td[class^='subject'] a").first()?.text()?.trim() ?: ""
                val author = product.select("td[class^='name']").first()?.text()?.trim() ?: ""
                val date = product.select("td[class^='date']").first()?.text()?.trim() ?: ""

                if (index > 0) {
                    if (number.isNumeric()) {
                        // normal
                        isNoticeList.add(false)
                    } else {
                        // notice
                        isNoticeList.add(true)
                    }
                    titleList.add(title)
                    authorList.add(author)
                    dateStringList.add(date)

                }

                index += 1
            }

            for (product in doc.select("div[class^='board-list'] td a")) {
                val href = product.attr("href")
                print("http://chemeng.ssu.ac.kr$href")
                urlList.add("http://chemeng.ssu.ac.kr$href")
            }

            index = 0
            for (num in urlList) {
                if (!(page > 1 && isNoticeList[index])) {
                    val noticeItem = NoticeItem(
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
    fun parseListElectric(page: Int, keyword: String?, completion: (ArrayList<NoticeItem>) -> Unit) {
        // offset : 0 / 10 / 20 / etc.
        // offset : 0 * 10 / 1 * 10
        val offset = (page - 1) * 10
        val noticeUrl = "${NoticeURL.engineerElectricURL}$offset"
        val noticeList = ArrayList<NoticeItem>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val dateStringList = ArrayList<String>()
        var index: Int
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://ee.ssu.ac.kr/sub/sub05_01.php?boardid=notice&sk=$keywordSearch&sw=a&category=&offset=$offset"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            for (product in doc.select("div[class^='num']")) {
                isNoticeList.add(!(product.text().isNumeric()))
            }

            for (product in doc.select("div[class^='subject']")) {
                //print("***")
                val content = product.text().trim()
                //                            print(content)
                val detailUrl = product.select("a").first()?.attr("href") ?: ""
                titleList.add(content)
                urlList.add("http://ee.ssu.ac.kr$detailUrl")
            }

            index = 0
            for (product in doc.select("div[class^='info'] span")) {
                val content = product.text().trim()

                when (index % 3) {
                    0 -> {
                        // Date
                        dateStringList.add(content)
                    }
                    1 -> {
                        // Author
                        authorList.add(content)
                    }
                    else -> {}
                }
                index += 1
            }

            index = 0
            for (num in urlList) {
                if (!(page > 1 && isNoticeList[index])) {
                    val noticeItem = NoticeItem(
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
    fun parseListIndustry(page: Int, keyword: String?, completion: (ArrayList<NoticeItem>) -> Unit) {
        val noticeUrl = "${NoticeURL.engineerIndustryURL}$page"
        val noticeList = ArrayList<NoticeItem>()
        var authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://iise.ssu.ac.kr/web/iise/notice?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            for (product in doc.select("table[class^='bbs-list'] td")) {
                //print("***")
                val content = product.text().trim()
                when (index % 5) {
                    0 -> {
                        if (product.html().contains("img")) {
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
                        // Date
                        dateStringList.add(content)
                    }
                    4 -> {}
                    else -> {}
                }
                index += 1
            }

            for (product in doc.select("table[class^='bbs-list'] td a")) {
                urlList.add(product.attr("href") ?: "")
            }

            index = 0
            for (num in urlList) {
                if (!(page > 1 && isNoticeList[index])) {
                    val noticeItem = NoticeItem(
                        "",
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
    fun parseListOrganic(page: Int, keyword: String?, completion: (ArrayList<NoticeItem>) -> Unit) {
        val noticeUrl = "${NoticeURL.engineerOrganicURL}$page"
        val noticeList = ArrayList<NoticeItem>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        var index: Int
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://materials.ssu.ac.kr/bbs/board.php?tbl=notice&&category=&findType=&findWord=$keywordSearch&sort1=&sort2=&it_id=&shop_flag=&mobile_flag=&page=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            for (product in (doc.select("div[class='mt40'] td[align=left] a"))) {
                val content = product.text().trim()
                titleList.add(content)
                urlList.add("http://materials.ssu.ac.kr${product.attr("href") ?: ""}")
            }
            // Remove Frist Item
            index = 0
            for (product in (doc.select("div[class='mt40'] tr[height='35']"))) {
                var realContent = product.html() ?: ""
                realContent = realContent.replace("\t", "")
                realContent = realContent.replace(" ", "")
                if (index > 0) {
                    var postItem = (realContent.split("\n"))
                    val postItemList = ArrayList<String>()
                    for (string in postItem){
                        postItemList.add(string)
                    }
                    if (postItem.count() > 9) {
                        // 번호를 지운다
                        postItemList.removeAt(0)
                    }
                    authorList.add(postItemList[3])
                    dateStringList.add(postItemList[5])
                }
                index += 1
            }

            index = 0
            for (num in urlList) {
                val noticeItem = NoticeItem(authorList[index], titleList[index], urlList[index], dateStringList[index], false)
                noticeList.add(noticeItem)
                index += 1
            }
        } catch (error: Exception) {
            print("Error : $error")
        }

        completion(noticeList)
    }

    @JvmStatic
    fun parseListArchitecture(page: Int, keyword: String?, completion: (ArrayList<NoticeItem>) -> Unit) {
        val noticeUrl = NoticeURL.engineerArchitectureURL
        val noticeList = ArrayList<NoticeItem>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        val requestURL: String

        requestURL = noticeUrl

        if (page < 2) {
            try {
                val doc = Jsoup.connect(requestURL).get()
                for (product in doc.select("table[class^=table] tbody tr")) {
                    val content = product.select("td")
                    if (keyword == null) {
                        titleList.add(content[0].select("span").text() + content[1].text())
                        authorList.add(content[0].select("span").text())
                        dateStringList.add(content[2].text())
                        urlList.add("http://soar.ssu.ac.kr" + product.attr("href"))
                    } else {
                        if (content[1].text().contains(keyword)) {
                            titleList.add(content[0].select("span").text() + content[1].text())
                            authorList.add(content[0].select("span").text())
                            dateStringList.add(content[2].text())
                            urlList.add("http://soar.ssu.ac.kr" + product.attr("href"))
                        }
                    }
                }
                var index = 0

                for (num in urlList){
                    val noticeItem = NoticeItem(authorList[index], titleList[index], urlList[index], dateStringList[index], false)
                    noticeList.add(noticeItem)
                    index += 1
                }

            } catch (error: Exception) {
                print("Error : $error")
            }
        }
        completion(noticeList)
    }
}

package com.yourssu.notissu.parser

import com.yourssu.notissu.data.NoticeURL
import com.yourssu.notissu.model.Notice
import com.yourssu.notissu.utils.isNumeric
import org.jsoup.Jsoup
import java.net.URLEncoder

object NoticeSocial {
    @JvmStatic
    fun parseListWelfare(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.socialWelfareURL}$page"
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
            val searchUrl = "http://pre.ssu.ac.kr/web/mysoongsil/bbs_notice?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
                for (product in doc.select("table[class='bbs-list'] td")) {
                    //print("***")
                    val content = product.text().trim()
                    print(content)
                    when (index % 6) {
                        0 -> {
                            if (product.html()?.contains("img") == true) {
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
                            // Author
                            authorList.add(content)
                        }
                        4 -> {
                            // Date
                            dateStringList.add(content)
                        }
                        else -> {}
                    }
                    index += 1
                }

                for (product in doc.select("table[class='bbs-list'] a")) {
                    print(product.attr("href") ?: "")
                    urlList.add(product.attr("href") ?: "")
                }
            } catch (error: Exception) {
                print("Error : $error")
            }

            index = 0
            for (num in urlList) {
                val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], isNoticeList[index])
                noticeList.add(noticeItem)
                index += 1
            }

            completion(noticeList)
    }

    @JvmStatic
    fun parseListAdministration(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.socialAdministrationURL}$page/"
        val noticeList = ArrayList<Notice>()
        var authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "https://pubad.ssu.ac.kr/%EC%A0%95%EB%B3%B4%EA%B4%91%EC%9E%A5/%ED%95%99%EB%B6%80-%EA%B3%B5%EC%A7%80%EC%82%AC%ED%95%AD/page/$page/?select=title&keyword=$keywordSearch#038;keyword=$keywordSearch"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
                for (product in doc.select("div[class='table_wrap'] td")) {
                    //print("***")
                    val content = product.text().trim()
                    when (index % 5) {
                        0 -> {
                            isNoticeList.add(!isNumeric((product.text() ?: "")))
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
                        else -> {}
                    }
                    index += 1
                }

                for (product in doc.select("td[class='title'] a")) {
                    print(product.attr("href") ?: "")
                    urlList.add(product.attr("href") ?: "")
                }
            } catch (error: Exception) {
                print("Error : $error")
            }

            index = 0
            for (num in urlList) {
                val noticeItem = Notice("", titleList[index], urlList[index], dateStringList[index], isNoticeList[index])
                noticeList.add(noticeItem)
                index += 1
            }

            completion(noticeList)
    }

    @JvmStatic
    fun parseListSociology(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val offset = (page - 1) * 10
        val noticeUrl = "${NoticeURL.socialSociologyURL}$offset"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://inso.ssu.ac.kr/sub/sub04_01.php?boardid=notice&sk=$keywordSearch&sw=a&category=%ED%95%99%EA%B3%BC%EA%B3%B5%EC%A7%80&offset=$offset"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
                for (product in doc.select("div[class='board_list'] td")) {
                    //print("***")
                    val content = product.text().trim()
                    print(content)
                    when (index % 6) {
                        0 -> {}
                        1 -> {}
                        2 -> {
                            // Title
                            titleList.add(content)
                        }
                        3 -> {
                            // Author
                            authorList.add(content)
                        }
                        4 -> {
                            // Date
                            dateStringList.add(content)
                        }
                        else -> {}
                    }
                    index += 1
                }

                for (product in doc.select("td[class='subject'] a")) {
                    var url = "http://inso.ssu.ac.kr${product.attr("href") ?: ""}"
                    url = url.replace("학과공지", "")
                    print(url)
                    urlList.add(url)
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

    @JvmStatic
    fun parseListJournalism(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.socialJournalismURL}$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val isNoticeList = ArrayList<Boolean>()
        val dateStringList = ArrayList<String>()
        var index: Int
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://pre.ssu.ac.kr/web/ssja/20?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            print("after request")
            val doc = Jsoup.connect(requestURL).get()
                index = 0

                val product = doc.select("table[class='bbs-list']").first()

                if (page > 1) {
                    for (item in product.select("tbody tr:not(.trNotice) td")) {
                        //print("***")
                        val content = item.text().trim()
                        print(content)
                        when (index % 5) {
                            0 -> {
                                isNoticeList.add(false)
                                // Title
                                titleList.add(content)
                            }
                            1 -> {}
                            2 -> {
                                // Author
                                authorList.add(content)
                            }
                            3 -> {
                                // Date
                                dateStringList.add(content)
                            }
                            else -> {}
                        }
                        index += 1
                    }
                } else {
                    for (item in product.select("tbody tr td")) {
                        //print("***")
                        val content = item.text().trim()
                        print(content)
                        when (index % 5) {
                            0 -> {
                                isNoticeList.add(item.attr("class") == "trNotice")
                                // Title
                                titleList.add(content)
                            }
                            1 -> {}
                            2 -> {
                                // Author
                                authorList.add(content)
                            }
                            3 -> {
                                // Date
                                dateStringList.add(content)
                            }
                            else -> {}
                        }
                        index += 1
                    }
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
                val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], isNoticeList[index])
                noticeList.add(noticeItem)
                index += 1
            }

            completion(noticeList)
    }

    @JvmStatic
    fun parseListLifeLong(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.socialLifeLongURL}$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        var index = 0
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://lifelongedu.ssu.ac.kr/bbs/board.php?bo_table=univ&sca=&sfl=wr_subject&stx=$keywordSearch&sop=and&page=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            var boldCount = 0
            val doc = Jsoup.connect(requestURL).get()
                var isAdd = false
                for (product in doc.select("table[class='board_list'] tbody td")) {
                    val content = product.text().trim()
                    print(content)
                    when (index % 5) {
                        0 -> {
                            if (page > 1 && !isNumeric(content)) {
                                isAdd = false
                                boldCount += 1
                            } else {
                                isAdd = true
                            }
                        }
                        1 -> {
                            // Title
                            if (isAdd) {
                                titleList.add(content)
                            }
                        }
                        2 -> {
                            // Author
                            if (isAdd) {
                                authorList.add(content)
                            }
                        }
                        3 -> {
                            // Date
                            if (isAdd) {
                                dateStringList.add(content)
                            }
                        }
                        else -> {}
                    }

                    print("index : $index / isAdd : $isAdd")
                    index += 1
                }

                print("bold Count : $boldCount")

                index = 0
                for (product in doc.select("td[class='subject'] a")) {
                    var url = "http://lifelongedu.ssu.ac.kr${product.attr("href") ?: ""}"
                    url = url.replace("..", "")
                    print(url)
                    if (index < boldCount) {
                        if (page < 2) {
                            urlList.add(url)
                        }
                    } else {
                        urlList.add(url)
                    }

                    index += 1
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

    @JvmStatic
    fun parseListPolitical(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.socialPoliticsURL}$page"
        val noticeList = ArrayList<Notice>()
        val authorList = ArrayList<String>()
        val titleList  = ArrayList<String>()
        val urlList = ArrayList<String>()
        val dateStringList = ArrayList<String>()
        var index: Int
        val requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch = URLEncoder.encode(keyword, "UTF-8")
            val searchUrl = "http://pre.ssu.ac.kr/web/psir/board_a?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            print("after request")
            val doc = Jsoup.connect(requestURL).get()
                index = 0

                val product = doc.select("table[class='bbs-list']").first()

                if (page > 1) {
                    for (item in product.select("tbody tr:not(.trNotice) td")) {
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
                } else {
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
                            else -> {}
                        }
                        index += 1
                    }
                }

                if (page > 1) {
                    for (item in product.select("tbody tr:not(.trNotice) td a")) {
                        print(item.attr("href") ?: "")
                        urlList.add(item.attr("href") ?: "")
                    }
                } else {
                    for (item in product.select("tbody tr td a")) {
                        print(item.attr("href") ?: "")
                        urlList.add(item.attr("href") ?: "")
                    }
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

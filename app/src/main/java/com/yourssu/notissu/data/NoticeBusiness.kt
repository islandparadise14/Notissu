package com.yourssu.notissu.data

import android.util.Log
import com.yourssu.notissu.model.Notice
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.URLEncoder

object NoticeBusiness {

    @JvmStatic
    fun parseListBiz(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Unit) {
        val noticeUrl = "${NoticeURL.businessBizURL}$page"
        var noticeList = ArrayList<Notice>()
        var authorList = ArrayList<String>()
        var titleList = ArrayList<String>()
        var urlList = ArrayList<String>()
        var isNoticeList = ArrayList<Boolean>()
        var dateStringList = ArrayList<String>()
        var index = 0
        var requestURL: String

        requestURL = if (keyword != null) {
            val keywordSearch =
                URLEncoder.encode(keyword, "UTF-8");
            val searchUrl =
                "http://biz.ssu.ac.kr/bbs/list.do?&bId=BBS_03_NOTICE&sc_title=$keywordSearch&page=$page"
            searchUrl
        } else {
            noticeUrl
        }

        try {
            val doc = Jsoup.connect(requestURL).get()
            val titleContainers = doc.select("ul[id='bList01']")
            for (product in titleContainers.select("div")) {
                val isContent = product.select("a").first()?.text()?.trim()
                val content = if (isContent.isNullOrEmpty()) "" else isContent

                when (index % 3) {
                    0 -> {
                        // Title
                        Log.d("Notice", content)
                        if (product.select("a").first()?.className() == "fixedPost") {
                            // isNotice
                            isNoticeList.add(true)
                        } else {
                            // normal
                            isNoticeList.add(false)
                        }
                        titleList.add(content)
                    }
                    else -> {}
                }
                index += 1
            }

            index = 0
            val dateContainers = doc.select("ul[id='bList01']")
            for (product in dateContainers.select("div")) {
                val isContent = product.select("span").first()?.text()?.trim()
                val content = if (isContent.isNullOrEmpty()) "" else isContent

                when (index % 3) {
                    0 -> {}
                    1 -> {
                        // Date
                        Log.d("Notice", content)
                        dateStringList.add(content.split("/")[0].trim())
                        authorList.add(content.split("/")[1].trim())
                    }
                    2 -> {}
                    else -> {}
                }
                index += 1
            }
            val urlContainers = doc.select("ul[id='bList01']")
            val product = urlContainers.select("div")
            val hrefs = product.select("a[href]")
            hrefs.forEach { href ->
                Log.d("Notice", href.toString())
                urlList.add("http://biz.ssu.ac.kr$href")
            }
        } catch (error: Exception) {
            Log.d("Notice", "Error : $error")
        }

        index = 0
        for (num in urlList) {
            val noticeItem = Notice(authorList[index], titleList[index], urlList[index], dateStringList[index], isNoticeList[index])
            noticeList.add(noticeItem)
            index += 1
        }

        completion(noticeList)
    }

//    fun parseListVenture(page: Int, keyword: String?, completion: @escaping ([Notice]) -> Void) {
//        val noticeUrl = "$NoticeURL.businessVentureURL$page"
//        var noticeList = ArrayList<Notice>()
//        var authorList = ArrayList<String>()
//        var titleList  = ArrayList<String>()
//        var urlList = ArrayList<String>()
//        var isNoticeList = ArrayList<Boolean>()
//        var dateStringList = ArrayList<String>()
//        var index = 0
//        var requestURL = ""
//
//        requestURL = if (keyword != null) {
//            val keywordSearch = keyword!.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)
//            val searchUrl = "http://ensb.ssu.ac.kr/web/ensb/23?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=2&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
//            searchUrl
//        } else {
//            noticeUrl
//        }
//
//        Alamofire.request(requestURL).responseString(encoding: .utf8) { response in
//                when (response.result) {
//                    case .success(_):
//                    if let data = response.result.value {
//                        do {
//                            val doc = try HTML(html: data, encoding: .utf8)
//                                for product in doc.css("table[class='bbs-list'] td") {
//                                    //print("***")
//                                    let content = product.text!.trimmingCharacters(in: .whitespacesAndNewlines)
//                                    print(content)
//                                    switch (index % 6) {
//                                        case 0:
//                                        if product.innerHTML?.contains("img") ?? false {
//                                        // isNotice
//                                        isNoticeList.append(true)
//                                    } else {
//                                        isNoticeList.append(false)
//                                    }
//                                        break
//                                        case 1:
//                                        // Title
//                                        titleList.append(content)
//                                        break
//                                        case 2: break
//                                        case 3:
//                                        // Author
//                                        authorList.append(content)
//                                        break
//                                        case 4:
//                                        // Date
//                                        dateStringList.append(content)
//                                        break
//                                        case 5: break
//                                        default: break
//                                    }
//                                    index += 1
//                                }
//
//                                for product in doc.css("table[class='bbs-list'] a") {
//                                    print(product["href"] ?? "")
//                                    urlList.append(product["href"] ?? "")
//                                }
//                            } catch let error {
//                                print("Error : \(error)")
//                            }
//
//                            index = 0
//                            for _ in urlList {
//                                let noticeItem = Notice(author: authorList[index], title: titleList[index], url: urlList[index], date: dateStringList[index], isNotice: isNoticeList[index])
//                                noticeList.append(noticeItem)
//                                index += 1
//                            }
//
//                            completion(noticeList)
//                        }
//                    case .failure(_):
//                    print("Error message:\(String(describing: response.result.error))")
//                    break
//                }
//        }
//    }
//
//    fun parseListAccount(page: Int, keyword: String?, completion: (ArrayList<Notice>) -> Void) {
//        val noticeUrl = "$NoticeURL.businessAccountURL$page"
//        var noticeList = ArrayList<Notice>()
//        var authorList = ArrayList<String>()
//        var titleList  = ArrayList<String>()
//        var urlList = ArrayList<String>()
//        var isNoticeList = ArrayList<Boolean>()
//        var dateStringList = ArrayList<String>()
//        var index = 0
//        var requestURL = ""
//
//        requestURL = if (keyword != null) {
//            val keywordSearch = keyword!.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)
//            val searchUrl = "http://accounting.ssu.ac.kr/web/accounting/3?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=2&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
//            searchUrl
//        } else {
//            noticeUrl
//        }
//
//        Alamofire.request(requestURL).responseString(encoding: .utf8) { response in
//                when (response.result) {
//                    case .success(_):
//                    if let data = response.result.value {
//                        do {
//                            let doc = try HTML(html: data, encoding: .utf8)
//                                for product in doc.css("table[class='bbs-list'] td") {
//                                    //print("***")
//                                    let content = product.text!.trimmingCharacters(in: .whitespacesAndNewlines)
//                                    print(content)
//                                    switch (index % 6) {
//                                        case 0:
//                                        if product.innerHTML?.contains("img") ?? false {
//                                        // isNotice
//                                        isNoticeList.append(true)
//                                    } else {
//                                        isNoticeList.append(false)
//                                    }
//                                        break
//                                        case 1:
//                                        // Title
//                                        titleList.append(content)
//                                        break
//                                        case 2: break
//                                        case 3:
//                                        // Author
//                                        authorList.append(content)
//                                        break
//                                        case 4:
//                                        // Date
//                                        dateStringList.append(content)
//                                        break
//                                        case 5: break
//                                        default: break
//                                    }
//                                    index += 1
//                                }
//
//                                for product in doc.css("table[class='bbs-list'] a") {
//                                    print(product["href"] ?? "")
//                                    urlList.append(product["href"] ?? "")
//                                }
//                            } catch let error {
//                                print("Error : \(error)")
//                            }
//
//                            index = 0
//                            for _ in urlList {
//                                let noticeItem = Notice(author: authorList[index], title: titleList[index], url: urlList[index], date: dateStringList[index], isNotice: isNoticeList[index])
//                                noticeList.append(noticeItem)
//                                index += 1
//                            }
//
//                            completion(noticeList)
//                        }
//                    case .failure(_):
//                    print("Error message:\(String(describing: response.result.error))")
//                    break
//                }
//        }
//    }
//
//    fun parseListFinance(page: Int, keyword: String?, completion: @escaping ([Notice]) -> Void) {
//        val noticeUrl = "$NoticeURL.businessFinanceURL$page"
//        var noticeList = ArrayList<Notice>()
//        var authorList = ArrayList<String>()
//        var titleList  = ArrayList<String>()
//        var urlList = ArrayList<String>()
//        var isNoticeList = ArrayList<Boolean>()
//        var dateStringList = ArrayList<String>()
//        var index = 0
//        var requestURL = ""
//
//        requestURL = if (keyword != null) {
//            val keywordSearch = keyword!.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)
//            val searchUrl = "http://finance.ssu.ac.kr/web/finance/menu5_1?p_p_id=EXT_BBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_EXT_BBS_struts_action=%2Fext%2Fbbs%2Fview&_EXT_BBS_sCategory=&_EXT_BBS_sTitle=$keywordSearch&_EXT_BBS_sWriter=&_EXT_BBS_sTag=&_EXT_BBS_sContent=&_EXT_BBS_sCategory2=&_EXT_BBS_sKeyType=title&_EXT_BBS_sKeyword=$keywordSearch&_EXT_BBS_curPage=$page"
//            searchUrl
//        } else {
//            noticeUrl
//        }
//
//        Alamofire.request(requestURL).responseString(encoding: .utf8) { response in
//                when (response.result) {
//                    case .success(_):
//                    if let data = response.result.value {
//                        do {
//                            let doc = try HTML(html: data, encoding: .utf8)
//                                for product in doc.css("table[class='bbs-list'] td") {
//                                    //print("***")
//                                    let content = product.text!.trimmingCharacters(in: .whitespacesAndNewlines)
//                                    print(content)
//                                    switch (index % 5) {
//                                        case 0:
//                                        if product.innerHTML?.contains("img") ?? false {
//                                        // isNotice
//                                        isNoticeList.append(true)
//                                    } else {
//                                        isNoticeList.append(false)
//                                    }
//                                        break
//                                        case 1:
//                                        // Title
//                                        titleList.append(content)
//                                        break
//                                        case 2:
//                                        // Author
//                                        authorList.append("")
//                                        break
//                                        case 3:
//                                        // Date
//                                        dateStringList.append(content)
//                                        break
//                                        case 4: break
//                                        case 5: break
//                                        default: break
//                                    }
//                                    index += 1
//                                }
//
//                                for product in doc.css("table[class='bbs-list'] a") {
//                                    print(product["href"] ?? "")
//                                    urlList.append(product["href"] ?? "")
//                                }
//                            } catch let error {
//                                print("Error : \(error)")
//                            }
//
//                            index = 0
//                            for _ in urlList {
//                                let noticeItem = Notice(author: authorList[index], title: titleList[index], url: urlList[index], date: dateStringList[index], isNotice: isNoticeList[index])
//                                noticeList.append(noticeItem)
//                                index += 1
//                            }
//
//                            completion(noticeList)
//                        }
//                    case .failure(_):
//                    print("Error message:\(String(describing: response.result.error))")
//                    break
//                }
//        }
//    }
}
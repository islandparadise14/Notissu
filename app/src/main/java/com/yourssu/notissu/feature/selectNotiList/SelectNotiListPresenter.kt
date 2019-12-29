package com.yourssu.notissu.feature.selectNotiList

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import com.yourssu.notissu.model.Notice
import com.yourssu.notissu.parser.*
import com.yourssu.notissu.utils.NetworkCheck

class SelectNotiListPresenter : SelectNotiListContract.Presenter {
    override lateinit var view: SelectNotiListContract.View

    override fun loadItem(majorNumber: Int, page: Int, keyword: String?, complete: (ArrayList<Notice>) -> Unit) {
        when (majorNumber) {
            -1 -> {NoticeSSUCatch.parseListSSUCatch(page= page, keyword = keyword, completion = complete)}
            0 -> {NoticeIT.parseListComputer(page= page, keyword = keyword, completion = complete)}
            1 -> {NoticeIT.parseListMedia(page= page, keyword = keyword, completion = complete)}
            2 -> {NoticeIT.parseListElectric(page= page, keyword = keyword, completion = complete)}
            3 -> {NoticeIT.parseListSoftware(page= page, keyword = keyword, completion = complete)}
            4 -> {NoticeIT.parseListSmartSystem(page= page, keyword = keyword, completion = complete)}
            5 -> {NoticeLaw.parseListLaw(page= page, keyword = keyword, completion = complete)}
            6 -> {NoticeLaw.parseListIntlLaw(page= page, keyword = keyword, completion = complete)}
            7 -> {NoticeHuman.parseListKorean(page= page, keyword = keyword, completion = complete)}
            8 -> {NoticeHuman.parseListFrench(page= page, keyword = keyword, completion = complete)}
            9 -> {NoticeHuman.parseListGerman(page= page, keyword = keyword, completion = complete)}
            10 -> {NoticeHuman.parseListChinese(page= page, keyword = keyword, completion = complete)}
            11 -> {NoticeHuman.parseListEnglish(page= page, keyword = keyword, completion = complete)}
            12 -> {NoticeHuman.parseListHistory(page= page, keyword = keyword, completion = complete)}
            13 -> {NoticeHuman.parseListPhilo(page= page, keyword = keyword, completion = complete)}
            14 -> {NoticeHuman.parseListJapanese(page= page, keyword = keyword, completion = complete)}
            15 -> {NoticeEngineer.parseListChemistryEngineering(page= page, keyword = keyword, completion = complete)}
            16 -> {NoticeEngineer.parseListMachine(page= page, keyword = keyword, completion = complete)}
            17 -> {NoticeEngineer.parseListElectric(page= page, keyword = keyword, completion = complete)}
            18 -> {NoticeEngineer.parseListIndustry(page= page, keyword = keyword, completion = complete)}
            19 -> {NoticeEngineer.parseListOrganic(page= page, keyword = keyword, completion = complete)}
            20 -> {NoticeScience.parseListMath(page= page, keyword = keyword, completion = complete)}
            21 -> {NoticeScience.parseListPhysics(page= page, keyword = keyword, completion = complete)}
            22 -> {NoticeScience.parseListChemistry(page= page, keyword = keyword, completion = complete)}
            23 -> {NoticeScience.parseListActuarial(page= page, keyword = keyword, completion = complete)}
            24 -> {NoticeScience.parseListBiomedical(page= page, keyword = keyword, completion = complete)}
            25 -> {NoticeBusiness.parseListBiz(page= page, keyword = keyword, completion = complete)}
            26 -> {NoticeBusiness.parseListVenture(page= page, keyword = keyword, completion = complete)}
            27 -> {NoticeBusiness.parseListAccount(page= page, keyword = keyword, completion = complete)}
            28 -> {NoticeBusiness.parseListFinance(page= page, keyword = keyword, completion = complete)}
            29 -> {NoticeEconomy.parseListEconomics(page= page, keyword = keyword, completion = complete)}
            30 -> {NoticeEconomy.parseListGlobalCommerce(page= page, keyword = keyword, completion = complete)}
            31 -> {NoticeSocial.parseListWelfare(page= page, keyword = keyword, completion = complete)}
            32 -> {NoticeSocial.parseListAdministration(page= page, keyword = keyword, completion = complete)}
            33 -> {NoticeSocial.parseListSociology(page= page, keyword = keyword, completion = complete)}
            34 -> {NoticeSocial.parseListJournalism(page= page, keyword = keyword, completion = complete)}
            35 -> {NoticeSocial.parseListLifeLong(page= page, keyword = keyword, completion = complete)}
            36 -> {NoticeSocial.parseListPolitical(page= page, keyword = keyword, completion = complete)}
            37 -> {NoticeConvergence.parseListConvergence(page= page, keyword = keyword, completion = complete)}
            38 -> {NoticeEngineer.parseListArchitecture(page=page, keyword = keyword, completion = complete)}
        }
    }

    override fun checkNetwork(context: Context?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context?.let {
                if (NetworkCheck().getConnectivityStatus(it) == 0) {
                    Toast.makeText(it, "인터넷연결을 확인해주세요", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        } else {
            context?.let {
                if (NetworkCheck().getConnectivityStatusforLowVersion(it) == 0) {
                    Toast.makeText(it, "인터넷연결을 확인해주세요", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        return true
    }
}
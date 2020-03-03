package com.yourssu.notissu.feature.notiDetail

import android.content.Context
import android.os.Build
import android.widget.Toast
import com.yourssu.notissu.model.NoticeDetail
import com.yourssu.notissu.parser.detail.*
import com.yourssu.notissu.utils.NetworkCheck

class NotiDetailPresenter: NotiDetailContract.Presenter {
    override lateinit var view: NotiDetailContract.View

    override fun loadItem(majorNumber: Int, url: String, complete: (NoticeDetail) -> Unit) {
        when (majorNumber) {
            -1 -> {
                DetailNoticeSSUCatch.parseSSUCatch(url = url, completion = complete)}
            0 -> {
                DetailNoticeIT.parseComputer(url= url, completion = complete)}
            1 -> {
                DetailNoticeIT.parseMedia(url= url, completion = complete)}
            2 -> {
                DetailNoticeIT.parseElectric(url= url, completion = complete)}
            3 -> {
                DetailNoticeIT.parseSoftware(url= url, completion = complete)}
            4 -> {
                DetailNoticeIT.parseSmartSystem(url= url, completion = complete)}
            5 -> {
                DetailNoticeLaw.parseLaw(url= url, completion = complete)}
            6 -> {
                DetailNoticeLaw.parseIntLaw(url= url, completion = complete)}
            7 -> {
                DetailNoticeHuman.parseKorea(url= url, completion = complete)}
            8 -> {
                DetailNoticeHuman.parseFrench(url= url, completion = complete)}
            9 -> {
                DetailNoticeHuman.parseListGerman(url= url, completion = complete)}
            10 -> {
                DetailNoticeHuman.parseChinese(url= url, completion = complete)}
            11 -> {
                DetailNoticeHuman.parseEnglish(url= url, completion = complete)}
            12 -> {
                DetailNoticeHuman.parseHistory(url= url, completion = complete)}
            13 -> {
                DetailNoticeHuman.parsePhilo(url= url, completion = complete)}
            14 -> {
                DetailNoticeHuman.parseJapanese(url= url, completion = complete)}
            15 -> {
                DetailNoticeEngineer.parseChemistryEngineering(url= url, completion = complete)}
            16 -> {
                DetailNoticeEngineer.parseMachine(url= url, completion = complete)}
            17 -> {
                DetailNoticeEngineer.parseElectric(url= url, completion = complete)}
            18 -> {
                DetailNoticeEngineer.parseIndustry(url= url, completion = complete)}
            19 -> {
                DetailNoticeEngineer.parseOrganic(url= url, completion = complete)}
            20 -> {
                DetailNoticeScience.parseMath(url= url, completion = complete)}
            21 -> {
                DetailNoticeScience.parsePhysics(url= url, completion = complete)}
            22 -> {
                DetailNoticeScience.parseChemistry(url= url, completion = complete)}
            23 -> {
                DetailNoticeScience.parseActuarial(url= url, completion = complete)}
            24 -> {
                DetailNoticeScience.parseBiomedical(url= url, completion = complete)}
            25 -> {
                DetailNoticeBiz.parseBiz(url= url, completion = complete)}
            26 -> {
                DetailNoticeBiz.parseVenture(url= url, completion = complete)}
            27 -> {
                DetailNoticeBiz.parseAccount(url= url, completion = complete)}
            28 -> {
                DetailNoticeBiz.parseFinance(url= url, completion = complete)}
            29 -> {
                DetailNoticeEco.parseEconomics(url= url, completion = complete)}
            30 -> {
                DetailNoticeEco.parseGlobalCommerce(url= url, completion = complete)}
            31 -> {
                DetailNoticeSocial.parseWelfare(url= url, completion = complete)}
            32 -> {
                DetailNoticeSocial.parseAdministration(url= url, completion = complete)}
            33 -> {
                DetailNoticeSocial.parseSociology(url= url, completion = complete)}
            34 -> {
                DetailNoticeSocial.parseJournalism(url= url, completion = complete)}
            35 -> {
                DetailNoticeSocial.parseLifeLong(url= url, completion = complete)}
            36 -> {
                DetailNoticeSocial.parsePolitical(url= url, completion = complete)}
            37 -> {
                DetailNoticeConvergence.parseConvergence(url= url, completion = complete)}
            38 -> {
                DetailNoticeEngineer.parseArchitecture(url = url, completion = complete)}
            39 -> {
                DetailNoticeHuman.parseWriting(url = url, completion = complete)}
        }
    }

    override fun checkNetwork(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context?.let {
                if (NetworkCheck().getConnectivityStatus(it) == 0)
                    Toast.makeText(it, "인터넷연결을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        } else {
            context?.let {
                if (NetworkCheck().getConnectivityStatusforLowVersion(it) == 0)
                    Toast.makeText(it, "인터넷연결을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
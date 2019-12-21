package com.yourssu.notissu.feature.majorList

import android.view.View
import com.yourssu.notissu.data.MajorData

class MajorListPresenter: MajorListContract.Presenter {
    override lateinit var view: MajorListContract.View
    override lateinit var data: MajorData

    override fun loadItem(mView: View) {
        view.update(
            mView,
            data.getItMajors(),
            data.getLawMajors(),
            data.getHumanMajors(),
            data.getEngineerMajors(),
            data.getScienceMajors(),
            data.getBizMajors(),
            data.getEcoMajors(),
            data.getSocialMajors(),
            data.getConvergenceMajors()
        )
    }

}
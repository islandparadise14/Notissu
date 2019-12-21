package com.yourssu.notissu.feature.search

import android.view.View
import com.yourssu.notissu.data.MajorData

class SearchPresenter: SearchContract.Presenter {
    override lateinit var view: SearchContract.View

    override lateinit var data: MajorData

    override fun loadItem(mView: View) {
        view.update(mView, data.getMajors().map { t -> t.name })
    }

}
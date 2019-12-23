package com.yourssu.notissu.feature.search

import android.view.View
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.model.Major

class SearchPresenter: SearchContract.Presenter {
    override lateinit var view: SearchContract.View

    override lateinit var data: MajorData

    override fun loadItem(mView: View) {
        val searchList = ArrayList<Major>()
        searchList.add(Major(-1, "SSU:Catch", "SSU:Catch"))
        searchList.addAll(data.getMajors())
        view.update(mView, searchList.map { t -> t.name })
    }

}
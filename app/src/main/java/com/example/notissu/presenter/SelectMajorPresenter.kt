package com.example.notissu.presenter

import com.example.notissu.data.MajorData

class SelectMajorPresenter : SelectMajorContract.Presenter {
    override lateinit var view: SelectMajorContract.View
    override lateinit var data: MajorData

    override fun loadItem() {
        view.update(data.getMajors().map { t -> t.name })
    }
}
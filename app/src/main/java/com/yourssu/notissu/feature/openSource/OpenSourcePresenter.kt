package com.yourssu.notissu.feature.openSource

import com.yourssu.notissu.data.OpenSourceData

class OpenSourcePresenter: OpenSourceContract.Presenter {
    override lateinit var view: OpenSourceContract.View
    override lateinit var data: OpenSourceData

    override fun loadItem() {
        view.update(data.getOpenSources())
    }

}
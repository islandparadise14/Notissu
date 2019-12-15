package com.example.notissu.feature.selectMajor

import com.example.notissu.data.MajorData

interface SelectMajorContract {
    interface View {
        fun update(item: List<String>)
    }

    interface Presenter {
        var view: View
        var data: MajorData

        fun loadItem()
    }
}
package com.yourssu.notissu.feature.majorList

import android.view.View
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.model.Major

interface MajorListContract {

    interface View {
        fun update(view: android.view.View,
                   it: ArrayList<Major>,
                   law: ArrayList<Major>,
                   human: ArrayList<Major>,
                   engineer: ArrayList<Major>,
                   science: ArrayList<Major>,
                   biz: ArrayList<Major>,
                   eco: ArrayList<Major>,
                   social: ArrayList<Major>,
                   convergence: ArrayList<Major>
        )
    }

    interface Presenter {
        var view: View
        var data: MajorData

        fun loadItem(mView: android.view.View)
    }
}
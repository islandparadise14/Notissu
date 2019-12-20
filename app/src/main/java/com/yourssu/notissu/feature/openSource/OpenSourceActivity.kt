package com.yourssu.notissu.feature.openSource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.data.OpenSourceData
import com.yourssu.notissu.model.OpenSource
import kotlinx.android.synthetic.main.activity_open_source.*

class OpenSourceActivity : AppCompatActivity(), OpenSourceContract.View {
    private lateinit var presenter: OpenSourcePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source)

        openSourceTitle.setTitle("오픈소스 사용정보")
        presenter = OpenSourcePresenter()
        presenter.view = this@OpenSourceActivity
        presenter.data = OpenSourceData.getInstance()
        presenter.loadItem()

    }

    override fun update(list: ArrayList<OpenSource>) {
        openSourceRecycler.adapter = OpenSourceAdapter(list)
        openSourceRecycler.layoutManager = LinearLayoutManager(application, RecyclerView.VERTICAL, false)
    }
}

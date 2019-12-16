package com.yourssu.notissu.feature.majorList


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.model.Major
import kotlinx.android.synthetic.main.fragment_major_list.*
import kotlinx.android.synthetic.main.fragment_major_list.view.*

/**
 * A simple [Fragment] subclass.
 */
class MajorListFragment : Fragment(), MajorListContract.View {
    private lateinit var presenter: MajorListPresenter

    companion object {
        @JvmStatic
        fun getInstance() = MajorListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_major_list, container, false)

        presenter = MajorListPresenter()
        presenter.view = this@MajorListFragment
        presenter.data = MajorData.getInstance()
        presenter.loadItem(view)

        return view
    }

    override fun update(view: View,
        it: ArrayList<Major>,
        law: ArrayList<Major>,
        human: ArrayList<Major>,
        engineer: ArrayList<Major>,
        science: ArrayList<Major>,
        biz: ArrayList<Major>,
        eco: ArrayList<Major>,
        social: ArrayList<Major>,
        convergence: ArrayList<Major>
    ) {
        view.itMajors.adapter = MajorAdapter(it)
        view.itMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.lawMajors.adapter = MajorAdapter(law)
        view.lawMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.humanMajors.adapter = MajorAdapter(human)
        view.humanMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.engineerMajors.adapter = MajorAdapter(engineer)
        view.engineerMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.scienceMajors.adapter = MajorAdapter(science)
        view.scienceMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.bizMajors.adapter = MajorAdapter(biz)
        view.bizMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.ecoMajors.adapter = MajorAdapter(eco)
        view.ecoMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.socialMajors.adapter = MajorAdapter(social)
        view.socialMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.convergenceMajors.adapter = MajorAdapter(convergence)
        view.convergenceMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }


}

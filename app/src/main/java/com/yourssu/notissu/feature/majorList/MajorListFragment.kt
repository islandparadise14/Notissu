package com.yourssu.notissu.feature.majorList


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.feature.main.MainActivity
import com.yourssu.notissu.feature.majorNotiList.MajorNotiActivity
import com.yourssu.notissu.model.Major
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
        view.itMajors.adapter = MajorAdapter(it, clicked)
        view.itMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.lawMajors.adapter = MajorAdapter(law, clicked)
        view.lawMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.humanMajors.adapter = MajorAdapter(human, clicked)
        view.humanMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.engineerMajors.adapter = MajorAdapter(engineer, clicked)
        view.engineerMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.scienceMajors.adapter = MajorAdapter(science, clicked)
        view.scienceMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.bizMajors.adapter = MajorAdapter(biz, clicked)
        view.bizMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.ecoMajors.adapter = MajorAdapter(eco, clicked)
        view.ecoMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.socialMajors.adapter = MajorAdapter(social, clicked)
        view.socialMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.convergenceMajors.adapter = MajorAdapter(convergence, clicked)
        view.convergenceMajors.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    val clicked = { position: Int ->
        (activity as MainActivity).intentActivity(position)
    }
}

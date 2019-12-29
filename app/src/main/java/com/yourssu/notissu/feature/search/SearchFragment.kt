package com.yourssu.notissu.feature.search


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yourssu.notissu.R
import com.yourssu.notissu.data.KEYWORD_INTENT_KEY
import com.yourssu.notissu.data.MAJOR_INTENT_KEY
import com.yourssu.notissu.data.MajorData
import com.yourssu.notissu.feature.majorNotiList.MajorNotiActivity
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment, SearchContract.View {

    constructor()

    private var majorNumber: Int = 0
    lateinit var presenter: SearchPresenter


    companion object {
        @JvmStatic
        fun getInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        presenter = SearchPresenter()

        presenter.view = this@SearchFragment

        presenter.data = MajorData.getInstance()

        presenter.loadItem(view)

        view.to_search_result.setOnClickListener {
            val keyword = search_keyword.text.toString()
            if (keyword.isEmpty()){
                Toast.makeText(context!!, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, MajorNotiActivity::class.java)
                intent.putExtra(MAJOR_INTENT_KEY, majorNumber)
                intent.putExtra(KEYWORD_INTENT_KEY, keyword)
                startActivity(intent)
            }
        }

        return view
    }

    override fun update(mView: View, item: List<String>) {
        val majorAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, item)

        mView.spinnerMajor.adapter = majorAdapter

        mView.spinnerMajor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                majorNumber = position - 1
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

}

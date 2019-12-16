package com.yourssu.notissu.feature.majorList


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yourssu.notissu.R

/**
 * A simple [Fragment] subclass.
 */
class MajorListFragment : Fragment() {

    companion object {
        @JvmStatic
        fun getInstance() = MajorListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_major_list, container, false)
        return view
    }


}

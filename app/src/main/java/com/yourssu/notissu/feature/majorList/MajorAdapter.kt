package com.yourssu.notissu.feature.majorList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.model.Major
import kotlinx.android.synthetic.main.item_major.view.*
import java.util.zip.Inflater

class MajorAdapter(var majors: ArrayList<Major>?) : RecyclerView.Adapter<MajorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MajorViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_major, parent, false)
        return MajorViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if(majors != null)
            majors!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: MajorViewHolder, position: Int) {
        majors?.let {
            holder.itemView.majorName.text = it[position].name
            holder.itemView.majorNameEng.text = it[position].engName
            if (it.size - 1 == position)
                holder.itemView.underLine.visibility = View.GONE
        }
    }

}

class MajorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
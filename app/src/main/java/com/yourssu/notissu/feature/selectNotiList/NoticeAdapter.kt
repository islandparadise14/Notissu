package com.yourssu.notissu.feature.selectNotiList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.model.Notice
import kotlinx.android.synthetic.main.item_noti.view.*

class NoticeAdapter(callback : DiffUtil.ItemCallback<Notice>, var notices: ArrayList<Notice>?, val clicked: (String?) -> Unit) : ListAdapter<Notice, RecyclerView.ViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_noti, parent, false)
        return NoticeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if(notices != null)
            notices!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        notices?.let {
            holder.itemView.listNotiTitle.text = it[position].title
            holder.itemView.listNotiDate.text = it[position].date
            val url = it[position].url
            if (it[position].isNotice == true)
                holder.itemView.listIsNoti.visibility = View.VISIBLE
            else
                holder.itemView.listIsNoti.visibility = View.GONE

            holder.itemView.setOnClickListener { clicked(url) }
        }
    }

}

class NoticeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
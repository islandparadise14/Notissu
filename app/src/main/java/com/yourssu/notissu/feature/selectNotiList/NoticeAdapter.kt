package com.yourssu.notissu.feature.selectNotiList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.model.NoticeItem
import kotlinx.android.synthetic.main.item_noti.view.*

class NoticeAdapter(callback : DiffUtil.ItemCallback<NoticeItem>, var notices: ArrayList<NoticeItem>?, val clicked: (String?, String?, String?) -> Unit) : ListAdapter<NoticeItem, RecyclerView.ViewHolder>(callback) {
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
            val title = it[position].title
            val date = it[position].date
            val url = it[position].url
            holder.itemView.listNotiTitle.text = title
            holder.itemView.listNotiDate.text = date
            if (it[position].isNotice == true)
                holder.itemView.listIsNoti.visibility = View.VISIBLE
            else
                holder.itemView.listIsNoti.visibility = View.GONE

            holder.itemView.setOnClickListener { clicked(title, date, url) }
        }
    }

}

class NoticeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
package com.yourssu.notissu.feature.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.model.File
import com.yourssu.notissu.room.entity.Notice
import kotlinx.android.synthetic.main.item_file.view.*
import kotlinx.android.synthetic.main.item_noti.view.*

class BookmarkAdapter(var noticeList: List<Notice>?, val clicked: (String, String, String, Int) -> Unit) : RecyclerView.Adapter<BookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_noti, parent, false)
        return BookmarkViewHolder(itemView, itemView.listNotiTitle, itemView.listNotiDate)
    }

    override fun getItemCount(): Int {
        return if(noticeList != null)
            noticeList!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        noticeList?.let {
            holder.listNotiTitle.text = it[position].title
            holder.listNotiDate.text = it[position].date
            holder.itemView.setOnClickListener { _ ->
                clicked(it[position].url, it[position].title, it[position].date, it[position].majorNumber)
            }
        }
    }

}

class BookmarkViewHolder(itemView: View, val listNotiTitle: TextView, val listNotiDate: TextView): RecyclerView.ViewHolder(itemView)
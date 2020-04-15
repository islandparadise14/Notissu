package com.yourssu.notissu.feature.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.model.File
import com.yourssu.notissu.room.entity.Notice
import kotlinx.android.synthetic.main.item_file.view.*
import kotlinx.android.synthetic.main.item_noti.view.*

class BookmarkAdapter(var noticeList: List<Notice>?, val clicked: (String?, String?) -> Unit) : RecyclerView.Adapter<BookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_noti, parent, false)
        return BookmarkViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if(noticeList != null)
            noticeList!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        noticeList?.let {
            holder.itemView.listNotiTitle.text = it[position].title
        }
    }

}

class BookmarkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
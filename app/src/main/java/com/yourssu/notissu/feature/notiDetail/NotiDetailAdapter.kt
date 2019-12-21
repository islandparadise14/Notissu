package com.yourssu.notissu.feature.notiDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.model.File
import kotlinx.android.synthetic.main.item_file.view.*

class NotiDetailAdapter(var fileList: ArrayList<File>?, val clicked: (String?, String?) -> Unit) : RecyclerView.Adapter<NoticeDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeDetailViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_file, parent, false)
        return NoticeDetailViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if(fileList != null)
            fileList!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: NoticeDetailViewHolder, position: Int) {
        fileList?.let {
            val url = it[position].fileURL
            val name = it[position].fileName
            holder.itemView.fileTitle.text = name
            holder.itemView.downloadText.setOnClickListener { clicked(url, name) }
        }
    }

}

class NoticeDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
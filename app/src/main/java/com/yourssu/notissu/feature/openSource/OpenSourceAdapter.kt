package com.yourssu.notissu.feature.openSource

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yourssu.notissu.R
import com.yourssu.notissu.model.OpenSource
import kotlinx.android.synthetic.main.item_open_source.view.*

class OpenSourceAdapter(var openSource: ArrayList<OpenSource>?) : RecyclerView.Adapter<OpenSourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenSourceViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_open_source, parent, false)
        return OpenSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if(openSource != null)
            openSource!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: OpenSourceViewHolder, position: Int) {
        openSource?.let {
            holder.itemView.open_title.text = it[position].title
            holder.itemView.open_url.text = it[position].url
            holder.itemView.open_content.text = it[position].content
        }
    }

}

class OpenSourceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
package com.yan.ahtbibleaudio002.views.adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.yan.ahtbibleaudio002.R
import com.yan.ahtbibleaudio002.models.AudioItem
import kotlinx.android.synthetic.main.item_audio.view.*

class AudioAdapter(
    private val audioItemList: List<AudioItem>,
    private val context: Context,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_audio,
            parent, false
        )
        return AudioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val currentItem = audioItemList[position]
        holder.textView1.text = currentItem.title
        holder.textView2.text = currentItem.description
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener(
                currentItem,
                position
            )
        }
    }

    override fun getItemCount() = audioItemList.size
    inner class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.cv_title
        val textView2: TextView = itemView.cv_desc
    }

}
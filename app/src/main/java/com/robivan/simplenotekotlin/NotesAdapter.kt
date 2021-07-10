package com.robivan.simplenotekotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class NotesAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    private var data: List<NoteEntity> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(note: NoteEntity?)
    }

    fun setOnItemClickListener(onItemClickListener: (Any) -> Unit) {
        this.onItemClickListener = onItemClickListener as OnItemClickListener
    }

    fun setData(notes: List<NoteEntity>) {
        data = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, onItemClickListener!!)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
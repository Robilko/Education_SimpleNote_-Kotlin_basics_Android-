package com.robivan.simplenotekotlin

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteViewHolder(itemView: View, clickListener: NotesAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.subject_title_view)
    private val bodyTextView: TextView = itemView.findViewById(R.id.subject_text_view)
    private val cardView: CardView = itemView as CardView
    private var noteEntity: NoteEntity? = null
    fun bind(noteEntity: NoteEntity) {
        this.noteEntity = noteEntity
        titleTextView.text = noteEntity.title
        bodyTextView.text = noteEntity.noteText
    }

    init {
        cardView.setOnClickListener { v: View ->
            val popupMenu = PopupMenu(v.context, v)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.edit_note_popup -> {
                        clickListener.onItemClick(noteEntity)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.add_note_to_favorite_popup, R.id.delete_popup -> {
                        Toast.makeText(
                            v.context, v.resources.getString(R.string.do_not_realised_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnMenuItemClickListener true
                    }
                }
                true
            }
            popupMenu.show()
        }
    }
}
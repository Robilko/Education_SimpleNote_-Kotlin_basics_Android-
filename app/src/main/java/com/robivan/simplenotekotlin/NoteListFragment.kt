package com.robivan.simplenotekotlin

import android.content.Context
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.ArrayList

class NoteListFragment : Fragment() {
    private var createNoteButton: MaterialButton? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: NotesAdapter? = null
    private val noteList = ArrayList<NoteEntity>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        createNoteButton = view.findViewById(R.id.create_new_note)
        recyclerView = view.findViewById(R.id.recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NotesAdapter()
        adapter!!.setOnItemClickListener { item -> contract!!.editNote(item as NoteEntity?) }
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.adapter = adapter
        adapter!!.setData(noteList)
        createNoteButton!!.setOnClickListener { contract!!.createNewNote() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        check(context is Contract) { "Activity must implement Contract" }
    }

    fun addNote(note: NoteEntity?) {
        val sameNote = note?.let { findNoteById(it.id) }
        if (sameNote != null) {
            noteList.remove(sameNote)
        }
        if (note != null) {
            noteList.add(note)
        }
        adapter!!.setData(noteList)
    }

    private fun findNoteById(id: String): NoteEntity? {
        for (note in noteList) {
            if (note.id == id) return note
        }
        return null
    }

    private val contract: Contract?
        get() = activity as Contract?

    internal interface Contract {
        fun createNewNote()
        fun editNote(noteEntity: NoteEntity?)
    }

    companion object {
        val title: Int
            get() = R.string.notes_list_title
    }
}
package com.robivan.simplenotekotlin

import android.content.Context
import com.google.android.material.button.MaterialButton
import android.widget.EditText
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class EditNoteFragment : Fragment() {
    private var saveButton: MaterialButton? = null
    private var noteHeading: EditText? = null
    private var noteTextBody: EditText? = null
    private var noteDateCreate: TextView? = null
    private var note: NoteEntity? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_note, container, false)
        saveButton = view.findViewById(R.id.save_btn)
        noteHeading = view.findViewById(R.id.note_heading)
        noteTextBody = view.findViewById(R.id.note_text_body)
        noteDateCreate = view.findViewById(R.id.note_date)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null) {
            note = requireArguments().getSerializable(NOTE_EXTRA_KEY) as NoteEntity?
        }
        fillNote(note)
        saveButton!!.setOnClickListener { contract!!.saveNote(collectNote()) }
    }

    private fun collectNote(): NoteEntity {
        return NoteEntity(
            if (note == null) NoteEntity.generateNewId() else note!!.id,
            noteHeading!!.text.toString(),
            noteTextBody!!.text.toString(),
            if (note == null) NoteEntity.currentDate else note!!.date
        )
    }

    private fun fillNote(note: NoteEntity?) {
        if (note == null) return
        noteHeading!!.setText(note.title)
        noteTextBody!!.setText(note.noteText)
        val dateCreate = resources.getString(R.string.note_item_date) + note.createDate
        noteDateCreate!!.text = dateCreate
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        check(context is Contract) { "Activity must implement Contract" }
    }

    private val contract: Contract?
        get() = activity as Contract?

    internal interface Contract {
        fun saveNote(note: NoteEntity?)
    }

    companion object {
        private const val NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY"
        fun newInstance(noteEntity: NoteEntity?): EditNoteFragment {
            val fragment = EditNoteFragment()
            val bundle = Bundle()
            bundle.putSerializable(NOTE_EXTRA_KEY, noteEntity)
            fragment.arguments = bundle
            return fragment
        }

        fun getTitle(newNote: Boolean): Int {
            return if (newNote) R.string.create_note_title else R.string.edit_note_title
        }
    }
}
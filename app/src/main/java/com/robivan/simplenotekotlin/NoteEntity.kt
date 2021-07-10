package com.robivan.simplenotekotlin

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class NoteEntity(val id: String, val title: String, val noteText: String, val date: Long) :
    Serializable {
    val createDate: String

    companion object {
        fun generateNewId(): String {
            return UUID.randomUUID().toString()
        }

        val currentDate: Long
            get() = Calendar.getInstance().timeInMillis
    }

    init {
        val dateFormat = SimpleDateFormat("d-MM-y", Locale.getDefault())
        createDate = dateFormat.format(date)
    }
}
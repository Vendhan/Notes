package com.vendhan.notes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val timeStamp: String = "",
    val color: Int = -1,
    val isPinned: Boolean = false
)

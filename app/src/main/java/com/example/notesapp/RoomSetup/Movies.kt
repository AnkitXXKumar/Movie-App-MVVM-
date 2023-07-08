package com.example.notesapp.RoomSetup

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName =  "movies")
data class Movies(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val type : String,
    val name : String,
    var status : String
)
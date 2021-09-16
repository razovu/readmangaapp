package com.example.readmangaapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Manga(
    @PrimaryKey
    val url: String = "",

    val img: String = "",

    val name: String = "",

    val description: String = "",

    val volumes: List<Volume> = listOf(),

    val info: String = "",

    val rate: String = "",     //type String cause it may be not rated

    val read: Boolean = false,

    val favorite: Boolean = false,

    val lastReadVolumeUrl: String = ""
)
data class Volume( val volName: String, val volUrl: String)
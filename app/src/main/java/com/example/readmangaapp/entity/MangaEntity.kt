package com.example.readmangaapp.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MangaEntity(
    @PrimaryKey
    var url: String = "",

    @ColumnInfo(defaultValue = "")
    var img: String = "",

    @ColumnInfo(defaultValue = "")
    var name: String = "",

    @ColumnInfo(defaultValue = "")
    var description: String = "",

    @ColumnInfo(defaultValue = "")
    var descriptionImages: List<String> = listOf(),

    @ColumnInfo(defaultValue = "")
    var info: String = "",

    @ColumnInfo(defaultValue = "")
    var rate: String = "not rated",

    @ColumnInfo(defaultValue = false.toString())
    var read: Boolean = false,

    @ColumnInfo(defaultValue = false.toString())
    var favorite: Boolean = false,

    @ColumnInfo(defaultValue = "")
    var lastReadVolumeUrl: String = "",

): Parcelable
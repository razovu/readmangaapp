package com.example.readmangaapp.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class VolumeEntity(val volName: String, val volUrl: String): Parcelable
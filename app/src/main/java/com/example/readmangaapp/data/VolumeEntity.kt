package com.example.readmangaapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VolumeEntity(val volName: String, val volUrl: String): Parcelable
package com.example.readmangaapp.data.manga

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.readmangaapp.data.MangaEntity
import com.example.readmangaapp.data.profile.ProfileDao
import com.example.readmangaapp.utils.Converters

@TypeConverters(Converters::class)
@Database(entities = [MangaEntity::class], version = 1)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}
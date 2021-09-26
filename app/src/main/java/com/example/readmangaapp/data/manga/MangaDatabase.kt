package com.example.readmangaapp.data.manga

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.readmangaapp.data.Manga
import com.example.readmangaapp.data.profile.ProfileDao

@Database(entities = [Manga::class], version = 1)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}
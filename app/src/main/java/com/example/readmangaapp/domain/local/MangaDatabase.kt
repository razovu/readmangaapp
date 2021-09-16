package com.example.readmangaapp.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.readmangaapp.data.Manga

@Database(entities = [Manga::class], version = 1)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}
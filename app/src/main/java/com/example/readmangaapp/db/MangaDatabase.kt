package com.example.readmangaapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.utils.Converters

@TypeConverters(Converters::class)
@Database(entities = [MangaEntity::class], version = 2, exportSchema = true)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun profileDao(): MangaDao
}
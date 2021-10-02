package com.example.readmangaapp.data.profile

import androidx.room.*
import com.example.readmangaapp.data.MangaEntity

@Dao
interface ProfileDao {

    @Query("SELECT * FROM MangaEntity")
    fun getAll(): List<MangaEntity>

    @Query("SELECT * FROM MangaEntity WHERE read = :isRead")
    fun getHistory(isRead: Boolean = true): List<MangaEntity>

    @Query("SELECT * FROM MangaEntity WHERE url = :url")
    fun getByMangaUrl(url: String): MangaEntity

    @Query("SELECT * FROM MangaEntity WHERE favorite = :isFavorite")
    fun getFavorites(isFavorite: Boolean = true): List<MangaEntity>

    @Insert
    fun insert(mangaEntity: MangaEntity)

    @Update
    fun update(mangaEntity: MangaEntity)

    @Delete
    fun delete(mangaEntity: MangaEntity)
}
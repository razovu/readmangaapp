package com.example.readmangaapp.db

import androidx.room.*
import com.example.readmangaapp.entity.MangaEntity

@Dao
interface MangaDao {

    @Query("SELECT * FROM MangaEntity")
    fun getAll(): List<MangaEntity>?

    @Query("SELECT * FROM MangaEntity WHERE read = :isRead")
    fun getHistory(isRead: Boolean): List<MangaEntity>?

    @Query("SELECT * FROM MangaEntity WHERE url = :url")
    fun getByMangaUrl(url: String): MangaEntity?

    @Query("SELECT * FROM MangaEntity WHERE favorite = :isFavorite")
    fun getFavorites(isFavorite: Boolean): List<MangaEntity>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(mangaEntity: MangaEntity)

    @Update
    fun update(mangaEntity: MangaEntity)

    @Delete
    fun delete(mangaEntity: MangaEntity)
}
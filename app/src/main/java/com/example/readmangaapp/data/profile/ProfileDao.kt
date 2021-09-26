package com.example.readmangaapp.data.profile

import androidx.room.*
import com.example.readmangaapp.data.Manga

@Dao
interface ProfileDao {

    @Query("SELECT * FROM Manga")
    fun getAll(): List<Manga>

    @Query("SELECT * FROM Manga WHERE read = :isRead")
    fun getHistory(isRead: Boolean = true): List<Manga>

    @Query("SELECT * FROM Manga WHERE url = :url")
    fun getByMangaUrl(url: String): Manga

    @Query("SELECT * FROM Manga WHERE favorite = :isFavorite")
    fun getFavorites(isFavorite: Boolean = true): List<Manga>

    @Insert
    fun insert(manga: Manga)

    @Update
    fun update(manga: Manga)

    @Delete
    fun delete(manga: Manga)
}
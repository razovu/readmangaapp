package com.example.readmangaapp.data.profile

import android.annotation.SuppressLint
import com.example.readmangaapp.db.MangaDao
import com.example.readmangaapp.entity.MangaEntity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val mangaDao: MangaDao) {

    fun addToFavorites(mangaUrl: String) {
        val manga = getByMangaUrl(mangaUrl)

        manga.favorite = true
        mangaDao.update(manga)
    }

    fun removeFromFavorites(mangaUrl: String) {
        val manga = getByMangaUrl(mangaUrl)

        manga.favorite = false
        mangaDao.update(manga)
    }

    fun getFavorites(): List<MangaEntity> {
        return mangaDao.getFavorites(true) ?: listOf()
    }

    fun descriptionUpdate(entity: MangaEntity, url: String): MangaEntity {
        val manga = getByMangaUrl(url)

        manga.info = entity.info
        manga.descriptionImages = entity.descriptionImages
        manga.description = entity.description
        mangaDao.update(manga)

        return manga
    }

    @SuppressLint("SimpleDateFormat")
    fun addToHistory(mangaUrl: String, lastReadVolumeUrl: String, lastReadVolumeName: String, position: Int) {

        val manga = getByMangaUrl(mangaUrl)
        val posList = mutableListOf(position.toString()) + manga.readVolumesIndices
        manga.lastReadVolumeName = lastReadVolumeName
        manga.lastReadVolumeUrl = lastReadVolumeUrl
        manga.read = true
        manga.readVolumesIndices = posList.toMutableList()
        manga.lastReadTime = SimpleDateFormat("yyyy.MM.dd 'в' HH:mm").format(Date())

        mangaDao.update(manga)
    }



    fun getAll(): List<MangaEntity> {
        return mangaDao.getAll() ?: listOf()
    }

    fun getHistory(isRead: Boolean = true): List<MangaEntity> {
        return mangaDao.getHistory(isRead) ?: listOf()
    }

    fun getByMangaUrl(url: String): MangaEntity {
        return mangaDao.getByMangaUrl(url) ?: MangaEntity()
    }

    fun insert(mangaEntity: MangaEntity) {
        mangaDao.insert(mangaEntity)
    }

    fun update(mangaEntity: MangaEntity) {
        mangaDao.update(mangaEntity)
    }

    fun delete(mangaEntity: MangaEntity) {
        mangaDao.delete(mangaEntity)
    }

}

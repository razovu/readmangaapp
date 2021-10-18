package com.example.readmangaapp.data.profile

import android.util.Log
import com.example.readmangaapp.data.profile.local.ProfileDao
import com.example.readmangaapp.entity.MangaEntity
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    fun addToFavorites(mangaUrl: String){
        val manga = profileDao.getByMangaUrl(mangaUrl)
        if (manga != null) {
            manga.favorite = true
            profileDao.update(manga)
        }
    }

    fun removeFromFavorites(mangaUrl: String){
        val manga = profileDao.getByMangaUrl(mangaUrl)
        if (manga != null) {
            manga.favorite = false
            profileDao.update(manga)
        }
    }

    fun getFavorites(): List<MangaEntity> {
        return profileDao.getFavorites(true) ?: listOf()
    }

    fun descriptionUpdate(entity: MangaEntity){
        val manga = profileDao.getByMangaUrl(entity.url)
        if (manga != null) {
            manga.info = entity.info
            manga.descriptionImages = entity.descriptionImages
            manga.description = entity.description
            profileDao.update(manga)
        }
    }

    fun addToHistory(mangaUrl: String, lastReadVolumeUrl: String) {
        val manga = profileDao.getByMangaUrl(mangaUrl)
        if (manga != null) {
            manga.lastReadVolumeUrl = lastReadVolumeUrl
            manga.read = true
            profileDao.update(manga)
        }
    }

    fun getAll() : List<MangaEntity> {
        return profileDao.getAll() ?: listOf()
    }

    fun getHistory(isRead: Boolean = true): List<MangaEntity> {
        return profileDao.getHistory(isRead) ?: listOf()
    }

    fun getByMangaUrl(url: String): MangaEntity {
        return profileDao.getByMangaUrl(url) ?: MangaEntity()
    }

    fun insert(mangaEntity: MangaEntity) {
        profileDao.insert(mangaEntity)
    }

    fun update(mangaEntity: MangaEntity) {
        profileDao.update(mangaEntity)
    }

    fun delete(mangaEntity: MangaEntity) {
        profileDao.delete(mangaEntity)
    }

}

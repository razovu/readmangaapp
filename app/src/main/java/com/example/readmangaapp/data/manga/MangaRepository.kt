package com.example.readmangaapp.data.manga

import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.data.SiteContentParser
import com.example.readmangaapp.entity.VolumeEntity
import javax.inject.Inject

class MangaRepository @Inject constructor(private val siteContentParser: SiteContentParser) {

    suspend fun getCatalogList(offset: Int): List<MangaEntity> {
        return siteContentParser.loadCatalogList(offset)
    }

    suspend fun getSearchResponseList(offset: Int, query: String): List<MangaEntity> {
        return siteContentParser.searchManga(offset, query)
    }

    suspend fun getMangaDescription(mangaUri: String): MangaEntity {
        return siteContentParser.loadDescription(mangaUri)
    }

    suspend fun getMangaVolumeList(mangaUri: String): List<VolumeEntity> {
        return siteContentParser.loadMangaVolumeList(mangaUri)
    }

    suspend fun getVolumePages(volumeUri: String): List<String> {
        return siteContentParser.loadVolumePages(volumeUri)
    }
}
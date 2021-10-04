package com.example.readmangaapp.data.manga

import com.example.readmangaapp.data.MangaEntity
import com.example.readmangaapp.data.SiteContentParser
import com.example.readmangaapp.data.VolumeEntity
import javax.inject.Inject

class MangaRepository @Inject constructor(private val siteContentParser: SiteContentParser) {

    suspend fun getCatalogList(offset: Int): List<MangaEntity> {
        return siteContentParser.loadCatalogList(offset)
    }

    suspend fun getSearchResponseList(offset: Int, query: String): List<MangaEntity> {
        return siteContentParser.searchManga(offset, query)
    }

    suspend fun getMangaDescription(mangaLink: String): MangaEntity {
        return siteContentParser.loadDescription(mangaLink)
    }

    suspend fun getMangaVolumeList(mangaLink: String): List<VolumeEntity> {
        return siteContentParser.loadMangaVolumeList(mangaLink)
    }
}
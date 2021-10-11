package com.example.readmangaapp.data.news

import com.example.readmangaapp.entity.ReadMangaNewsEntity
import com.example.readmangaapp.data.SiteContentParser
import javax.inject.Inject

class NewsRepository @Inject constructor(private val siteContentParser: SiteContentParser)  {
    suspend fun getNewsList(offset: Int): List<ReadMangaNewsEntity> {
        return siteContentParser.getNewsList(offset)
    }
}
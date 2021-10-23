package com.example.readmangaapp.data.news

import com.example.readmangaapp.entity.NewsEntity
import com.example.readmangaapp.data.SiteContentParser
import javax.inject.Inject

class NewsRepository @Inject constructor(private val siteContentParser: SiteContentParser)  {
    suspend fun getNewsList(offset: Int): List<NewsEntity> {
        return siteContentParser.getNewsList(offset)
    }
}
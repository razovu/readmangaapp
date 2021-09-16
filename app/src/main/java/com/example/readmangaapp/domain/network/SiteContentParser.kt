package com.example.readmangaapp.domain.network

import com.example.readmangaapp.data.Manga
import org.jsoup.Jsoup

class SiteContentParser {


    private val baseUrl: String = "https://readmanga.live"
    private val catalogPrefix: String = "/list"
    private val adultPrefix: String = "?mtr=1"
    private val subSearch: String = "/search"
    private val subGenres: String = "/genres"
    private val offSetPrefix = "?offset="


    fun loadMangaList(offSet: Int? = 0): MutableList<Manga> {

        val listManga = mutableListOf<Manga>()

        val doc = Jsoup.connect(baseUrl + catalogPrefix + offSetPrefix + offSet + adultPrefix).get()
        val element = doc.select("div#wrap").select(".tile")

        for (i in 0 until element.size) {

            val ttl = element.select("img[src]").eq(i).attr("alt").substringBefore("(")
            val linkImage = element.select("img[src]").eq(i).attr("data-original")
            val linkManga = element.select("h3").select("a").eq(i).attr("href")
            listManga.add(
                Manga(
                    img = linkImage,
                    name = ttl,
                    url = linkManga
                )
            )
        }

        return listManga
    }
}
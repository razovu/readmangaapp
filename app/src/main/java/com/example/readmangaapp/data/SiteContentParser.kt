package com.example.readmangaapp.data

import android.util.Log
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SiteContentParser @Inject constructor() {


    private val baseUrl: String = "https://readmanga.io"
    private val catalogPrefix: String = "/list"
    private val adultPrefix: String = "?mtr=1"
    private val subSearch: String = "/search"
    private val subGenres: String = "/genres"
    private val offSetPrefix = "?offset="

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .callTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()


    private fun getDocument(url: String): Document {
        return try {
            Jsoup.connect(url).timeout(10000).get()
        } catch (e: IOException) {
            getDocument(url)

        } catch (e: SocketTimeoutException) {
            getDocument(url)
        }
    }


    fun loadCatalogList(offset: Int): MutableList<MangaEntity> {
        val listManga = mutableListOf<MangaEntity>()

        val doc = getDocument(baseUrl + catalogPrefix + offSetPrefix + offset)
        val element = doc.select("div#wrap").select(".tile")

        for (i in 0 until element.size) {

            val ttl = element.select("img[src]").eq(i).attr("alt").substringBefore("(")
            val linkImage = element.select("img[src]").eq(i).attr("data-original")
            val linkManga = element.select("h3").select("a").eq(i).attr("href")
            val rate = element.select(".star-rate").select(".rating").eq(i).attr("title")
            listManga.add(
                MangaEntity(
                    img = linkImage,
                    name = ttl,
                    url = linkManga,
                    rate = rate
                )
            )
        }
        return listManga
    }

    fun loadDescription(mangaLink: String): MangaEntity {

        val doc = getDocument(baseUrl + mangaLink)
        val element = doc.select(".expandable")

        val mangaImages = element.select(".picture-fotorama").select("img[src]").eachAttr("src")
        val mangaDesc = doc.selectFirst(".manga-description")?.text() ?: ""
        val mangaInfo = doc.select(".col-sm-7").select(".subject-meta").select("p").eachText()
        val mangaTitle = doc.selectFirst(".name")?.text() ?: ""

        return MangaEntity(
            descriptionImages = mangaImages,
            info = mangaInfo.joinToString("\n"),
            description = mangaDesc,
            name = mangaTitle
        )
    }

    //На вход подаются параметры post запроса.
    fun searchManga(paramOffsetSearch: Int, query: String): MutableList<MangaEntity> {

        val listManga = mutableListOf<MangaEntity>()

        val body = FormBody.Builder()
            .add("q", query)
            .add("offset", paramOffsetSearch.toString())
            .build()
        val req = Request.Builder().url(baseUrl + subSearch).post(body).build()
        val response = client.newCall(req).execute()
        val doc = Jsoup.parse(response.body()!!.string())

        try {
//            val element = doc.select("div[class=tile col-sm-6 ]")
            val element = doc.select("div#wrap").select(".tile")

            listManga.clear()
            for (i in 0 until element.size) {

                val ttl = element.select("img[src]").eq(i).attr("alt").substringBefore("(")
                val linkImage = element.select("img[src]").eq(i).attr("data-original")
                val linkManga = element.select("h3").select("a").eq(i).attr("href")
                val rate = element.select(".star-rate").select(".rating").eq(i).attr("title")
                if (ttl.isNotBlank()) listManga.add(
                    MangaEntity(
                        img = linkImage,
                        url = linkManga,
                        name = ttl,
                        rate = rate
                    )
                )
            }

            return listManga

        } catch (e: NullPointerException) {
            return listManga
        }
    }
}
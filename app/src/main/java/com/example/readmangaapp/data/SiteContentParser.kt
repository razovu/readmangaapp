package com.example.readmangaapp.data

import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.entity.ReadMangaNewsEntity
import com.example.readmangaapp.entity.VolumeEntity
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SiteContentParser @Inject constructor() {


    private val baseUrl: String = "https://readmanga.io"
    private val catalogPrefix: String = "/list"
    private val adultPrefix: String = "?mtr=1"
    private val subSearch: String = "/search"
    private val subGenres: String = "/genres"
    private val offSetPrefix = "?offset="
    private val newsPrefix = "/news/allnews"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .callTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()


    private fun getDocument(url: String): Document {
        val req = Request.Builder().url(url).build()
        val response = client.newCall(req).execute()
        return Jsoup.parse(response.body()!!.string())
//        return Jsoup.connect(url).timeout(10000).get()
    }

    private fun rateFormat(rate: String): String {
        return when (rate.length) {
            0 -> "not rated"
            1 -> rate
            2 -> rate
            3 -> rate
            else -> rate.substring(0..2)
        }
    }

    /** catalog list manga */
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
                    rate = rateFormat(rate)
                )
            )
        }
        return listManga
    }

    /** manga description */
    fun loadDescription(mangaLink: String): MangaEntity {

        val doc = getDocument(baseUrl + mangaLink)
        val element = doc.select(".expandable")

        val mangaImages = element.select(".picture-fotorama").select("img[src]").eachAttr("src")
        val mangaDesc = doc.selectFirst(".manga-description")?.text() ?: ""
        val mangaInfo = doc.select(".col-sm-7").select(".subject-meta").select("p").eachText()
        val mangaTitle = doc.selectFirst(".name")?.text() ?: ""

        //Убираем теги и популярность из информации о манге
        val mangaInfoMapped = mangaInfo.filterNot { it.contains("Теги:") }.dropLast(1)

        return MangaEntity(
            descriptionImages = mangaImages,
            info = mangaInfoMapped.joinToString("\n"),
            description = mangaDesc,
            name = mangaTitle
        )
    }

    /** manga volumes */
    fun loadMangaVolumeList(mangaLink: String): List<VolumeEntity> {

        val url = baseUrl + mangaLink
        val mangaVolumeList = mutableListOf<VolumeEntity>()
        val volNames = mutableListOf<String>()
        val volLinks = mutableListOf<String>()

        val doc = getDocument(url)
        val mangaTitle = doc.selectFirst(".name")?.text() ?: ""
        val element = doc.select(".expandable.chapters-link")

        element.select("a[href]")
            .forEach { volNames.add(it.text()) }

        element.select("a")
            .eachAttr("href")
            .map { volLinks.add(it.toString()) }

        //Каждая глава содержит название тайтла перед названием главы
        //Сразу укоротим, убрав название тайтла перед номером главы
        for (i in 0 until volNames.size) {
            mangaVolumeList.add(
                VolumeEntity(
                    volName = volNames[i].substringAfter(mangaTitle).trim(),
                    volUrl = volLinks[i]
                )
            )
        }
        /*  Тут есть неочевидность. Парсим главы с последней и до первой,
         т.е. mangaVolumeList.last() это первая глава, что не логично!
         Поэтому мы ее перевворачиваем по логике вещей  */
        return mangaVolumeList.reversed()
    }

    /** manga volume pages */
    fun loadVolumePages(volumeUri: String): List<String> {
        val imageList = mutableListOf<String>()
        val url = baseUrl + volumeUri + adultPrefix
        val doc = getDocument(url)

        //Тут ссылочки запакованы в суперстранный json4ик поэтому ручками распакуем
        val lineLinks = doc.data()
            .substringAfter("rm_h.initReader( [2,3], ")
            .substringBefore(", 0, false);")
            .replace("manga/", "")

        // Получили 3 эелемента (2 ссылки на сервер и 1 ссылка на пикчу)
        for (index in 0 until JSONArray(lineLinks).length()) {
            val tempList = JSONArray(lineLinks).getJSONArray(index)
            val link = tempList.get(0).toString() + tempList.get(2).toString()

            imageList.add(link)
        }
        return imageList
    }

    /** search manga */
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
                        rate = rateFormat(rate)
                    )
                )
            }

            return listManga

        } catch (e: NullPointerException) {
            return listManga
        }
    }

    /**---- News -----*/
    fun getNewsList(offset: Int): List<ReadMangaNewsEntity> {
        val list = mutableListOf<ReadMangaNewsEntity>()
        val doc = Jsoup.connect(baseUrl + newsPrefix + offSetPrefix + offset).get()
        val element = doc.select("div#wrap").select(".news-tiles").select(".col-md-6")
        println(element.size)
        for (i in 0 until element.size) {
            val postUrl = element.select("a").eq(i).attr("href")
            val postImg = element.select("img").eq(i).attr("data-original")
            val postTitle = element.select("img").eq(i).attr("title")
            val postDesc = element.select(".desc").select(".news-summary").eq(i).text()
            val postDate = element.select(".desc").select(".details").eq(i).text()
            list.add(
                ReadMangaNewsEntity(
                    postUrl = postUrl,
                    postDate = postDate,
                    postImg = postImg,
                    postTitle = postTitle,
                    postDescription = postDesc
                )
            )
        }
        return list
    }
}
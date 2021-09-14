package com.example.readmangaapp.data

data class Manga(
    val img: String = "",
    val name: String = "",
    val mangaUrl: String = "",
    val description: String = "",
    val volumes: List<Volume> = listOf(),
    val info: String = ""
)
data class Volume( val volName: String, val volUrl: String)
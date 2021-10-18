package com.example.readmangaapp.common

const val KEY_MANGA_URL = "MANGA_URL"
const val KEY_VOLUME_SELECTED = "VOLUME_SELECTED"
const val KEY_VOLUME_LIST = "VOLUME_LIST"
const val KEY_MANGA_ENTITY = "MANGA_ENTITY"
const val FIRST_VOLUME = 0
const val THERE_IS_NO_NEXT_VOLUME = "Следующей главы нет =("
const val THERE_IS_NO_PREV_VOLUME = "Предыдущей главы нет =)"




data class SomeData(
    val some1: String = "1",
    val some2: String = "2",
    val some3: String = "3",
    val some4: String = "4",
)
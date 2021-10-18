package com.example.readmangaapp.screens.description

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.entity.VolumeEntity
import com.example.readmangaapp.data.manga.MangaRepository
import com.example.readmangaapp.data.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(
    private val mangaRepository: MangaRepository,
    private val profileRepository: ProfileRepository
    ): ViewModel() {

    private val _mangaEntity = MutableLiveData<MangaEntity>()
    val mangaEntity = _mangaEntity

    private val _mangaVolumeList = MutableLiveData<List<VolumeEntity>>()
    val mangaVolumeList = _mangaVolumeList

    private var mangaUrl = ""

    init {
        getMangaEntity()
    }

    private fun getMangaEntity(){

        viewModelScope.launch(Dispatchers.Default) {
            val mangaDesc = mangaRepository.getMangaDescription(mangaUrl)
            val mangaVolumeList = mangaRepository.getMangaVolumeList(mangaUrl)


            _mangaEntity.postValue(mangaDesc)
            _mangaVolumeList.postValue(mangaVolumeList)

            profileRepository.addToFavorites(mangaUrl)
            profileRepository.descriptionUpdate(mangaDesc)

        }
    }

    fun getVolumeNamesArray() : Array<String>{
        val volumeList = mutableListOf<String>()
        if (!_mangaVolumeList.value.isNullOrEmpty()) {
            _mangaVolumeList.value!!.forEach { volumeList.add(it.volName) }
        }
        return volumeList.reversed().toTypedArray()
    }

    fun setMangaUrl(mangaUrl: String?) {
        this.mangaUrl = mangaUrl ?: ""
    }

    fun addToFavorites() {

    }

    fun removeFromFavorites() {

    }

    fun getMangaUri(): String = mangaUrl

}

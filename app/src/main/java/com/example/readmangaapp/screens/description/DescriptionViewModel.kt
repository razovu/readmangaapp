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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(
    private val mangaRepository: MangaRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _mangaEntity = MutableLiveData<MangaEntity>()
    val mangaEntity = _mangaEntity

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite = _isFavorite

    private val _mangaVolumeList = MutableLiveData<List<VolumeEntity>>()
    val mangaVolumeList = _mangaVolumeList

    private var mangaUrl = ""

    init {
        getMangaEntity()
    }

    private fun getMangaEntity() {

        viewModelScope.launch(Dispatchers.Default) {
            val mangaDesc = mangaRepository.getMangaDescription(mangaUrl)
            val mangaVolumeList = mangaRepository.getMangaVolumeList(mangaUrl)
            val isFav = profileRepository.getByMangaUrl(mangaUrl).favorite
            val newEntity = profileRepository.descriptionUpdate(mangaDesc, mangaUrl)

            _mangaVolumeList.postValue(mangaVolumeList)
            _mangaEntity.postValue(newEntity)
            _isFavorite.postValue(isFav)
        }
    }

    fun setMangaUrl(mangaUrl: String?) {
        this.mangaUrl = mangaUrl ?: ""
    }

    fun addToFavorite() {
        viewModelScope.launch(Dispatchers.Default) {
            profileRepository.addToFavorites(mangaUrl)
            _isFavorite.postValue(true)
        }
    }

    fun removeFromFavorites() {
        viewModelScope.launch(Dispatchers.Default) {
            profileRepository.removeFromFavorites(mangaUrl)
            _isFavorite.postValue(false)
        }
    }

    fun getMangaUri(): String = mangaUrl

}

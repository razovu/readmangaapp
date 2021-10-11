package com.example.readmangaapp.screens.description

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.entity.VolumeEntity
import com.example.readmangaapp.data.manga.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(private val mangaRepository: MangaRepository): ViewModel() {

    private var _mangaUri = ""

    private val _mangaEntity = MutableLiveData<MangaEntity>()
    val mangaEntity = _mangaEntity

    private val _mangaVolumeList = MutableLiveData<List<VolumeEntity>>()
    val mangaVolumeList = _mangaVolumeList

    init {
        getMangaEntity()
    }

    private fun getMangaEntity(){

        viewModelScope.launch(Dispatchers.Default) {
            _mangaEntity.postValue(mangaRepository.getMangaDescription(_mangaUri))
            _mangaVolumeList.postValue(mangaRepository.getMangaVolumeList(_mangaUri))
        }
    }

    fun getVolumeNamesArray() : Array<String>{
        val volumeList = mutableListOf<String>()
        if (!_mangaVolumeList.value.isNullOrEmpty()) {
            _mangaVolumeList.value!!.forEach { volumeList.add(it.volName) }
        }
        return volumeList.reversed().toTypedArray()
    }
    fun setMangaUri(mangaUri: String?) { _mangaUri = mangaUri ?: "" }
    fun getMangaUri(): String = _mangaUri
}

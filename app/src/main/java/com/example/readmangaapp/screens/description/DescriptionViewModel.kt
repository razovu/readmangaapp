package com.example.readmangaapp.screens.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.data.MangaEntity
import com.example.readmangaapp.data.VolumeEntity
import com.example.readmangaapp.data.manga.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(private val mangaRepository: MangaRepository): ViewModel() {

    private var url = ""

    private val _mangaEntity = MutableLiveData<MangaEntity>()
    val mangaEntity = _mangaEntity

    private val _mangaVolumeList = MutableLiveData<List<VolumeEntity>>()
    val mangaVolumeList = _mangaVolumeList

    init {
        getMangaEntity()
    }

    private fun getMangaEntity(){

        viewModelScope.launch(Dispatchers.Default) {
            _mangaEntity.postValue(mangaRepository.getMangaDescription(url))
            _mangaVolumeList.postValue(mangaRepository.getMangaVolumeList(url))
        }
    }

    fun getVolumeNamesArray() : Array<String>{
        val volumeList = mutableListOf<String>()
        if (!_mangaVolumeList.value.isNullOrEmpty()) {
            _mangaVolumeList.value!!.forEach { volumeList.add(it.volName) }
        }
        return volumeList.toTypedArray()
    }
    fun getMangaLink(mangaLink: String?) { url = mangaLink ?: "" }
}

package com.example.readmangaapp.screens.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.data.VolumeEntity
import com.example.readmangaapp.data.manga.MangaRepository
import com.google.android.material.internal.ContextUtils.getActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {
    private var _mangaUri = ""
    private var _volumeList = listOf<VolumeEntity>()
    private var _currentVolumePosition = 0
    private val _volumePages = MutableLiveData<List<String>>()
    val volumePages = _volumePages

    fun setNavArgs(mangaUri: String, volumeList: List<VolumeEntity>, volumePosition: Int) {
        _mangaUri = mangaUri
        _volumeList = volumeList
        _currentVolumePosition = volumePosition
    }

    fun loadVolumePages() {
        viewModelScope.launch(Dispatchers.Default) {
            val currentVolumeUri = _volumeList[_currentVolumePosition]
            _volumePages.postValue(mangaRepository.getVolumePages(currentVolumeUri.volUrl))
        }
    }

    fun loadNextVolumePages() = _currentVolumePosition + 1
    fun loadPreviousVolumePages() = _currentVolumePosition - 1


}
package com.example.readmangaapp.screens.reader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.common.THERE_IS_NO_NEXT_VOLUME
import com.example.readmangaapp.common.THERE_IS_NO_PREV_VOLUME
import com.example.readmangaapp.entity.VolumeEntity
import com.example.readmangaapp.data.manga.MangaRepository
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

    //Закинули аргументы навигации из вью слоя
    fun setNavArgs(mangaUri: String, volumeList: List<VolumeEntity>, volumePosition: Int) {
        _mangaUri = mangaUri
        _volumeList = volumeList
        _currentVolumePosition = volumePosition
    }

    fun loadVolumePages() {
        viewModelScope.launch(Dispatchers.Default) {
            _volumePages.postValue(mangaRepository.getVolumePages(_volumeList[_currentVolumePosition].volUrl))
        }
    }

    fun getCurrentVolumeName(): String = _volumeList[_currentVolumePosition].volName

    fun getNextVolumeName(): String {
        return if (_currentVolumePosition + 1 in _volumeList.indices) {
            _volumeList[_currentVolumePosition + 1].volName
        } else THERE_IS_NO_NEXT_VOLUME
    }

    fun getPreviousVolumeName(): String {
        return if (_currentVolumePosition - 1 in _volumeList.indices) {
            _volumeList[_currentVolumePosition - 1].volName
        } else THERE_IS_NO_PREV_VOLUME
    }

    fun goToNextVolume() {
        if (_currentVolumePosition + 1 in _volumeList.indices) {
            _currentVolumePosition += 1
            loadVolumePages()
        }
    }

    fun goToPreviousVolume() {
        if (_currentVolumePosition - 1 in _volumeList.indices) {
            _currentVolumePosition -= 1
            loadVolumePages()
        }
    }


}



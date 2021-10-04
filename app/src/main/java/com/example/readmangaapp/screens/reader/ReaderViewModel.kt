package com.example.readmangaapp.screens.reader

import androidx.lifecycle.ViewModel
import com.example.readmangaapp.data.VolumeEntity
import com.example.readmangaapp.data.manga.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(private val mangaRepository: MangaRepository): ViewModel() {
    private var _mangaUri = ""
    private var _volumeList = listOf<VolumeEntity>()
    private var _currentVolumePosition = 0

    fun setNavArgs(mangaUri: String, volumeList: List<VolumeEntity>, volumePosition: Int) {
        _mangaUri = mangaUri
        _volumeList = volumeList
        _currentVolumePosition = volumePosition
    }
    fun getMangaUri(): String = _mangaUri
    fun getVolumeList(): List<VolumeEntity> = _volumeList
    fun getCurrentVolumePosition(): Int = _currentVolumePosition


}
package com.example.readmangaapp.screens.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.data.MangaEntity
import com.example.readmangaapp.data.manga.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(private val mangaRepository: MangaRepository): ViewModel() {

    private val _mangaList: MutableLiveData<MutableList<MangaEntity>> = MutableLiveData(mutableListOf())
    val mangaList: LiveData<MutableList<MangaEntity>> = _mangaList


    private var offset: Int = 0

    init {
        updateCatalogList()
    }

    fun updateCatalogList() {
        offset += 70
        viewModelScope.launch(Dispatchers.Default) {
            val list = mangaRepository.getCatalogList(offset)
            _mangaList.postValue((_mangaList.value?.plus(list)) as MutableList<MangaEntity>?)
        }

    }

    fun goFirstPage() { offset = 0 }

}
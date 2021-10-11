package com.example.readmangaapp.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.data.manga.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor (private val mangaRepository: MangaRepository): ViewModel() {

    private val _mangaList: MutableLiveData<MutableList<MangaEntity>> = MutableLiveData(mutableListOf())
    val mangaList: LiveData<MutableList<MangaEntity>> = _mangaList

    private var offset: Int = 0

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _mangaList.postValue(mutableListOf())
            val list = mangaRepository.getSearchResponseList(offset, query)
            _mangaList.postValue((_mangaList.value?.plus(list)) as MutableList<MangaEntity>?)
        }
    }

    fun goNextPage() { offset += 70 }
    fun goFirstPage() { offset = 0 }
}
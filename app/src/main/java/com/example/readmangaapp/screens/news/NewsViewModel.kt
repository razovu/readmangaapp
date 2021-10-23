package com.example.readmangaapp.screens.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.entity.NewsEntity
import com.example.readmangaapp.data.news.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel(){

    private val _newsList: MutableLiveData<MutableList<NewsEntity>> = MutableLiveData(mutableListOf())
    val newsList: LiveData<MutableList<NewsEntity>> = _newsList

    private var offset: Int = -10

    init {
        updateCatalogList()
    }

    fun updateCatalogList() {
        offset += 10
        viewModelScope.launch(Dispatchers.Default) {
            val list = repository.getNewsList(offset)
            _newsList.postValue((_newsList.value?.plus(list)) as MutableList<NewsEntity>?)
        }

    }

    fun goFirstPage() { offset = 0 }
}
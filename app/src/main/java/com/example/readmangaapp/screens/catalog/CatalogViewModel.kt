package com.example.readmangaapp.screens.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.data.Manga
import com.example.readmangaapp.domain.network.SiteContentParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(): ViewModel() {

    private val _manga: MutableLiveData<MutableList<Manga>> = MutableLiveData(mutableListOf())
    val manga: LiveData<MutableList<Manga>> = _manga

    private var offSet: Int = -70

    init {
        updateCatalogList()
    }


    fun updateCatalogList() {
        offSet += 70
        viewModelScope.launch(Dispatchers.Default) {
            val list = SiteContentParser().loadMangaList(offSet)
            _manga.postValue((_manga.value?.plus(list)) as MutableList<Manga>?)
        }

    }
}
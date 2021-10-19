package com.example.readmangaapp.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmangaapp.data.profile.ProfileRepository
import com.example.readmangaapp.entity.MangaEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository): ViewModel() {

    private val _favoriteList: MutableLiveData<MutableList<MangaEntity>> = MutableLiveData(mutableListOf())
    val favoriteList: LiveData<MutableList<MangaEntity>> = _favoriteList

    private val _historyList: MutableLiveData<MutableList<MangaEntity>> = MutableLiveData(mutableListOf())
    val historyList: LiveData<MutableList<MangaEntity>> = _historyList


    fun updateFavoritesList() {
        viewModelScope.launch(Dispatchers.Default) {
            val favList = profileRepository.getFavorites()
            _favoriteList.postValue(favList as MutableList<MangaEntity>)
        }

    }

    fun updateHistoryList() {
        viewModelScope.launch(Dispatchers.Default) {
            val historyList = profileRepository.getHistory()
            _historyList.postValue(historyList as MutableList<MangaEntity>)
        }

    }

    fun favBtnClickListener(mangaUrl: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (profileRepository.getByMangaUrl(mangaUrl).favorite){
                profileRepository.removeFromFavorites(mangaUrl)
            } else {
                profileRepository.addToFavorites(mangaUrl)
            }

            updateFavoritesList()
            updateHistoryList()
        }


    }



}
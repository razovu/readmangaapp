package com.example.readmangaapp.presentation.screens.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.readmangaapp.R

class SearchFragment : Fragment(R.layout.fragment_search){

    private val searchViewModel = SearchViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
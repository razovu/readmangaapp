package com.example.readmangaapp.screens.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.readmangaapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search){

    private val searchViewModel = SearchViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
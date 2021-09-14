package com.example.readmangaapp.presentation.screens.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.readmangaapp.R

class CatalogFragment : Fragment(R.layout.fragment_catalog){

    private val catalogViewModel = CatalogViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
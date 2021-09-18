package com.example.readmangaapp.screens.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.MainActivity
import com.example.readmangaapp.R
import com.example.readmangaapp.data.Manga
import com.example.readmangaapp.common.CatalogListRVAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog){

    private val catalogViewModel by viewModels<CatalogViewModel>()
    private val adapter = CatalogListRVAdapter()
    private lateinit var catalogRecyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catalogRecyclerView = view.findViewById(R.id.catalog_recycler_view)

        initRecyclerView()
    }

    // RecyclerView
    private fun initRecyclerView() {
        //Определение ширины экрана и подсчета колонн
        val metrics = resources.displayMetrics
        val spanCount = (metrics.widthPixels / (115 * metrics.scaledDensity)).toInt()

        catalogRecyclerView.adapter = adapter
        catalogRecyclerView.layoutManager = GridLayoutManager(activity, spanCount)

        catalogViewModel.manga.observe(viewLifecycleOwner, {
//            it.let { adapter.set(it) }
            adapter.set(it)
        })
    }

}
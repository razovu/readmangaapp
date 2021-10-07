package com.example.readmangaapp.screens.catalog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.R
import com.example.readmangaapp.utils.addOnScrolledToEnd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val catalogViewModel by viewModels<CatalogViewModel>()
    private val adapter = CatalogListRVAdapter()

    private lateinit var catalogRecyclerView: RecyclerView
    private lateinit var searchBtn: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Views init
        catalogRecyclerView = view.findViewById(R.id.catalog_recycler_view)
        searchBtn = view.findViewById(R.id.catalog_search_btn)

        initRecyclerView()
        initSearchBtn()
    }

    // RecyclerView
    private fun initRecyclerView() {
        //Определение ширины экрана и подсчета колонн
        val metrics = resources.displayMetrics
        val spanCount = (metrics.widthPixels / (115 * metrics.scaledDensity)).toInt()

        catalogRecyclerView.adapter = adapter
        catalogRecyclerView.layoutManager = GridLayoutManager(activity, spanCount)
        catalogRecyclerView.addOnScrolledToEnd { catalogViewModel.updateCatalogList() }

        catalogViewModel.goFirstPage()
        catalogViewModel.mangaList.observe(viewLifecycleOwner, { adapter.set(it) })

    }

    private fun initSearchBtn() {
        searchBtn.setOnClickListener { it.findNavController().navigate(R.id.action_catalogFragment_to_searchFragment) }
    }

}
package com.example.readmangaapp.screens.catalog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.R
import com.example.readmangaapp.common.KEY_MANGA_URL
import com.example.readmangaapp.utils.OnClickItemRecycler
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

        //Назначаем адаптер + лейаутменеджер + листенер для бесконечной прокрутки
        catalogRecyclerView.adapter = adapter
        catalogRecyclerView.layoutManager = GridLayoutManager(activity, spanCount)
        catalogRecyclerView.addOnScrolledToEnd { catalogViewModel.updateCatalogList() }

        //Клик по итему в ресайклере -≥ переход в деталку
        adapter.attachItemClickCallback(object : OnClickItemRecycler {

            override fun onClickFavBtn(mangaUrl: String) {}
            override fun onItemClick(mangaUrl: String) {
                Navigation
                    .findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(R.id.descriptionFragment, bundleOf(KEY_MANGA_URL to mangaUrl))
            }

        })

        catalogViewModel.goFirstPage()
        catalogViewModel.mangaList.observe(viewLifecycleOwner, { adapter.set(it) })

    }

    //переход в экран поиска
    private fun initSearchBtn() {
        searchBtn.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.fragment_container_main)
                .navigate(R.id.searchFragment)
        }
    }


}
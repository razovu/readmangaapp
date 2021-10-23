package com.example.readmangaapp.screens.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.readmangaapp.R
import dagger.hilt.android.AndroidEntryPoint
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.common.KEY_MANGA_URL
import com.example.readmangaapp.screens.catalog.CatalogListRVAdapter
import com.example.readmangaapp.utils.OnClickItemRecycler


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val adapter = SearchListRVAdapter()
    private val searchViewModel by viewModels<SearchViewModel>()

    private lateinit var catalogRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var backBtn: ImageButton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.search_view)
        catalogRecyclerView = view.findViewById(R.id.catalog_search_recycler_view)
        backBtn = view.findViewById(R.id.search_back_btn)

        backBtn.setOnClickListener { it.findNavController().navigateUp() }

        initRecyclerView()
        initSearchAction()

    }

    // RecyclerView
    private fun initRecyclerView() {
        //Определение ширины экрана и подсчета колонн
        val metrics = resources.displayMetrics
        val spanCount = (metrics.widthPixels / (115 * metrics.scaledDensity)).toInt()

        catalogRecyclerView.adapter = adapter
        catalogRecyclerView.layoutManager = GridLayoutManager(activity, spanCount)
        adapter.attachItemClickCallback(object : OnClickItemRecycler {
            override fun onClickFavBtn(mangaUrl: String) {}

            override fun onItemClick(mangaUrl: String) {
                Navigation
                    .findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(R.id.descriptionFragment, bundleOf(KEY_MANGA_URL to mangaUrl))
            }

        })

        searchViewModel.mangaList.observe(viewLifecycleOwner, { adapter.set(it) })

    }

    //Search
    private fun initSearchAction() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean = false
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.goFirstPage()
                searchViewModel.search(query = query ?: "")
                return false
            }
        })
        searchView.requestFocus()
        searchView.showKeyboard()

    }

    //Show keyboard when fragment started
    private fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

}
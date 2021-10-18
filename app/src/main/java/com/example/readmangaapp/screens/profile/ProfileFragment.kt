package com.example.readmangaapp.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.R
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel by viewModels<ProfileViewModel>()
    private val favoritesAdapter = ProfileFavoritesAdapter()
    private val historyAdapter = ProfileHistoryAdapter()

    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.profile_rv)
        tabLayout = view.findViewById(R.id.tab_layout)
        initRecyclerView()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            private val isHistoryTab = 1
            private val isFavoritesTab = 0
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    isHistoryTab -> setHistoryContent()
                    isFavoritesTab -> setFavoritesContent()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        setFavoritesContent()

    }



    private fun setHistoryContent() {
        profileViewModel.updateHistoryList()
        recyclerView.adapter = historyAdapter
        profileViewModel.historyList.observe(viewLifecycleOwner, { historyAdapter.set(it) })
    }

    private fun setFavoritesContent() {
        profileViewModel.updateFavoritesList()
        recyclerView.adapter = favoritesAdapter
        favoritesAdapter.attachFavBtnCallback(object : OnClickFavBtn {
            override fun onClick(mangaUrl: String) {
                profileViewModel.favBtnClickListener(mangaUrl)
            }
        })
        profileViewModel.favoriteList.observe(viewLifecycleOwner, { favoritesAdapter.set(it) })
    }

    // RecyclerView
    private fun initRecyclerView() {
        //Определение ширины экрана и подсчета колонн
        val metrics = resources.displayMetrics
        val spanCount = (metrics.widthPixels / (115 * metrics.scaledDensity)).toInt()

        recyclerView.adapter = favoritesAdapter
        recyclerView.layoutManager = GridLayoutManager(activity, spanCount)

    }
}
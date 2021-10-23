package com.example.readmangaapp.screens.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.R
import com.example.readmangaapp.common.KEY_MANGA_URL
import com.example.readmangaapp.utils.OnClickItemRecycler
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

        //Таб листенер
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            private val isHistoryTab = 1
            private val isFavoritesTab = 0
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    isHistoryTab -> setHistoryContent()
                    isFavoritesTab -> setFavoritesContent()
                }
            }
        })
        //По дефолту выбираем таб "избранное"
        setFavoritesContent()

    }


    //Сначала говорим вьюмодели, чтобы обновила лист, инитим ресайклер, ставим слушатели
    //Избранное удаляет/добвляет в бд
    //Итемклик отправляет нас в деталку(descriptionFragment)
    private fun setHistoryContent() {
        profileViewModel.updateHistoryList()
        initHistoryRV()
        historyAdapter.attachItemClickCallback(object : OnClickItemRecycler {
            override fun onClickFavBtn(mangaUrl: String) {
                profileViewModel.favBtnClickListener(mangaUrl)
            }
            override fun onItemClick(mangaUrl: String) {
                Navigation
                    .findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(R.id.descriptionFragment, bundleOf(KEY_MANGA_URL to mangaUrl))
            }
        })
        profileViewModel.historyList.observe(viewLifecycleOwner, { historyAdapter.set(it) })
    }

    private fun setFavoritesContent() {
        profileViewModel.updateFavoritesList()
        initFavoritesRV()
        favoritesAdapter.attachItemClickCallback(object : OnClickItemRecycler {
            override fun onClickFavBtn(mangaUrl: String) {
                profileViewModel.favBtnClickListener(mangaUrl)
            }
            override fun onItemClick(mangaUrl: String) {
                Navigation
                    .findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(R.id.descriptionFragment, bundleOf(KEY_MANGA_URL to mangaUrl))
            }
        })
        profileViewModel.favoriteList.observe(viewLifecycleOwner, { favoritesAdapter.set(it) })
    }

    // Favorites RecyclerView
    private fun initFavoritesRV() {
        //Определение ширины экрана и подсчета колонн
        val metrics = resources.displayMetrics
        val spanCount = (metrics.widthPixels / (115 * metrics.scaledDensity)).toInt()

        recyclerView.adapter = favoritesAdapter
        recyclerView.layoutManager = GridLayoutManager(activity, spanCount)

    }

    // History RecyclerView
    private fun initHistoryRV() {
        recyclerView.adapter = historyAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
}
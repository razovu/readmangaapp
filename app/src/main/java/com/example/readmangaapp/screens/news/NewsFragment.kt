package com.example.readmangaapp.screens.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.R
import com.example.readmangaapp.utils.addOnScrolledToEnd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment: Fragment(R.layout.fragment_news) {

    private val viewModel by viewModels<NewsViewModel>()
    private val adapter = NewsListRVAdapter()

    private lateinit var newsRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsRecyclerView = view.findViewById(R.id.news_rv)
        newsRecyclerView.adapter = adapter
        newsRecyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.newsList.observe(viewLifecycleOwner, { adapter.set(it) })
    }
}
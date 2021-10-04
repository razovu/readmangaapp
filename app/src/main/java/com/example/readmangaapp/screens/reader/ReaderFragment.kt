package com.example.readmangaapp.screens.reader

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.example.readmangaapp.R
import com.example.readmangaapp.screens.description.DescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReaderFragment : Fragment(R.layout.fragment_reader){

    //ViewModel & Adapter
    private val viewModel by viewModels<ReaderViewModel>()

    //Args
    private lateinit var mangaLink: String

    //Views
    private lateinit var readerViewPager: ViewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mangaLink = arguments?.getString("mangaLink") ?: ""
            readerViewPager = view.findViewById(R.id.reader_view_pager)
        view.findViewById<TextView>(R.id.tettete).text = mangaLink
    }
}
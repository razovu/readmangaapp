package com.example.readmangaapp.screens.description

import android.os.Bundle
import android.view.RoundedCorner
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import com.example.readmangaapp.R
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionFragment : Fragment(R.layout.fragment_description){

    private val descViewModel by viewModels<DescriptionViewModel>()
    private val adapter = DescriptionViewPager2Adapter()

    private lateinit var titleName: TextView
    private lateinit var titleInfo: TextView
    private lateinit var titleDescription: TextView
    private lateinit var favoriteBtn: ImageButton
    private lateinit var readBtn: Button
    private lateinit var showVolumeListBtn: Button
    private lateinit var pager: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        descViewModel.getMangaLink(arguments?.getString("url"))

        titleName = view.findViewById(R.id.desc_title_name)
        titleInfo = view.findViewById(R.id.desc_title_info)
        titleDescription = view.findViewById(R.id.desc_title_description)
        favoriteBtn = view.findViewById(R.id.desc_favorite_btn)
        readBtn = view.findViewById(R.id.desc_read_btn)
        showVolumeListBtn = view.findViewById(R.id.desc_show_volume_list)
        pager = view.findViewById(R.id.desc_title_view_pager)
        dotsIndicator = view.findViewById(R.id.pager_dots_indicator)

        pager.adapter = adapter
        dotsIndicator.setViewPager2(pager)

        bindViews()
    }

    private fun bindViews() {
        //Оставлю тут блюр эффект от койла
        //{ transformations(BlurTransformation(requireContext(),20f)) }

        descViewModel.mangaEntity.observe(viewLifecycleOwner, {
            adapter.set(it.descriptionImages)
            titleName.text = it.name
            titleInfo.text = it.info
            titleDescription.text = it.description
        })
    }

}
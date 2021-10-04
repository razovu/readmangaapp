package com.example.readmangaapp.screens.description

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.readmangaapp.R
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionFragment : Fragment(R.layout.fragment_description){

    //ViewModels & Adapters
    private val descViewModel by viewModels<DescriptionViewModel>()
    private val titleImgViewPager2Adapter = DescriptionViewPager2Adapter()

    //Args
    private lateinit var mangaLink: String

    //Views
    private lateinit var fab: Button
    private lateinit var titleName: TextView
    private lateinit var titleInfo: TextView
    private lateinit var titleDescription: TextView
    private lateinit var favoriteBtn: ImageButton
    private lateinit var titleImgPager: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mangaLink = arguments?.getString("url") ?: ""
        descViewModel.getMangaLink(mangaLink = mangaLink)

        fab = view.findViewById(R.id.fab_read)
        titleName = view.findViewById(R.id.desc_title_name)
        titleInfo = view.findViewById(R.id.desc_title_info)
        titleDescription = view.findViewById(R.id.desc_title_description)
        favoriteBtn = view.findViewById(R.id.desc_favorite_btn)
        titleImgPager = view.findViewById(R.id.desc_title_view_pager)
        dotsIndicator = view.findViewById(R.id.pager_dots_indicator)

        titleImgPager.adapter = titleImgViewPager2Adapter
        dotsIndicator.setViewPager2(titleImgPager)

        setContent()
    }

    private fun setContent() {

        descViewModel.mangaEntity.observe(viewLifecycleOwner, {
            titleImgViewPager2Adapter.set(it.descriptionImages)
            titleName.text = it.name
            titleInfo.text = it.info
            titleDescription.text = it.description

        })

        fab.setOnClickListener {
            descViewModel.mangaVolumeList.observe(viewLifecycleOwner, {
                val volList = descViewModel.getVolumeNamesArray()
                if (volList.isNullOrEmpty()){
                    fab.setText(R.string.without_volumes)
                } else {
                    setDialogVolumeList(volList)
                }

            })
        }

    }

    private fun setDialogVolumeList(volList: Array<String>) {
        val itemClickListener = { dialog: DialogInterface, which: Int ->
            val bundle = bundleOf(
                "mangaLink" to mangaLink,
                "whichVolume" to which,
                "volumeList" to descViewModel.mangaVolumeList.value
            )
            findNavController().navigate(R.id.readerFragment, bundle)
        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.volume_list)
        builder.setItems(volList, itemClickListener)
        builder.setNeutralButton(R.string.dialog_cancel) {_, _ -> }
        builder.setPositiveButton(R.string.dialog_read_first_volume) {_, _ -> }
        builder.show()


    }


}
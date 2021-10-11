package com.example.readmangaapp.screens.reader

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.readmangaapp.R
import com.example.readmangaapp.common.KEY_MANGA_URL
import com.example.readmangaapp.common.KEY_VOLUME_LIST
import com.example.readmangaapp.common.KEY_VOLUME_SELECTED
import com.example.readmangaapp.entity.VolumeEntity
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReaderFragment : Fragment(R.layout.fragment_reader){

    //ViewModel & Adapter
    private val viewModel by viewModels<ReaderViewModel>()
    private lateinit var readerAdapter: ReaderViewPager2Adapter


    //Views
    private lateinit var readerViewPager: ViewPager2
    private lateinit var btnPreviousVolume: Button
    private lateinit var fullscreenBtn: ImageButton
    private lateinit var btnNextVolume: Button
    private lateinit var pageCounter: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var navContainer: FrameLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullscreenBtn = view.findViewById(R.id.fullscreen_btn)
        navContainer = view.findViewById(R.id.volume_navigation_container)
        toolbar = view.findViewById(R.id.reader_toolbar)
        btnPreviousVolume = view.findViewById(R.id.btnPrevVol)
        btnNextVolume = view.findViewById(R.id.btnNextVol)
        pageCounter = view.findViewById(R.id.pageCounter)
        readerViewPager = view.findViewById(R.id.reader_view_pager)
        readerAdapter = ReaderViewPager2Adapter()

        //BigImageView
        BigImageViewer.initialize(GlideImageLoader.with(requireActivity().applicationContext))

        //Кнопка назад
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        //Мы уверены в том, что аргументы навигации non nullable. Иначе мы не попали бы на этот экран
        viewModel.setNavArgs(
            mangaUri = arguments?.getString(KEY_MANGA_URL)!!,
            volumeList = arguments?.getParcelableArray(KEY_VOLUME_LIST)!!.map { it as VolumeEntity },
            volumePosition = arguments?.getInt(KEY_VOLUME_SELECTED)!!
        )

        //Показать/скрыть элементы интерфейса
        fullscreenBtn.setOnClickListener {
            if(toolbar.visibility == View.VISIBLE){
                requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
                toolbar.visibility = View.GONE
                navContainer.visibility = View.GONE
            } else {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                toolbar.visibility = View.VISIBLE
                navContainer.visibility = View.VISIBLE
            }
        }


        //Назначаем адаптер и подписываемся на изменения вьюмодели
        readerViewPager.adapter = readerAdapter
        viewModel.loadVolumePages()
        viewModel.volumePages.observe(viewLifecycleOwner, {
            setContent(imageList = it)
        })

        //Загрузить следующую главу
        btnNextVolume.setOnClickListener {
            viewModel.goToNextVolume()
        }

        //Загрузить предыдущую главу
        btnPreviousVolume.setOnClickListener {
            viewModel.goToPreviousVolume()
        }
    }


    private fun setContent(imageList: List<String>) {

        //Текст заготовка для textView
        var counterText = "1 из ${imageList.size}"

        //биндим вьюхи
        toolbar.title = viewModel.getCurrentVolumeName()
        pageCounter.text = counterText
        btnNextVolume.text = viewModel.getNextVolumeName()
        btnPreviousVolume.text = viewModel.getPreviousVolumeName()

        //Сеттим вьюпейджер
        readerAdapter.set(imageList)
        readerViewPager.offscreenPageLimit = 2
        readerViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPx: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(pos: Int) {
                counterText = "${pos + 1} из ${imageList.size}"
                pageCounter.text = counterText
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Выходим из фуллскрин мода
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
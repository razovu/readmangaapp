package com.example.readmangaapp.screens.description

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.readmangaapp.R
import com.example.readmangaapp.common.*
import com.example.readmangaapp.entity.VolumeEntity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionFragment : Fragment(R.layout.fragment_description) {

    //ViewModels & Adapters
    private val descViewModel by viewModels<DescriptionViewModel>()
    private val titleImgViewPager2Adapter = DescriptionViewPager2Adapter()
    private val volumesRVAdapter = VolumesRVAdapter()

    //Args
    private lateinit var mangaUrl: String

    //Views
    private lateinit var recyclerView: RecyclerView
    private lateinit var volumeListTextView: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var fab: Button
    private lateinit var titleName: TextView
    private lateinit var titleInfo: TextView
    private lateinit var titleDescription: TextView
    private lateinit var favoriteBtn: ImageButton
    private lateinit var titleImgPager: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator
    private lateinit var progressBar: ContentLoadingProgressBar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Отправляем аргументы навигации во вьюмодель
        descViewModel.setMangaUrl(mangaUrl = arguments?.getString(KEY_MANGA_URL))
        mangaUrl = descViewModel.getMangaUri()

        //Views init
        recyclerView = view.findViewById(R.id.desc_rv_volumes)
        btnBack = view.findViewById(R.id.desc_navigate_back)
        fab = view.findViewById(R.id.fab_read)
        titleName = view.findViewById(R.id.desc_title_name)
        titleInfo = view.findViewById(R.id.desc_title_info)
        titleDescription = view.findViewById(R.id.desc_title_description)
        volumeListTextView = view.findViewById(R.id.desc_volumes_header)
        favoriteBtn = view.findViewById(R.id.desc_favorite_btn)
        titleImgPager = view.findViewById(R.id.desc_title_view_pager)
        dotsIndicator = view.findViewById(R.id.pager_dots_indicator)
        progressBar = view.findViewById(R.id.desc_progress_bar)

        //Кнопка назад
        btnBack.setOnClickListener { activity?.onBackPressed() }

        //Адаптер + привязываем индикатор к нему
        titleImgPager.adapter = titleImgViewPager2Adapter
        dotsIndicator.setViewPager2(titleImgPager)


        setContent()
    }

    private fun setContent() {

        progressBar.show()
        //Назначаем адаптер + лейаутменеджер
        recyclerView.adapter = volumesRVAdapter
        val llm = LinearLayoutManager(requireContext())
        llm.stackFromEnd = true
        llm.reverseLayout = true
        recyclerView.layoutManager = llm

        //Подписываемся на изменения mangaEntity и наполняем вьюхи
        descViewModel.mangaEntity.observe(viewLifecycleOwner, {
            titleImgViewPager2Adapter.set(it.descriptionImages)
            titleName.text = it.name
            titleInfo.text = it.info
            titleDescription.text = it.description
            volumesRVAdapter.setMangaEntity(it)
            progressBar.hide()
        })

        //Ресайклер
        descViewModel.mangaVolumeList.observe(viewLifecycleOwner, { list -> configureRecyclerViewAndFabBtn(list) })

        //кнопка "избранное"
        descViewModel.isFavorite.observe(viewLifecycleOwner, {isFav -> configureFavoriteBtn(isFav) })

    }

    private fun configureFavoriteBtn(isFav: Boolean) {
        //Если в избранном - то соответствующая иконка
        if (isFav) {
            favoriteBtn.load(R.drawable.ic_favorite_filled)
        } else {
            favoriteBtn.load(R.drawable.ic_favorite)
        }

        //По нажатию добавляем/удаляем из избранных и меняем иконку
        favoriteBtn.setOnClickListener {
            if (isFav) {
                descViewModel.removeFromFavorites()
                favoriteBtn.load(R.drawable.ic_favorite)
            } else {
                descViewModel.addToFavorite()
                favoriteBtn.load(R.drawable.ic_favorite_filled)
            }
        }
    }

    // RecyclerView
    private fun initRecyclerView() {

        //Назначаем адаптер + лейаутменеджер
        recyclerView.adapter = volumesRVAdapter
        val llm = LinearLayoutManager(requireContext())
        llm.stackFromEnd = true
        llm.reverseLayout = true
        recyclerView.layoutManager = llm

        descViewModel.mangaVolumeList.observe(viewLifecycleOwner, { list ->
            configureRecyclerViewAndFabBtn(list)
        })

    }

    /** Здесь конфигурирую ресайклер и кнопку "Читать"(она же fab)
     Бывает такое, что у манги нет глав в соответствии с авторскими правами.
     Соответственно, если глав нет, то убираем кнопку читать, пишем что глав нет :(
     и ничего не передаем ресайклеру. В обратном случае плывем по течению */

    private fun configureRecyclerViewAndFabBtn(list: List<VolumeEntity>) {

        fun navigateToReader(bundle: Bundle) {
            Navigation
                .findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.readerFragment, bundle)
        }

        //Если глав нет, то меняем текст кнопкпи. Иначе выполняем стандартную работу
        if (list.isEmpty()) {
            fab.visibility = View.GONE
            volumeListTextView.setText(R.string.without_volumes)

        } else {
            fab.visibility = View.VISIBLE
            volumeListTextView.setText(R.string.read_first_volume)

            //Сеттим в адаптер спиок глав
            volumesRVAdapter.set(list.toMutableList())

            //Ставим слушатель на фаб
            fab.setOnClickListener {
                navigateToReader(bundleOf(
                    KEY_MANGA_URL to mangaUrl,
                    KEY_VOLUME_SELECTED to FIRST_VOLUME,
                    KEY_VOLUME_LIST to list.toTypedArray()
                ))
            }

            //Клик по итему в ресайклере -> переход в reader
            volumesRVAdapter.attachItemClickCallback(object : OnVolumeClick {
                override fun onItemClick(position: Int) {
                    navigateToReader(bundleOf(
                        KEY_MANGA_URL to mangaUrl,
                        KEY_VOLUME_SELECTED to position,
                        KEY_VOLUME_LIST to list.toTypedArray()
                    ))
                }
            })

        }
    }

}
package com.example.readmangaapp.screens.description

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.readmangaapp.R
import com.example.readmangaapp.common.*
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class DescriptionFragment : Fragment(R.layout.fragment_description) {

    //ViewModels & Adapters
    private val descViewModel by viewModels<DescriptionViewModel>()
    private val titleImgViewPager2Adapter = DescriptionViewPager2Adapter()

    //Args
    private lateinit var mangaUrl: String

    //Views
    private lateinit var btnBack: ImageButton
    private lateinit var fab: Button
    private lateinit var titleName: TextView
    private lateinit var titleInfo: TextView
    private lateinit var titleDescription: TextView
    private lateinit var favoriteBtn: ImageButton
    private lateinit var titleImgPager: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Отправляем аргументы навигации во вьюмодель
        descViewModel.setMangaUrl(mangaUrl = arguments?.getString(KEY_MANGA_URL))
        mangaUrl = descViewModel.getMangaUri()

        //Views init
        btnBack = view.findViewById(R.id.desc_navigate_back)
        fab = view.findViewById(R.id.fab_read)
        titleName = view.findViewById(R.id.desc_title_name)
        titleInfo = view.findViewById(R.id.desc_title_info)
        titleDescription = view.findViewById(R.id.desc_title_description)
        favoriteBtn = view.findViewById(R.id.desc_favorite_btn)
        titleImgPager = view.findViewById(R.id.desc_title_view_pager)
        dotsIndicator = view.findViewById(R.id.pager_dots_indicator)

        btnBack.setOnClickListener { activity?.onBackPressed() }

        //Адаптер + привязываем индикатор к нему
        titleImgPager.adapter = titleImgViewPager2Adapter
        dotsIndicator.setViewPager2(titleImgPager)

        setContent()
    }

    private fun setContent() {

        //Подписываемся на изменения mangaEntity и наполняем вьюхи
        descViewModel.mangaEntity.observe(viewLifecycleOwner, {
            titleImgViewPager2Adapter.set(it.descriptionImages)
            titleName.text = it.name
            titleInfo.text = it.info
            titleDescription.text = it.description
        })

        //кнопка "избранное"
        descViewModel.isFavorite.observe(viewLifecycleOwner, {isFav ->
            Log.e("isfav", isFav.toString())
            if (isFav) {
                favoriteBtn.load(R.drawable.ic_favorite_filled)
            } else {
                favoriteBtn.load(R.drawable.ic_favorite)
            }

            favoriteBtn.setOnClickListener {
                if (isFav) {
                    descViewModel.removeFromFavorites()
                    favoriteBtn.load(R.drawable.ic_favorite)
                } else {
                    descViewModel.addToFavorite()
                    favoriteBtn.load(R.drawable.ic_favorite_filled)
                }
            }
        })




    /** Кнопка "Читать"
        Бывает такое, что у манги нет глав в соответствии с авторскими правами.
        Так что делаем проверку на их наличие. Если глав нет, то перехода на следующий экран
        не будет. в Связи с этим мы будем уверены, что аргументы навигации не будут пустыми или null */
        fab.setOnClickListener {
            descViewModel.mangaVolumeList.observe(viewLifecycleOwner, {
                val volumeNameList = descViewModel.getVolumeNamesArray()

                if (volumeNameList.isNullOrEmpty()) {
                    fab.setText(R.string.without_volumes)
                } else {
                    setDialogVolumeList(volumeNameList)
                }
            })
        }

    }

    /**  Диалоговое окно с выбором главы
        Напоминаю, что это окно не появится если глав нет, т.е. если mangaVolumeList пуст или null
        При клике по итему (коим является какая-либо глава) кладем в аргументы навигации
        /урл манги/ + /выбранная глава/ + /список всех глав/  и переходим на след экран
        Выбранная глава очень важная деталь. Тк для удобства пользователя список перевернутый
        Значит и позицию в ней должны учитывать. чтобы volumeList.first() была именно первая глава */
    private fun setDialogVolumeList(volumeNameList: Array<String>) {

        val volList = descViewModel.mangaVolumeList.value!!.toTypedArray()

        val itemClickListener = { _: DialogInterface, position: Int ->
            val bundle = bundleOf(
                KEY_MANGA_URL to mangaUrl,
                KEY_VOLUME_SELECTED to abs(position - volList.size + 1),
                KEY_VOLUME_LIST to volList
            )
            Navigation
                .findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.readerFragment, bundle)
        }

        val readFirstVolumeBtnClickListener = { _: DialogInterface, _: Int ->
            val bundle = bundleOf(
                KEY_MANGA_URL to mangaUrl,
                KEY_VOLUME_SELECTED to FIRST_VOLUME,
                KEY_VOLUME_LIST to volList
            )

            Navigation
                .findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.readerFragment, bundle)
        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.volume_list)
        builder.setItems(volumeNameList, itemClickListener)
        builder.setNeutralButton(R.string.dialog_cancel) { _, _ -> }
        builder.setPositiveButton(R.string.dialog_read_first_volume, readFirstVolumeBtnClickListener)
        builder.show()


    }



}
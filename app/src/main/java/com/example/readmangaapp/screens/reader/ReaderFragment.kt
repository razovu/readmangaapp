package com.example.readmangaapp.screens.reader

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.readmangaapp.R
import com.example.readmangaapp.common.KEY_MANGA_URL
import com.example.readmangaapp.common.KEY_VOLUME_LIST
import com.example.readmangaapp.common.KEY_VOLUME_SELECTED
import com.example.readmangaapp.data.VolumeEntity
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReaderFragment : Fragment(R.layout.fragment_reader){

    /** Варианты читалки
     *
     * [1] внедрить переключатели после последней/до первой страницы. Но например если юзер не хочет
     * читать всякие послесловия и т.п. ему придется их промотать, что не удобно.
     *
     * [2] добавить переключатели и перемещаться между главами с их помощью
     *
     * [3] когда юзер на предпоследней сранице, начать загружать странички след главы
     *     учитывая, есть ли он вообще. Получится бесшовное чтение. Но как тут быть
     *     с предыдущей главой + неудобства 1го варианта
     *
     * Проще всего и даже логичнее использовать 2й вариант
     * */

    //ViewModel & Adapter
    private val viewModel by viewModels<ReaderViewModel>()
    private lateinit var readerAdapter: ReaderViewPagerAdapter


    //Views
    private lateinit var readerViewPager: ViewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        //BigImageView
        BigImageViewer.initialize(GlideImageLoader.with(requireContext()))

        //Мы уверены в том, что аргументы навигации non nullable. Иначе мы не попали бы на этот экран
        viewModel.setNavArgs(
            mangaUri = arguments?.getString(KEY_MANGA_URL)!!,
            volumeList = arguments?.getParcelableArray(KEY_VOLUME_LIST)!!.map { it as VolumeEntity },
            volumePosition = arguments?.getInt(KEY_VOLUME_SELECTED)!!
        )

        readerAdapter = ReaderViewPagerAdapter(requireActivity().applicationContext)
        readerViewPager = view.findViewById(R.id.reader_view_pager)
        readerViewPager.adapter = readerAdapter

        setContent()
    }

    private fun setContent() {
        viewModel.loadVolumePages()
        viewModel.volumePages.observe(viewLifecycleOwner, {
            readerViewPager.offscreenPageLimit = it.size
            readerAdapter.set(it)

        })
    }
}
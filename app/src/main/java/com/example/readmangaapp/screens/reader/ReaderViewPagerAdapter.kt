package com.example.readmangaapp.screens.reader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.PagerAdapter
import com.example.readmangaapp.R
import com.example.readmangaapp.utils.ProgressBIVIndicator
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator
import com.github.piasy.biv.view.BigImageView

class ReaderViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private val imgList = mutableListOf<String>()

    //Предзагрузка страниц


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = imgList.size

    fun set(list: List<String>) {
        this.imgList.clear()
        this.imgList.addAll(list)
        this.notifyDataSetChanged()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        if (position < imgList.size) BigImageViewer.prefetch(Uri.parse(imgList[position]))

        val imgView = BigImageView(context)
        with(imgView) {
            setProgressIndicator(ProgressBIVIndicator())
            setOptimizeDisplay(true)
            setFailureImage(ContextCompat.getDrawable(context, R.drawable.logo_rm))
            setTapToRetry(true)
            showImage(Uri.parse(imgList[position]))
        }

        container.addView(imgView)

        return imgView

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as BigImageView)
    }
}
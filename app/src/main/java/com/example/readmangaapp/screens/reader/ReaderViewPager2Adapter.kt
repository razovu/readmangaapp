package com.example.readmangaapp.screens.reader

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.R
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.BigImageView
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.io.File
import java.lang.Exception
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView





class ReaderViewPager2Adapter() :
    RecyclerView.Adapter<ReaderViewPager2Adapter.ReaderViewHolder>() {

    private val imgList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReaderViewHolder {
        return ReaderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_reader, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReaderViewHolder, position: Int) {
        holder.bind(imgList[position])
    }

    override fun onViewRecycled(holder: ReaderViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    override fun onViewAttachedToWindow(holder: ReaderViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.hasNoImage()) holder.rebind()
    }

    override fun getItemCount(): Int = imgList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: List<String>) {
        this.imgList.clear()
        this.imgList.addAll(list)
        this.notifyDataSetChanged()
    }

    inner class ReaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgUrl = ""
        private val img = itemView.findViewById<BigImageView>(R.id.mBigImage)
        private val loadStatus = itemView.findViewById<TextView>(R.id.biv_indicator_status)
        private val progressIndicator = itemView.findViewById<CircularProgressIndicator>(R.id.biv_progress_indicator)

        fun bind(page: String) {
            imgUrl = page

            with(img) {
                layoutTransition.setAnimateParentHierarchy(false)
                showImage(Uri.parse(page))
                setImageLoaderCallback(object : ImageLoader.Callback {
                    override fun onCacheHit(imageType: Int, image: File?) {}
                    override fun onCacheMiss(imageType: Int, image: File?) {}
                    override fun onSuccess(image: File?) {}
                    override fun onFail(error: Exception?) {}

                    override fun onStart() {
                        progressIndicator.visibility = View.VISIBLE
                        loadStatus.visibility = View.VISIBLE
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onProgress(progress: Int) {
                        if (progress < 0 || progress > 100 || progressIndicator == null) {
                            return
                        }
                        loadStatus.text = "$progress%"
                        progressIndicator.progress = progress
                    }

                    override fun onFinish() {
                        loadStatus.visibility = View.GONE
                        progressIndicator.visibility = View.GONE
                    }
                })
            }
        }

        fun rebind() = bind(imgUrl)

        fun clear() = img.ssiv.recycle()

        fun hasNoImage(): Boolean {
            val ssiv: SubsamplingScaleImageView? = img.ssiv
            return if(ssiv != null) {
                !ssiv.hasImage()
            } else return true
        }



    }
}
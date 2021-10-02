package com.example.readmangaapp.screens.description

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation
import com.example.readmangaapp.R
import com.example.readmangaapp.data.MangaEntity


class DescriptionViewPager2Adapter : RecyclerView.Adapter<DescriptionViewPager2Adapter.CatalogViewHolder>() {

    private val imgList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.description_view_pager_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(imgList[position])
    }

    override fun getItemCount(): Int = imgList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: List<String>) {
        this.imgList.clear()
        this.imgList.addAll(list)
        this.notifyDataSetChanged()
    }

    inner class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(mangaImage: String) {
            itemView.findViewById<ImageView>(R.id.view_pager2_img)
                .load(mangaImage){
                    crossfade(true)
                }
        }

    }
}
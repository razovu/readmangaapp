package com.example.readmangaapp.screens.description

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.readmangaapp.R


class DescriptionViewPager2Adapter : RecyclerView.Adapter<DescriptionViewPager2Adapter.CatalogViewHolder>() {

    private val imgList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_description_view_pager, parent, false)
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
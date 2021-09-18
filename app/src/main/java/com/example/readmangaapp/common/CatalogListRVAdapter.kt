package com.example.readmangaapp.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readmangaapp.R
import com.example.readmangaapp.data.Manga

class CatalogListRVAdapter : RecyclerView.Adapter<CatalogListRVAdapter.CatalogViewHolder>() {

    private val catalogList = mutableListOf<Manga>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(catalogList[position])
    }

    override fun getItemCount(): Int = catalogList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: MutableList<Manga>) {
        this.catalogList.clear()
        this.catalogList.addAll(list)
        this.notifyDataSetChanged()
    }

    inner class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(manga: Manga) {
            Glide.with(itemView.context)
                .load(manga.img)
                .into(itemView.findViewById(R.id.catalog_item_img))
        }

    }
}
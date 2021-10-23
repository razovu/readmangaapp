package com.example.readmangaapp.screens.catalog

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
import com.example.readmangaapp.R
import com.example.readmangaapp.common.KEY_MANGA_ENTITY
import com.example.readmangaapp.common.KEY_MANGA_URL
import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.utils.OnClickItemRecycler


class CatalogListRVAdapter : RecyclerView.Adapter<CatalogListRVAdapter.CatalogViewHolder>() {

    private val catalogList = mutableListOf<MangaEntity>()
    private var clickCallback: OnClickItemRecycler? = null

    fun attachItemClickCallback(callback: OnClickItemRecycler) {
        clickCallback = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false),
        clickCallback)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(catalogList[position])
    }

    override fun getItemCount(): Int = catalogList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: MutableList<MangaEntity>) {
        this.catalogList.clear()
        this.catalogList.addAll(list)
        this.notifyDataSetChanged()
    }

    inner class CatalogViewHolder(itemView: View, private val clickItemRecycler: OnClickItemRecycler?) : RecyclerView.ViewHolder(itemView) {

        fun bind(mangaEntity: MangaEntity) {

            val img = itemView.findViewById<ImageView>(R.id.catalog_item_img)
            val name = itemView.findViewById<TextView>(R.id.catalog_item_name)
            val rate = itemView.findViewById<TextView>(R.id.catalog_item_rate)

            img.load(mangaEntity.img) {crossfade(true)}
            name.text = mangaEntity.name
            rate.text = mangaEntity.rate

            itemView.setOnClickListener {
                if (mangaEntity.url.isNotEmpty()) clickItemRecycler?.onItemClick(mangaEntity.url)
            }

        }

    }
}
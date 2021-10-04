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
import com.example.readmangaapp.common.KEY_MANGA_URL
import com.example.readmangaapp.data.MangaEntity


class CatalogListRVAdapter : RecyclerView.Adapter<CatalogListRVAdapter.CatalogViewHolder>() {

    private val catalogList = mutableListOf<MangaEntity>()

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
    fun set(list: MutableList<MangaEntity>) {
        this.catalogList.clear()
        this.catalogList.addAll(list)
        this.notifyDataSetChanged()
    }




    inner class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(mangaEntity: MangaEntity) {

            val img = itemView.findViewById<ImageView>(R.id.catalog_item_img)
            val name = itemView.findViewById<TextView>(R.id.catalog_item_name)
            val rate = itemView.findViewById<TextView>(R.id.catalog_item_rate)
            val bookmarkBtn = itemView.findViewById<ImageSwitcher>(R.id._catalog_item_fav_switcher)

            img.load(mangaEntity.img)
            name.text = mangaEntity.name
            //в rate получим 12значное число, так что укорачиваем
            rate.text = mangaEntity.rate.substring(0..2)

            itemView.setOnClickListener {
                val bundle: Bundle = bundleOf(KEY_MANGA_URL to mangaEntity.url)
                it.findNavController().navigate(R.id.descriptionFragment, bundle)
            }

            bookmarkBtn.setOnClickListener { bookmarkBtn.showNext() }
        }

    }
}
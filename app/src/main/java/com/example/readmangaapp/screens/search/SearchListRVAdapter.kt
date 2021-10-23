package com.example.readmangaapp.screens.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.readmangaapp.R
import com.example.readmangaapp.common.KEY_MANGA_URL
import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.utils.OnClickItemRecycler


class SearchListRVAdapter : RecyclerView.Adapter<SearchListRVAdapter.SearchViewHolder>() {

    private val catalogList = mutableListOf<MangaEntity>()
    private var clickCallback: OnClickItemRecycler? = null

    fun attachItemClickCallback(callback: OnClickItemRecycler) {
        clickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false),
            clickCallback
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(catalogList[position])
    }

    override fun getItemCount(): Int = catalogList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: MutableList<MangaEntity>) {
        this.catalogList.clear()
        this.catalogList.addAll(list)
        this.notifyDataSetChanged()
    }




    inner class SearchViewHolder(itemView: View, private val clickItemRecycler: OnClickItemRecycler?) : RecyclerView.ViewHolder(itemView) {

        fun bind(mangaEntity: MangaEntity) {

            val img = itemView.findViewById<ImageView>(R.id.catalog_item_img)
            val name = itemView.findViewById<TextView>(R.id.catalog_item_name)
            val rate = itemView.findViewById<TextView>(R.id.catalog_item_rate)

            img.load(mangaEntity.img)
            name.text = mangaEntity.name
            rate.text = mangaEntity.rate

            itemView.setOnClickListener {
                Log.e("pos search adapter", bindingAdapterPosition.toString())
                clickItemRecycler?.onItemClick(mangaEntity.url)}

        }

    }
}
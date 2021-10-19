package com.example.readmangaapp.screens.profile

import android.annotation.SuppressLint
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


class ProfileHistoryAdapter : RecyclerView.Adapter<ProfileHistoryAdapter.ProfileViewHolder>() {

    private val mangaList = mutableListOf<MangaEntity>()
    private var clickCallback: OnClickItemRecycler? = null

    fun attachItemClickCallback(callback: OnClickItemRecycler) {
        clickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile2, parent, false),
            clickCallback
        )
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(mangaList[position])
    }

    override fun getItemCount(): Int = mangaList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: MutableList<MangaEntity>) {
        this.mangaList.clear()
        this.mangaList.addAll(list.reversed())
        this.notifyDataSetChanged()
    }


    inner class ProfileViewHolder(itemView: View,
        private val clickItemRecycler: OnClickItemRecycler?) : RecyclerView.ViewHolder(itemView) {

        fun bind(mangaEntity: MangaEntity) {
            val img = itemView.findViewById<ImageView>(R.id.item_profile_manga_img)
            val name = itemView.findViewById<TextView>(R.id.item_profile_manga_name)
            val date = itemView.findViewById<TextView>(R.id.item_profile_date)
            val rate = itemView.findViewById<TextView>(R.id.item_profile_rate)
            val lastVolumeName = itemView.findViewById<TextView>(R.id.item_profile_last_volume)
            val favBtn = itemView.findViewById<ImageView>(R.id.item_profile_fav_btn)

            img.load(mangaEntity.img)
            name.text = mangaEntity.name
            date.text = mangaEntity.lastReadTime
            rate.text = mangaEntity.rate
            lastVolumeName.text = mangaEntity.lastReadVolumeName
            if (mangaEntity.favorite) {
                favBtn.load(R.drawable.ic_favorite_filled)
            } else {
                favBtn.load(R.drawable.ic_favorite)
            }
            favBtn.setOnClickListener { clickItemRecycler?.onClickFavBtn(mangaEntity.url) }
            itemView.setOnClickListener { clickItemRecycler?.onItemClick(mangaEntity.url) }
        }

    }
}
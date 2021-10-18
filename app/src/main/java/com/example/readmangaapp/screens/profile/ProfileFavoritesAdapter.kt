package com.example.readmangaapp.screens.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.readmangaapp.R
import com.example.readmangaapp.entity.MangaEntity


class ProfileFavoritesAdapter : RecyclerView.Adapter<ProfileFavoritesAdapter.ProfileViewHolder>() {

    private val mangaList = mutableListOf<MangaEntity>()
    private var clickCallback: OnClickFavBtn? = null

    fun attachFavBtnCallback(callback: OnClickFavBtn) {
        clickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        , clickCallback)
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




    inner class ProfileViewHolder(itemView: View, val clickFavBtn: OnClickFavBtn?) : RecyclerView.ViewHolder(itemView) {

        fun bind(mangaEntity: MangaEntity) {
            val img = itemView.findViewById<ImageView>(R.id.profile_item_img)
            val name = itemView.findViewById<TextView>(R.id.profile_item_name)
            val date = itemView.findViewById<TextView>(R.id.profile_item_date)
            val favBtn = itemView.findViewById<ImageView>(R.id._profile_item_fav_btn)

            img.load(mangaEntity.img)
            name.text = mangaEntity.name
            date.text = mangaEntity.rate
            if (mangaEntity.favorite) {
                favBtn.load(R.drawable.ic_favorite_filled)
            } else {
                favBtn.load(R.drawable.ic_favorite)
            }
            favBtn.setOnClickListener { clickFavBtn?.onClick(mangaEntity.url) }

        }

    }
}
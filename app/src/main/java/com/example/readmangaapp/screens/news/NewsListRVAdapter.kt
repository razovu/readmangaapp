package com.example.readmangaapp.screens.news

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.readmangaapp.R
import com.example.readmangaapp.entity.ReadMangaNewsEntity
import androidx.core.content.ContextCompat.startActivity





class NewsListRVAdapter() : RecyclerView.Adapter<NewsListRVAdapter.NewsViewHolder>() {

    private val newsList = mutableListOf<ReadMangaNewsEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: List<ReadMangaNewsEntity>) {
        this.newsList.clear()
        this.newsList.addAll(list)
        this.notifyDataSetChanged()
    }


    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(newsEntity: ReadMangaNewsEntity) {

            val img = itemView.findViewById<ImageView>(R.id.news_post_img)
            val title = itemView.findViewById<TextView>(R.id.news_post_title)
            val date = itemView.findViewById<TextView>(R.id.news_post_date)
            val description = itemView.findViewById<TextView>(R.id.news_post_desc)

            img.load(newsEntity.postImg)
            img.clipToOutline = true
            title.text = newsEntity.postTitle
            date.text = newsEntity.postDate
            description.text = newsEntity.postDescription

//            itemView.setOnClickListener { openWebPage(newsEntity.postUrl) }

        }

//        private fun openWebPage(url: String) {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            val title = "Выберите бразуер"
//            val chooser = Intent.createChooser(intent, title)
//            startActivity(context, chooser, null)
//        }

    }
}
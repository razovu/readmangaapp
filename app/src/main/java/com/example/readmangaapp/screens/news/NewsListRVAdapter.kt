package com.example.readmangaapp.screens.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.readmangaapp.R
import com.example.readmangaapp.entity.NewsEntity
import com.example.readmangaapp.utils.OnClickItemRecycler


class NewsListRVAdapter : RecyclerView.Adapter<NewsListRVAdapter.NewsViewHolder>() {

    private val newsList = mutableListOf<NewsEntity>()
    private var clickCallback: OnClickItemRecycler? = null

    fun attachItemClickCallback(callback: OnClickItemRecycler) {
        clickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false),
            clickCallback
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: List<NewsEntity>) {
        this.newsList.clear()
        this.newsList.addAll(list)
        this.notifyDataSetChanged()
    }


    inner class NewsViewHolder(itemView: View, private val clickItemRecycler: OnClickItemRecycler?) : RecyclerView.ViewHolder(itemView) {

        fun bind(newsEntity: NewsEntity) {

            val img = itemView.findViewById<ImageView>(R.id.news_post_img)
            val title = itemView.findViewById<TextView>(R.id.news_post_title)
            val date = itemView.findViewById<TextView>(R.id.news_post_date)
            val description = itemView.findViewById<TextView>(R.id.news_post_desc)

            img.load(newsEntity.postImg)
            img.clipToOutline = true
            title.text = newsEntity.postTitle
            date.text = newsEntity.postDate
            description.text = newsEntity.postDescription

            itemView.setOnClickListener { clickItemRecycler?.onItemClick(newsEntity.postUrl) }

        }

    }
}
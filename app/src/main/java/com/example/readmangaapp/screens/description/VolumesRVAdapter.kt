package com.example.readmangaapp.screens.description

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readmangaapp.R
import com.example.readmangaapp.entity.MangaEntity
import com.example.readmangaapp.entity.VolumeEntity


class VolumesRVAdapter : RecyclerView.Adapter<VolumesRVAdapter.DescViewHolder>() {

    private val catalogList = mutableListOf<VolumeEntity>()
    private val readVolumeList = mutableListOf<Int>()
    private var clickCallback: OnVolumeClick? = null

    fun attachItemClickCallback(callback: OnVolumeClick) {
        clickCallback = callback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMangaEntity(mangaEntity: MangaEntity) {
        val list = mangaEntity.readVolumesIndices.map { it.toInt() }
        readVolumeList.clear()
        readVolumeList.addAll(list)
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescViewHolder {
        return DescViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_volumes, parent, false),
            clickItemRecycler = clickCallback
        )
    }

    override fun onBindViewHolder(holder: DescViewHolder, position: Int) {
        holder.bind(catalogList[position], position)
    }

    override fun getItemCount(): Int = catalogList.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(list: MutableList<VolumeEntity>) {
        this.catalogList.clear()
        this.catalogList.addAll(list)
        this.notifyDataSetChanged()

    }

    inner class DescViewHolder(
        itemView: View,
        private val clickItemRecycler: OnVolumeClick?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("ResourceAsColor")
        fun bind(volumeEntity: VolumeEntity, position: Int) {
            val volumeName = itemView.findViewById<TextView>(R.id.item_desc_volume_name)
            val volumeNumber = itemView.findViewById<TextView>(R.id.item_desc_vol_number)

            if (position in readVolumeList) volumeName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            volumeName.text = volumeEntity.volName
            volumeNumber.text = "${position + 1}."
            itemView.setOnClickListener {
                if (volumeEntity.volUrl.isNotEmpty()) clickItemRecycler?.onItemClick(position)
            }

        }

    }
}
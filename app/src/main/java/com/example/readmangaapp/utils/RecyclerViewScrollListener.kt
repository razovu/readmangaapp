package com.example.readmangaapp.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

//Определеяем видимость последних элементов чтобы загрузить следующие данные

fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: () -> Unit) {

    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        private val VISIBLE_THRESHOLD = 5
        private var loading = true
        private var previousTotal = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            with(layoutManager as GridLayoutManager) {

                val visibleItemCount = childCount
                val totalItemCount = itemCount
                val lastVisible = findLastVisibleItemPosition()

                //исключение бесконечного цикла
                if (loading && totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }


                if (!loading && (totalItemCount - visibleItemCount) <= (lastVisible + VISIBLE_THRESHOLD)) {
                    onScrolledToEnd()
                    loading = true
                }
            }

        }

    })
}
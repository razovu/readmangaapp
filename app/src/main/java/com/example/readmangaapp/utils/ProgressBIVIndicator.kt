package com.example.readmangaapp.utils

import android.view.LayoutInflater
import android.view.View
import com.example.readmangaapp.R
import com.filippudak.ProgressPieView.ProgressPieView
import com.github.piasy.biv.indicator.ProgressIndicator
import com.github.piasy.biv.view.BigImageView
import java.util.*

class ProgressBIVIndicator : ProgressIndicator {
    private var mProgressPieView: ProgressPieView? = null
    override fun getView(parent: BigImageView): View {
        mProgressPieView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ui_progress_pie_indicator, parent, false) as ProgressPieView?
        return mProgressPieView!!
    }

    override fun onStart() {
        // not interested
    }

    override fun onProgress(progress: Int) {
        if (progress < 0 || progress > 100 || mProgressPieView == null) {
            return
        }
        mProgressPieView!!.progress = progress
        mProgressPieView!!.text = String.format(Locale.getDefault(), "%d%%", progress)
    }

    override fun onFinish() {
        // not interested
    }
}
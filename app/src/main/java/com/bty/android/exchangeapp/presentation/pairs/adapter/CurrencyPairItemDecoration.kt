package com.bty.android.exchangeapp.presentation.pairs.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bty.android.exchangeapp.R

class CurrencyPairItemDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(
            view.context.resources.getDimension(R.dimen.base_margin).toInt(),
            view.context.resources.getDimension(R.dimen.item_margin).toInt(),
            view.context.resources.getDimension(R.dimen.base_margin).toInt(),
            view.context.resources.getDimension(R.dimen.item_margin).toInt()
        )
    }
}
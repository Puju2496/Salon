package com.example.salon.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class SpaceItemDecoration(private val space: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = parent.adapter?.itemCount ?: 0
        Timber.d("<<>> $itemCount, ${parent.getChildAdapterPosition(view)}")
        if (parent.getChildAdapterPosition(view) < itemCount) {
            if ((parent.layoutManager as LinearLayoutManager).orientation == RecyclerView.HORIZONTAL)
                outRect.right = space
            else
                outRect.bottom = space
        }
    }
}
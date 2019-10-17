package com.example.anothertimdatxe.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemRecyclerViewDecoration(var context: Context, var spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            right = getDimensionPixelSize(spacing)
            left = getDimensionPixelSize(spacing)
            bottom = getDimensionPixelSize(spacing)
        }
    }

    fun getDimensionPixelSize(spacing: Int): Int {
        return context.resources.getDimensionPixelSize(spacing)
    }
}
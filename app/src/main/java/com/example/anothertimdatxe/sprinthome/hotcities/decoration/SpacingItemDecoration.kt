package com.example.anothertimdatxe.sprinthome.hotcities.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(var spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val params = view.layoutParams as GridLayoutManager.LayoutParams
        with(outRect) {
            if (params.spanIndex % 2 == 0) {
                left = spacing / 2
                bottom = spacing
                right = spacing / 2
            } else {
                right = spacing / 2
                bottom = spacing
                left = spacing / 2
            }
        }
    }
}
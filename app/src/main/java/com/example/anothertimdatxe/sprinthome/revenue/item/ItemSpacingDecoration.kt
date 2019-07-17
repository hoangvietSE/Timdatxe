package com.example.anothertimdatxe.sprinthome.revenue.item

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(var offset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                left = offset
            }
            right = offset
            top = offset
            bottom = offset
        }
    }
}
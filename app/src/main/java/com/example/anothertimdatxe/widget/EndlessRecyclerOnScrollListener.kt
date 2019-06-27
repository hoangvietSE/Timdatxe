package com.example.anothertimdatxe.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener(private val mLayoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var mDisable: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = mLayoutManager.itemCount
        if (mLayoutManager is LinearLayoutManager) {
            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
        } else if (mLayoutManager is GridLayoutManager) {
            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!mDisable && !loading && totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            onLoadMore()
            loading = true
        }
    }

    fun reset() {
        previousTotal = 0
        loading = true
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
    }

    fun disable() {
        mDisable = false
    }

    abstract fun onLoadMore()

    companion object {
        const val VISIBLE_THRESHOLD = 5 // The minimum amount of items to have below your current scroll position before loading more.
    }
}
package com.example.anothertimdatxe.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.adapter.BaseAdapter
import kotlinx.android.synthetic.main.layout_base_recycler_view.view.*

class BaseRecyclerView(context: Context?, attrs: AttributeSet?) : BaseCustomViewRelativeLayout(context, attrs) {
    private var mParentListener: onRefreshLoadMoreListener? = null
    private var mEndlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mAdapter: BaseAdapter<*, *>?=null

    override val mLayoutId: Int
        get() = R.layout.layout_base_recycler_view

    override fun initView() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
    }

    override fun initData() {
    }

    override fun initListener() {
        swipeRefresh.setOnRefreshListener {
            mParentListener!!.onRefresh()
            setOnLoadMoreListener(object : onLoadMoreListener{
                override fun onLoadMore() {
                    mParentListener!!.onLoadMore()                }
            })
        }
    }

    fun setListLayoutManager() {
        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvActual.layoutManager = mLayoutManager
        setOnLoadMoreListener(object : onLoadMoreListener {
            override fun onLoadMore() {
                mParentListener!!.onLoadMore()
            }

        })
    }

    fun setAdapter(adapter: BaseAdapter<*, *>) {
        mAdapter = adapter
        rvActual.adapter = adapter
    }

    fun setOnLoadMoreListener(listener: onLoadMoreListener) {
        mEndlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener(mLayoutManager!!) {
            override fun onLoadMore() {
                listener.onLoadMore()
            }
        }
        rvActual.addOnScrollListener(mEndlessRecyclerOnScrollListener!!)
    }

    /**
     * Set action for pull and refresh list.
     *
     * @param listener Listener pull to refresh
     */
    fun setOnRefreshListener(listen: SwipeRefreshLayout.OnRefreshListener) {
        swipeRefresh.setOnRefreshListener(listen)
    }

    fun <T> setListItem(list: List<T>) {
        (mAdapter as BaseAdapter<T, *>).setListItems(list)
    }

    fun <T> addListItem(list: List<T>) {
        (mAdapter as BaseAdapter<T, *>).addListItems(list)
    }

    fun showStartLoading() {
        swipeRefresh.isRefreshing = true
    }

    fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    fun turnOffLoading() {
        if (isNoItem()) {
            tv_no_item.visibility = View.VISIBLE
            rvActual.visibility = View.GONE
        } else {
            tv_no_item.visibility = View.GONE
            rvActual.visibility = View.VISIBLE
        }
        swipeRefresh.isRefreshing = false
    }

    fun <T> getListItem(): ArrayList<T> {
        return mAdapter!!.getListItem() as ArrayList<T>
    }

    fun notifyItemChanged(position: Int) {
        mAdapter!!.notifyItemChanged(position)
    }

    fun removeAndNotify(position: Int) {
        mAdapter!!.removeAndNotify(position)
    }

    fun isNoItem(): Boolean {
        if (!(mAdapter != null && mAdapter!!.getItemCount() > 0)) {
            return true
        }
        return false
    }

    fun setOnRefreshAndLoadMore(listener: onRefreshLoadMoreListener) {
        mParentListener = listener
    }

    interface onLoadMoreListener {
        fun onLoadMore()
    }

    interface onRefreshLoadMoreListener {
        fun onRefresh()
        fun onLoadMore()
    }
}
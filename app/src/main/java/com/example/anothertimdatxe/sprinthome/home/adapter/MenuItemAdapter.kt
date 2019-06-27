package com.example.anothertimdatxe.sprinthome.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.adapter.BaseAdapter
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.base.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuItemAdapter(context: Context, var mListener: BaseRvListener?) : BaseAdapter<MenuItemData, MenuItemAdapter.MenuViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false), mListener)
    }

    class MenuViewHolder(itemView: View, mListener: BaseRvListener?) : BaseViewHolder<MenuItemData>(itemView, mListener) {
        override fun bindData(data: MenuItemData) {
            itemView.tv_menu_item.text = data.title
            itemView.imv_menu_item.setImageResource(data.itemNormal)
        }
    }
}
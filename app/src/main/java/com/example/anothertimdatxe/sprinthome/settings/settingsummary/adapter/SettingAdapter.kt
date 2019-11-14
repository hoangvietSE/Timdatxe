package com.example.anothertimdatxe.sprinthome.settings.settingsummary.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.adapter.BaseAdapter
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.base.adapter.BaseViewHolder
import com.example.anothertimdatxe.extension.inflate
import kotlinx.android.synthetic.main.item_settings.view.*

class SettingAdapter(context: Context, var mListener: BaseRvListener) :
        BaseAdapter<ItemData, SettingAdapter.ViewHolder>(context) {
    init {
        mListItem.add(ItemData("Điều khoản và dịch vụ"))
        mListItem.add(ItemData("Ngôn ngữ"))
        mListItem.add(ItemData("Đổi mật khẩu"))
        mListItem.add(ItemData("Đánh giá ứng dụng"))
        mListItem.add(ItemData("FAQ"))
        mListItem.add(ItemData("Cập nhật version APP"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(context.inflate(R.layout.item_settings, parent, false), mListener)
    }

    class ViewHolder(itemView: View, listener: BaseRvListener?) : BaseViewHolder<ItemData>(itemView, listener) {
        override fun bindData(data: ItemData) {
            itemView.tv_item_setting.text = data.nameOfSetting
        }
    }
}
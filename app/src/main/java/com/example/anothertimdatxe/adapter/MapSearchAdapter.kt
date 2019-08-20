package com.example.anothertimdatxe.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.adapter.BaseAdapter
import com.example.anothertimdatxe.base.adapter.BaseRvListener
import com.example.anothertimdatxe.base.adapter.BaseViewHolder
import com.example.anothertimdatxe.extension.inflate
import com.google.android.libraries.places.api.model.AutocompletePrediction
import kotlinx.android.synthetic.main.item_search_prediction.view.*

class MapSearchAdapter(context: Context, var mListener: BaseRvListener) : BaseAdapter<AutocompletePrediction, MapSearchAdapter.MapSearchViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapSearchViewHolder {
        return MapSearchViewHolder(context!!.inflate(R.layout.item_search_prediction, parent, false), mListener)
    }

    class MapSearchViewHolder(itemView: View, mListener: BaseRvListener) : BaseViewHolder<AutocompletePrediction>(itemView, mListener) {
        override fun bindData(data: AutocompletePrediction) {
            itemView.tv_location_primary.text = data.getPrimaryText(null).toString()
            itemView.tv_location_secondary.text = data.getSecondaryText(null).toString()
        }
    }
}
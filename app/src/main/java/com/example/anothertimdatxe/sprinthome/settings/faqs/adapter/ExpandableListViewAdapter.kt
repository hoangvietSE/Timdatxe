package com.example.anothertimdatxe.sprinthome.settings.faqs.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.example.anothertimdatxe.R

class ExpandableListViewAdapter(
    var context: Context,
    var expandableListTitle: MutableList<String>,
    var expandableListDetail: MutableMap<String, String>
) : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return expandableListTitle.get(groupPosition)
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var title: String =  expandableListTitle.get(groupPosition)
        //var title: String = expandableListTitle.get(groupPosition)
        var convertView: View? = convertView
        if (convertView == null) {
            var layoutInflater: LayoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.faqs_list_group, null)
        }
        var tvFaqsNumber:AppCompatTextView = convertView!!.findViewById(R.id.tvFaqsNumber)
        var tvListGroup: TextView = convertView.findViewById(R.id.tvListGroup)
        tvFaqsNumber.setText("${groupPosition + 1}) ")
        tvListGroup.setText(title)
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return expandableListDetail.get(expandableListTitle.get(groupPosition)).toString()
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var titleItem: String = expandableListDetail.get(expandableListTitle.get(groupPosition)).toString()
        var convertView: View? = convertView
        if (convertView == null) {
            var layoutInflater: LayoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.faqs_list_item, null)
        }
        var tvListItem: TextView = convertView!!.findViewById(R.id.tvListItem)
        //show html in TextView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvListItem.setText(Html.fromHtml(titleItem, Html.FROM_HTML_MODE_COMPACT))
        } else {
            tvListItem.setText(Html.fromHtml(titleItem))
        }
        //tvListItem.setText(titleItem)
        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }
}
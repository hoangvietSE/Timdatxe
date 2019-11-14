package com.example.anothertimdatxe.sprinthome.settings.faqs

import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.entity.response.FaqsResponse
import com.example.anothertimdatxe.sprinthome.settings.faqs.adapter.ExpandableListViewAdapter
import kotlinx.android.synthetic.main.activity_faqs.*

class FaqsActivity : TimdatxeBaseActivity<FaqsPresenter>(),
        FaqsView {
    var expandableListTitle: MutableList<String> = mutableListOf()
    var expandableListDetail: MutableMap<String, String> = mutableMapOf()
    var expandableListAdapter: ExpandableListViewAdapter? = null

    override fun setView(result: List<FaqsResponse>?) {
        for (i in result!!) {
            expandableListDetail.put(i.title, i.content)
        }
        expandableListTitle = expandableListDetail.keys.toMutableList()
        expandableListAdapter = ExpandableListViewAdapter(this, expandableListTitle, expandableListDetail)
        elv_faqs.setAdapter(expandableListAdapter)
    }

    override val layoutRes: Int
        get() = R.layout.activity_faqs

    override fun getPresenter(): FaqsPresenter {
        return FaqsPresenterImpl(this)
    }

    override fun initView() {
        mPresenter!!.onGetFaqs()
        toolbarTitle?.let {
            it.text = getString(R.string.faqs_title)
        }
    }

    //set indicator is in the right
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            elv_faqs.setIndicatorBounds(elv_faqs.getMeasuredWidth() - 80, elv_faqs.getMeasuredWidth())
        } else {
            elv_faqs.setIndicatorBoundsRelative(elv_faqs.getMeasuredWidth() - 80, elv_faqs.getMeasuredWidth())
        }
    }

}
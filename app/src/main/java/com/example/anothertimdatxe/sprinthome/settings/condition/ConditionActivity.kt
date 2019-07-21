package com.example.anothertimdatxe.sprinthome.condition

import android.graphics.Color
import beetech.com.carbooking.sprinthome.condition.ConditionPresenter
import beetech.com.carbooking.sprinthome.condition.ConditionPresenterImpl
import beetech.com.carbooking.sprinthome.condition.ConditionView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.entity.response.TermAndConditionResponse
import kotlinx.android.synthetic.main.activity_condition.*

class ConditionActivity : TimdatxeBaseActivity<ConditionPresenter>(), ConditionView {

    override val layoutRes: Int
        get() = R.layout.activity_condition

    override fun getPresenter(): ConditionPresenter {
        return ConditionPresenterImpl(this)
    }

    override fun initView() {
        initData()
    }

    private fun initData() {
        mPresenter!!.getContentCondition()
    }

    override fun showContentCondition(data: TermAndConditionResponse) {
        initToolbarTitle(data)
        showContent(data)
    }

    private fun showContent(data: TermAndConditionResponse) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tv_term_and_condition.setText(Html.fromHtml(data.content, Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            tv_term_and_condition.setText(Html.fromHtml(data.content));
//        }

        var settings = tv_term_and_condition.settings
        settings.setDefaultTextEncodingName("utf-8")
        tv_term_and_condition.loadData(data.content, "text/html; charset=utf-8",null)
        tv_term_and_condition.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun initToolbarTitle(data: TermAndConditionResponse) {
        toolbarTitle?.let {
            it.text = data.title!!.toUpperCase()
        }
    }
}
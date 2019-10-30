package com.example.anothertimdatxe.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.invisible
import com.example.anothertimdatxe.extension.visible

class BaseConfirmBookingRow : BaseCustomViewRelativeLayout {
    companion object {
        const val TYPE_EDITTEXT = 1
        const val TYPE_TEXTVIEW = 2
    }

    override val mLayoutId: Int
        get() = R.layout.layout_row_user_confirm_booking
    override val styleableId: IntArray?
        get() = R.styleable.BaseItemUserConfirmBooking
    private var rowTitle: TextView? = null
    private var rowEdittext: EditText? = null
    private var rowTextView: TextView? = null
    private var divider: View? = null
    private var type: Int? = -1

    override fun initView() {
        rowTitle = findViewById(R.id.row_title)
        rowEdittext = findViewById(R.id.row_edt_detail)
        rowTextView = findViewById(R.id.row_tv_detail)
        divider = findViewById(R.id.divider)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initDataStyleable(a: TypedArray?) {
        super.initDataStyleable(a)
        val title = a?.getString(R.styleable.BaseItemUserConfirmBooking_ucb_title)
        val detail = a?.getString(R.styleable.BaseItemUserConfirmBooking_ucb_detail)
        val type = a?.getInt(R.styleable.BaseItemUserConfirmBooking_ucb_type_detail, 1)
        val enable = a?.getBoolean(R.styleable.BaseItemUserConfirmBooking_ucb_enable_line, true)
        setType(type)
        setDivider(enable)
        setTitle(title)
        setDetail(detail)
    }

    fun setTitle(title: String?) {
        title?.let {
            rowTitle?.text = title
        }
    }

    fun setDetail(detail: String?) {
        detail?.let {
            rowEdittext?.setText(detail)
            rowTextView?.text = detail
        }
    }

    fun getDetail(): String? {
        if(rowEdittext?.isShown!!){
            return rowEdittext?.text.toString()
        }else{
            return rowTextView?.text.toString()
        }
    }


    fun setDivider(enable: Boolean?) {
        if (enable!!) {
            divider?.visible()
        } else {
            divider?.gone()
        }
    }

    fun setType(type: Int?) {
        this.type = type
        when (type) {
            TYPE_EDITTEXT -> {
                rowEdittext?.visible()
                rowTextView?.invisible()
            }
            TYPE_TEXTVIEW -> {
                rowEdittext?.invisible()
                rowTextView?.visible()
            }
        }
    }

    fun requestFocus(error: String) {
        rowEdittext?.error = error
        rowEdittext?.requestFocus()
    }

    fun clearError() {
        rowEdittext?.error = null
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}
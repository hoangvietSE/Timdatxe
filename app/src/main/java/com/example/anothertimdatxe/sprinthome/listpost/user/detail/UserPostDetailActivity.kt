package com.example.anothertimdatxe.sprinthome.listpost.user.detail

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity
import com.example.anothertimdatxe.entity.response.PostDetailResponse
import com.example.anothertimdatxe.entity.response.UserPostResponse
import com.example.anothertimdatxe.extension.gone
import com.example.anothertimdatxe.extension.invisible
import com.example.anothertimdatxe.extension.visible
import com.example.anothertimdatxe.presentation.map.mapshow.MapShowActivity
import com.example.anothertimdatxe.util.*
import kotlinx.android.synthetic.main.activity_user_post_detail.*

class UserPostDetailActivity : BaseActivity<UserPostDetailPresenter>(), UserPostDetailView {
    companion object {
        const val EXTRA_USER_POST_ID = "extra_user_post_id"
        const val CAN_EDIT = 1
        const val CAN_BOOK = 1
    }

    private var userPostId: Int? = null
    private var mUserPostResponse: UserPostResponse? = null

    override val layoutRes: Int
        get() = R.layout.activity_user_post_detail

    override fun getPresenter(): UserPostDetailPresenter {
        return UserPostDetailPresenterImpl(this)
    }

    override fun initView() {
        setLayoutToolbar()
        getDataIntent()
        fetchUserPostDetail()
    }

    private fun fetchUserPostDetail() {
        mPresenter?.fetchUserPostDetail(userPostId!!)
    }

    private fun getDataIntent() {
        userPostId = intent.getIntExtra(EXTRA_USER_POST_ID, -1)
    }

    private fun setLayoutToolbar() {
        toolbarTitle?.let {
            it.text = resources.getString(R.string.user_post_detail_toolbar_title).toUpperCase()
        }
    }

    override fun setListener() {
        rightButton?.setOnClickListener {
            showDeleteConfirm()
        }
        btn_show_map.setOnClickListener {
            startActivity(Intent(this, MapShowActivity::class.java).apply {
                putExtra(MapShowActivity.LAT_FROM, mUserPostResponse?.lat_from?.toDouble())
                putExtra(MapShowActivity.LNG_FROM, mUserPostResponse?.lng_from?.toDouble())
                putExtra(MapShowActivity.LAT_TO, mUserPostResponse?.lat_to?.toDouble())
                putExtra(MapShowActivity.LNG_TO, mUserPostResponse?.lng_to?.toDouble())
                putExtra(MapShowActivity.ORIGIN_LOCATION, mUserPostResponse?.start_point)
                putExtra(MapShowActivity.DESTINATION_LOCATION, mUserPostResponse?.end_point)
            })
        }
    }

    private fun showDeleteConfirm() {
        DialogUtil.showAlertDialogTitle(
                this,
                "Thông báo",
                "Bạn có chắc chắn muốn xóa chuyến đi này không?",
                true,
                "TIẾP TỤC",
                "HỦY BỎ",
                object : DialogUtil.BaseAlertDialogListener {
                    override fun onPositiveClick(dialogInterface: DialogInterface) {
                        dialogInterface.dismiss()
                        mPresenter?.deleteUserPost(userPostId!!)
                    }

                    override fun onNegativeClick(dialogInterface: DialogInterface) {
                        dialogInterface.dismiss()
                    }

                }
        )
    }

    override fun showUserPostDetail(data: PostDetailResponse) {

        row_starting_point.setRowDetail(data.startPoint)
        row_ending_point.setRowDetail(data.endPoint)
        row_distance.setRowDetail(NumberUtil.showDistance(data.distance.toString()))
        row_time.setRowDetail(DateUtil.formatDate(data.startTime!!, DateUtil.DATE_FORMAT_13, DateUtil.DATE_FORMAT_24))
        row_time_estimate.setRowDetail(data.durationTime.toString())
        row_number_seat.setRowDetail(data.numberSeat.toString())
        row_status.setRowDetail(data.strStatus)
        when (data.status) {
            Constant.USER_POST_PENDING -> {
                row_status.setRowIcon(R.drawable.ic_status_pending)
                row_status.setDetailColor(R.color.color_pending)
                hideReason()
            }
            Constant.USER_POST_PUBLISHED -> {
                row_status.setRowIcon(R.drawable.ic_status_pending)
                row_status.setDetailColor(R.color.colorPrimary)
                hideReason()
            }
            Constant.USER_POST_DONE -> {
                row_status.setRowIcon(R.drawable.ic_status_reject)
                row_status.setDetailColor(R.color.color_reject)
                hideReason()
            }
            Constant.USER_POST_FINISH -> {
                row_status.setRowIcon(R.drawable.ic_status_finish)
                row_status.setDetailColor(R.color.color_finish)
                hideReason()
            }
            Constant.USER_POST_CANCEL -> {
                row_status.setRowIcon(R.drawable.ic_status_cancel)
                row_status.setDetailColor(R.color.color_cancel)
                showReason(data.strReason)
            }
        }
        tv_requirement.text = data.description?.let { it } ?: ""
        if (data.canEdit == CAN_EDIT) {
            btn_edit.visible()
            rightButton?.visible()
            rightButton?.setImageResource(R.drawable.ic_delete)
        } else {
            btn_edit.gone()
            rightButton?.invisible()
        }
        if (data.canBook == CAN_BOOK) {
            showListDriver()

        } else {
            hideListDriver()
        }
    }

    private fun showListDriver() {
        tv_list_driver.visible()
        recycler_view.visible()
    }

    private fun hideListDriver() {
        tv_list_driver.gone()
        recycler_view.gone()
    }

    private fun showReason(strReason: String?) {
        row_reason.visible()
        row_reason.setRowDetail(strReason)
    }

    private fun hideReason() {
        row_reason.gone()
    }

    override fun onDeleteUserPostSuccess() {
        ToastUtil.show(resources.getString(R.string.user_post_detail_delete_success))
        setResult(Activity.RESULT_OK)
        finish()
    }
}
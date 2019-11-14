package com.example.anothertimdatxe.sprinthome.settings.version

import android.os.Build
import com.example.anothertimdatxe.BuildConfig
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.common.TimdatxeBaseActivity
import com.example.anothertimdatxe.entity.response.VersionAppResponse
import com.example.anothertimdatxe.util.DateUtil
import kotlinx.android.synthetic.main.activity_update_version.*

class VersionUpdateActivity : TimdatxeBaseActivity<VersionUpdatePresenter>(), VersionUpdateView {
    private var mCurrentVersionApp: String? = null

    override val layoutRes: Int
        get() = R.layout.activity_update_version

    override fun getPresenter(): VersionUpdatePresenter {
        return VersionUpdatePresenterImpl(this)
    }

    override fun initView() {
        initToolbarTitle()
        getCurrentVersionApp()
        getData()
    }

    private fun getCurrentVersionApp() {
        mCurrentVersionApp = BuildConfig.VERSION_NAME
    }

    private fun initToolbarTitle() {
        toolbarTitle!!.text = resources.getString(R.string.update_version_title)
    }

    private fun getData() {
        mPresenter!!.getVersionApp()
    }

    override fun showVersionApp(data: List<VersionAppResponse>) {
        showContentUpdateVersion(data)
    }

    private fun showContentUpdateVersion(data: List<VersionAppResponse>) {
        tv_app_version.text = data.get(0).app_version
        tv_release_date.text = DateUtil.formatDate(data.get(0).release_date!!, DateUtil.DATE_FORMAT_1, DateUtil.DATE_FORMAT_21)
        tv_description.text = data.get(0).description
        if (mCurrentVersionApp!!.equals(data.get(0).app_version)) {
            btn_update?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    it.background = resources.getDrawable(R.drawable.bg_button_update_version, null)
                } else {
                    it.background = resources.getDrawable(R.drawable.bg_button_update_version)
                }
            }
        } else {
            btn_update?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    it.background = resources.getDrawable(R.drawable.bg_button_update_version_not_update, null)
                } else {
                    it.background = resources.getDrawable(R.drawable.bg_button_update_version_not_update)
                }
            }
        }
    }
}
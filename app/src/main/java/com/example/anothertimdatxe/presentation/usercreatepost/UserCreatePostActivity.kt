package com.example.anothertimdatxe.presentation.usercreatepost

import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.activity.BaseActivity

class UserCreatePostActivity : BaseActivity<UserCreatePostPresenter>(), UserCreatePostView {
    override val layoutRes: Int
        get() = R.layout.activity_user_book_detail

    override fun getPresenter(): UserCreatePostPresenter {
        return UserCreatePostPresenterImpl(this)
    }

    override fun initView() {

    }
}
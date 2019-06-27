package com.example.anothertimdatxe.sprinthome.profile.user

import android.os.Bundle
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.extension.setAvatar
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : BaseFragment<UserProfilePresenter>(), UserProfileView {
    companion object {
        fun getInstance(): UserProfileFragment {
            var bundle: Bundle = Bundle()
            val fragment: UserProfileFragment = UserProfileFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_user_profile

    override fun initView() {
        mPresenter!!.getUserData()
    }

    override fun getPresenter(): UserProfilePresenter {
        return UserProfilePresenterImpl(this)
    }

    override fun showData(userData: UserData) {
        imv_avatar.let {
            it.setAvatar(context!!, it, userData.avatar!!)
        }
        tv_fullname.text = userData.full_name
        tv_number_trip.text = userData.count_books.toString()
        rowFullName.setContent(userData.full_name)
        rowEmail.setContent(userData.email)
        rowPhoneNumber.setContent(userData.phone)
        rowDateOfBirth.setContent(userData.birthday)
        rowGender.setContent(userData.gender)
        rowAddress.setContent(userData.address)
        userData.description?.let {
            tv_des.text = (if (it.isNullOrEmpty() || it.isNullOrBlank()) resources.getString(R.string.user_profile_des) else it).toString()
        }
    }
}
package com.example.anothertimdatxe.sprinthome.profile.user

import android.os.Bundle
import com.example.anothertimdatxe.R
import com.example.anothertimdatxe.base.fragment.BaseFragment
import com.example.anothertimdatxe.entity.UserData
import com.example.anothertimdatxe.extension.setAvatar
import com.example.anothertimdatxe.sprinthome.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_user_profile.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class UserProfileFragment : BaseFragment<UserProfilePresenter>(), UserProfileView {
    private var mUserProfile: UserData? = null

    companion object {
        fun getInstance(): UserProfileFragment {
            var bundle = Bundle()
            val fragment = UserProfileFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    override val layoutRes: Int
        get() = R.layout.fragment_user_profile

    override fun initView() {
        mPresenter!!.getUserData()
        EventBus.getDefault().register(this)
    }

    override fun getPresenter(): UserProfilePresenter {
        return UserProfilePresenterImpl(this)
    }

    override fun showData(userData: UserData) {
        mUserProfile = userData
        imv_avatar.setAvatar(context!!, userData.avatar)
        tv_fullname.text = userData.fullName
        userData.count_books.let {
            if (it != null) {
                tv_number_trip.text = it.toString()
            }
        }
        rowFullName.setContent(userData.fullName)
        rowEmail.setContent(userData.email)
        rowPhoneNumber.setContent(userData.phone)
        rowDateOfBirth.setContent(userData.birthday)
        when (userData.gender) {
            "0" -> {
                rowGender.setContent("Nam")
            }
            "1" -> {
                rowGender.setContent("Nữ")
            }
        }
        rowAddress.setContent(userData.address)
        userData.description?.let {
            tv_des.text = (if (it.isEmpty() || it.isNullOrBlank()) resources.getString(R.string.user_profile_des) else it).toString()
        }
    }

    @Subscribe(sticky = true)
    fun updateUserProfile(userData: UserData) {
        showData(userData)
        (activity as HomeActivity).updateHeaderMenu(userData.fullName, userData.avatar!!)
        EventBus.getDefault().removeStickyEvent(userData)
    }

    fun getUserProfile(): UserData {
        return mUserProfile!!
    }
}
package com.example.anothertimdatxe.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.anothertimdatxe.R

class LoginDialogFrag : DialogFragment() {

    companion object {
        fun newInstance(): DialogFragment {
            var fragment = DialogFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.dialog_direct_login, container, false)
    }
}
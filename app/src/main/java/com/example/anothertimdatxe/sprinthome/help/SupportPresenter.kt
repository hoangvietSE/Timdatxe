package com.example.anothertimdatxe.sprinthome.help

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.entity.ContactEntity

interface SupportPresenter : BasePresenter {
    fun sendSupport(data: ContactEntity)
}
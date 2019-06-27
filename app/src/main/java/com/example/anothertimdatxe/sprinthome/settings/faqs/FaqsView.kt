package com.example.anothertimdatxe.sprinthome.settings.faqs

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.FaqsResponse

interface FaqsView : BaseView {
    fun setView(result: List<FaqsResponse>?)
}
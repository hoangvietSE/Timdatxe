package com.example.anothertimdatxe.sprinthome.settings.version

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.response.VersionAppResponse

interface VersionUpdateView : BaseView {
    fun showVersionApp(data: List<VersionAppResponse>)
}
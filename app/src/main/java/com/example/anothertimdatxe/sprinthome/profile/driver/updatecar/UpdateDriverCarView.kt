package com.example.anothertimdatxe.sprinthome.profile.driver.updatecar

import com.example.anothertimdatxe.base.mvp.BaseView
import com.example.anothertimdatxe.entity.DriverCarImage
import com.example.anothertimdatxe.entity.response.DriverCarBrandDetailResponse
import com.example.anothertimdatxe.entity.response.DriverCarBrandResponse

interface UpdateDriverCarView : BaseView {
    fun setAdapter(list: MutableList<DriverCarImage>)
    fun setButtonAdd()
    fun addImageIndex(pos: Int, image: DriverCarImage)
    fun addLastImage(image: DriverCarImage)
    fun removeItemAndAddButton(pos: Int)
    fun removeItem(pos: Int)
    fun showListDriverCarBrand(list: List<DriverCarBrandResponse>)
    fun showListDriverCarName(list: List<DriverCarBrandDetailResponse>)
    fun onCarBrandError()
    fun onCarNameSpinnerError()
    fun onCarNameEdittextError()
    fun onDoixeError()
    fun onNumberSeatError()
    fun onLicensePlateError()
    fun onColorError()
}
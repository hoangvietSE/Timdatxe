package com.example.anothertimdatxe.sprinthome.profile.driver.updatecar

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.entity.DriverCarImage

interface UpdateDriverCarPresenter : BasePresenter {
    fun addListImage(listImage: List<DriverCarImage>)
    fun addImage(image: DriverCarImage)
    fun setImage(listImage: MutableList<DriverCarImage>)
    fun deleteImage(pos: Int)
    fun fetchDriverCarBrand()
    fun fetchDriverCarName(id: Int)
}
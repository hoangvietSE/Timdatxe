package com.example.anothertimdatxe.sprinthome.profile.driver.updatecar

import com.example.anothertimdatxe.base.mvp.BasePresenter
import com.example.anothertimdatxe.entity.DriverCarImage

interface UpdateDriverCarPresenter : BasePresenter {
    fun initCarName()
    fun addListImage(listImage: List<DriverCarImage>)
    fun addImage(image: DriverCarImage)
    fun setImage(listImage: MutableList<DriverCarImage>)
    fun deleteImage(pos: Int)
    fun fetchDriverCarBrand()
    fun fetchDriverCarName(id: Int)
    fun createDriverCar(
            carBrandId: Int,
            isSpinnerCarName: Boolean,
            carId: Int,
            carName: String,
            carVersion: String,
            seatNumber: String,
            color: String,
            licensePlate: String,
            registrationDate: String,
            registerDate: String)
}
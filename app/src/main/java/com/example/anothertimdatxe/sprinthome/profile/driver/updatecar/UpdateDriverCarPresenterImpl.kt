package com.example.anothertimdatxe.sprinthome.profile.driver.updatecar

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.DriverCarImage
import com.example.anothertimdatxe.entity.response.DriverCarBrandDetailResponse
import com.example.anothertimdatxe.entity.response.DriverCarBrandResponse
import com.example.anothertimdatxe.util.NetworkUtil
import java.util.*

class UpdateDriverCarPresenterImpl(mView: UpdateDriverCarView) : BasePresenterImpl<UpdateDriverCarView>(mView), UpdateDriverCarPresenter {
    private var mList: MutableList<DriverCarImage> = mutableListOf()

    override fun addListImage(listImage: List<DriverCarImage>) {
    }

    override fun setImage(listImage: MutableList<DriverCarImage>) {
        this.mList = listImage
        if (listImage.size == 0) {
            mView!!.setAdapter(listImage)
            mView!!.setButtonAdd()
        }
    }

    override fun addImage(image: DriverCarImage) {
        if (mList.size < 5) {
            mView!!.addImageIndex(mList.size - 1, image)
        } else if (mList.size == 5) {
            mView!!.addLastImage(mList.size - 1, image)
        }
    }

    override fun deleteImage(pos: Int) {
        if (pos == mList.size - 1) {
            mView!!.removeItemAndAddButton(pos)
        } else if (pos < mList.size - 1) {
            mView!!.removeItem(pos)
        }
    }

    override fun fetchDriverCarBrand() {
        val disposable = RetrofitManager.driverCarBrand()
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            var mList: MutableList<DriverCarBrandResponse> = mutableListOf()
                            mList.add(0, DriverCarBrandResponse(-1, "Chọn hãng xe"))
                            mList.addAll(it.data!!)
                            mView!!.showListDriverCarBrand(mList)
                        },
                        {
                            NetworkUtil.handleError(it)
                        }
                )
        addDispose(disposable)
    }

    override fun fetchDriverCarName(id: Int) {
        val disposable = RetrofitManager.driverCarName(id)
                .doOnSubscribe {
                    mView!!.showLoading()
                }
                .doFinally {
                    mView!!.hideLoading()
                }
                .subscribe(
                        {
                            var mList: MutableList<DriverCarBrandDetailResponse> = mutableListOf()
                            mList.add(DriverCarBrandDetailResponse(-1, "Chọn tên xe", -1))
                            mList.addAll(it.data!!)
                            mView!!.showListDriverCarName(mList)
                        },
                        {
                            NetworkUtil.handleError(it)
                        }
                )
        addDispose(disposable)
    }

    override fun initCarName() {
        var mList: MutableList<DriverCarBrandDetailResponse> = mutableListOf()
        mList.add(DriverCarBrandDetailResponse(-1, "Chọn tên xe", -1))
        mView!!.showListDriverCarName(mList)
    }

    override fun createDriverCar(
            carBrandId: Int,
            isSpinnerCarName: Boolean,
            carId: Int, carName: String,
            carVersion: String,
            seatNumber: String,
            color: String,
            licensePlate: String,
            registrationDate: String,
            registerDate: String) {
        if (carBrandId == -1) {
            mView!!.onCarBrandError()
            return
        }
        if (isSpinnerCarName == true) {
            if (carId == -1) {
                mView!!.onCarNameSpinnerError()
                return
            }
        }
        if (isSpinnerCarName == false) {
            if (carName.isNullOrEmpty()) {
                mView!!.onCarNameEdittextError()
                return
            }
        }
        if (carVersion == "Chọn đời xe") {
            mView!!.onDoixeError()
            return
        }
        if (seatNumber.isNullOrEmpty()) {
            mView!!.onNumberSeatError()
            return
        }
        if (licensePlate.isNullOrEmpty()) {
            mView!!.onLicensePlateError()
            return
        }
        if (color.isNullOrEmpty()) {
            mView!!.onColorError()
            return
        }
        if (registerDate.isNullOrEmpty()) {
            mView!!.onDateRegisEmptyError()
            return
        }
        if (!registerDate.isNullOrEmpty()) {
            val calendar = Calendar.getInstance()
            val currentDatTime = Calendar.getInstance(TimeZone.getDefault())
            val list = registerDate.split('/')
            calendar.set(list[2].toInt(), list[1].toInt() - 1, list[0].toInt())
            if (calendar.after(currentDatTime) || calendar.equals(currentDatTime)) {
                mView!!.onDateRegisInFutureError()
            }
            return
        }
        if (registrationDate.isNullOrEmpty()) {
            mView!!.onDateRegistrationEmptyError()
            return
        }
        if (!registrationDate.isNullOrEmpty()) {
            val calendar = Calendar.getInstance()
            val regisDateCalendar = Calendar.getInstance()
            val list = registerDate.split('/')
            calendar.set(list[2].toInt(), list[1].toInt() - 1, list[0].toInt())
            val listDateRegis = registrationDate.split('/')
            regisDateCalendar.set(listDateRegis[2].toInt(), listDateRegis[1].toInt() - 1, listDateRegis[0].toInt())
            if (regisDateCalendar.before(calendar) || regisDateCalendar.equals(calendar)) {
                mView!!.onRegistrationNotLessThanRegiterDate()
            }
        }
    }

}
package com.example.anothertimdatxe.sprinthome.profile.driver.updatecar

import com.example.anothertimdatxe.base.mvp.BasePresenterImpl
import com.example.anothertimdatxe.base.network.RetrofitManager
import com.example.anothertimdatxe.entity.DriverCarImage
import com.example.anothertimdatxe.util.NetworkUtil

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
            mView!!.addLastImage(image)
        }
    }

    override fun deleteImage(pos: Int) {
        if (pos == 4) {
            mView!!.removeItemAndAddButton(pos)
        } else if (pos < 4) {
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
                            mView!!.showListDriverCarBrand(it.data!!)
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
                            mView!!.showListDriverCarName(it.data!!)
                        },
                        {
                            NetworkUtil.handleError(it)
                        }
                )
        addDispose(disposable)
    }
}
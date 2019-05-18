package com.example.anothertimdatxe.base.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenterImpl<T : BaseView>(mView: T) : BasePresenter {
    protected var mView: T? = mView
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onStart() {
        //initView
        mView!!.let {
            it.initView()
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        mView = null
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
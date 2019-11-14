package com.example.anothertimdatxe.base.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenterImpl<T : BaseView>(mView: T) : BasePresenter {
    protected var mView: T? = mView
    private var compositeDisposable = CompositeDisposable()

    override fun start() {
        mView!!.initView()
    }

    override fun destroy() {
        mView = null
        compositeDisposable.clear()
    }

    fun addDispose(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
package com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.base

open class BasePresenter<V> : IBasePresenter<V> where V : IBaseView {

    protected var mView : V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

}
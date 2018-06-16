package com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.base

interface IBasePresenter<in V : IBaseView> {
    fun attachView(view : V)
    fun detachView()
}
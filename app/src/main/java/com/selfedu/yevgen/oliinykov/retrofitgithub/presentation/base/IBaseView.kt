package com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.base

import android.support.annotation.StringRes
import android.view.View

interface IBaseView {

    fun showProgress()

    fun hideProgress()

    fun getContentView() : View

    fun showError(error: String?)

    fun showError(@StringRes stringResId: Int)

    fun showMessage(@StringRes strResId: Int)

    fun showMessage(message: String)

}
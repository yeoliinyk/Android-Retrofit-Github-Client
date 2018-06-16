package com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.selfedu.yevgen.oliinykov.retrofitgithub.R


abstract class BaseActivity<in V : IBaseView, P : IBasePresenter<V>> : AppCompatActivity(), IBaseView {

    private var mProgressDialog: ProgressDialog? = null

    protected abstract var mPresenter: P

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }


    override fun showProgress() {
        runOnUiThread {
            run { if (mProgressDialog == null) createProgressDialog() else mProgressDialog }
                    ?.show()
        }
    }

    override fun hideProgress() {
        runOnUiThread {
            mProgressDialog?.let { if (it.isShowing) it.dismiss() }
        }
    }

    override fun getContentView(): View {
        return window.decorView
    }

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showError(@StringRes stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(@StringRes strResId: Int) {
        Toast.makeText(this, strResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    protected fun createProgressDialog() =
            ProgressDialog(this@BaseActivity)
                    .apply { setMessage(getString(R.string.loading)) }
                    .apply { setCancelable(false) }

}
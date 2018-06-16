package com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.main

import com.selfedu.yevgen.oliinykov.retrofitgithub.data.Repository
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubIssue
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubRepo
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.UserCredentials
import com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import javax.crypto.Cipher

class MainPresenter : BasePresenter<MainContract.IView>(), MainContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView() {
        super.detachView()
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    override fun onOpenCredentialsDialog() {
        mView?.showCredentialsDialog()
    }

    override fun onLoadUserRepos() {
        compositeDisposable.add(Repository.getUserRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(repositoriesObserver))
    }

    override fun onRepoSelected(repo: GithubRepo) {
        compositeDisposable.add(Repository.getReposIssues(repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(issuesObserver))
    }

    override fun onAddCommentToIssue(comment: String, issue: GithubIssue) {
        compositeDisposable.add(Repository.postComment(comment, issue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(commentObserver))
    }

    override fun onCredentialsEntered(userName: String, pass: String) {
        Repository.saveUserCredentials(UserCredentials(userName, pass))
    }

    override fun onFingerprintValidationSuccess(cipher: Cipher) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val repositoriesObserver: DisposableSingleObserver<List<GithubRepo>>
        get() = object : DisposableSingleObserver<List<GithubRepo>>() {
            override fun onSuccess(value: List<GithubRepo>) {
                mView?.renderUserRepos(value)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mView?.showError("Can not load repositories")
            }
        }

    private val issuesObserver: DisposableSingleObserver<List<GithubIssue>>
        get() = object : DisposableSingleObserver<List<GithubIssue>>() {
            override fun onSuccess(value: List<GithubIssue>) {
                mView?.renderRepoIssues(value)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mView?.showError("Can not load issues")
            }
        }

    private val commentObserver: DisposableSingleObserver<ResponseBody>
        get() = object : DisposableSingleObserver<ResponseBody>() {
            override fun onSuccess(value: ResponseBody) {
                mView?.clearCommentInput()
                mView?.showMessage("Comment Created")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mView?.showError("Can not create comment")
            }
        }

}
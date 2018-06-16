package com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.main

import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubIssue
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubRepo
import com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.base.IBasePresenter
import com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.base.IBaseView
import javax.crypto.Cipher

interface MainContract {
    interface IView : IBaseView {
        fun showCredentialsDialog()
        fun renderUserRepos(repos: List<GithubRepo>)
        fun renderRepoIssues(issues: List<GithubIssue>)
        fun clearCommentInput()
    }

    interface IPresenter : IBasePresenter<IView> {
        fun onOpenCredentialsDialog()
        fun onLoadUserRepos()
        fun onRepoSelected(repo : GithubRepo)
        fun onAddCommentToIssue(comment: String, issue: GithubIssue)
        fun onCredentialsEntered(userName: String, pass: String)
        fun onFingerprintValidationSuccess(cipher: Cipher)
    }
}
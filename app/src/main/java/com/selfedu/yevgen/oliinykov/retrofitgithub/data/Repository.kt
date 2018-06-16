package com.selfedu.yevgen.oliinykov.retrofitgithub.data

import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubIssue
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubRepo
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.UserCredentials
import io.reactivex.Single
import okhttp3.ResponseBody

object Repository : IRepository {

    init {
        println("init complete")
    }

    private lateinit var githubApi: GithubApi

    override fun getUserRepos() : Single<List<GithubRepo>> {
       return githubApi.getRepos()
    }

    override fun getReposIssues(repo: GithubRepo): Single<List<GithubIssue>> {
        return githubApi.getIssues(repo.owner, repo.name)
    }

    override fun postComment(comment: String, issue: GithubIssue): Single<ResponseBody> {
        val body = hashMapOf("body" to comment)
        return githubApi.postComment(issue.commentsUrl, body)
    }

    override fun saveUserCredentials(userCredentials: UserCredentials) {
        //TODO: encode credentials and save to SP
        githubApi = GithubApi.getInstance(userCredentials)
    }

}
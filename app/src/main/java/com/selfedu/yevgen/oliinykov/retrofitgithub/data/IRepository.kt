package com.selfedu.yevgen.oliinykov.retrofitgithub.data

import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubIssue
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubRepo
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.UserCredentials
import io.reactivex.Single
import okhttp3.ResponseBody

interface IRepository {
    fun getUserRepos() : Single<List<GithubRepo>>
    fun getReposIssues(repo : GithubRepo) : Single<List<GithubIssue>>
    fun postComment(comment: String, issue : GithubIssue): Single<ResponseBody>
    fun saveUserCredentials(userCredentials: UserCredentials)
}
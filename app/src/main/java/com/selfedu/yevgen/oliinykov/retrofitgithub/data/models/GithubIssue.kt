package com.selfedu.yevgen.oliinykov.retrofitgithub.data.models

import com.google.gson.annotations.SerializedName

data class GithubIssue(
        @SerializedName("id") var id: String,
        @SerializedName("title") var title: String,
        @SerializedName("comments_url") var commentsUrl: String
) {

    override fun toString(): String {
        return "$id - $title"
    }
}

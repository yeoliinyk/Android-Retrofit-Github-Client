package com.selfedu.yevgen.oliinykov.retrofitgithub.data.models

import com.google.gson.annotations.SerializedName

data class GithubRepo(
        @SerializedName("name") var name: String,
        @SerializedName("owner") var owner: String,
        @SerializedName("url") var url: String
) {
    override fun toString(): String {
        return "$name $url"
    }
}

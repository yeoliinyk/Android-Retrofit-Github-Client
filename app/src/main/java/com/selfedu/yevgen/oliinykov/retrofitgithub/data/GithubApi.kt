package com.selfedu.yevgen.oliinykov.retrofitgithub.data

import com.google.gson.GsonBuilder
import com.selfedu.yevgen.oliinykov.retrofitgithub.core.SingletonHolder
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubIssue
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubRepo
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.UserCredentials

import io.reactivex.Single
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface GithubApi {

    companion object : SingletonHolder<GithubApi, UserCredentials>({
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(GithubRepo::class.java, GithubRepoDeserializer())
                .create()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val builder = originalRequest.newBuilder().header("Authorization",
                            Credentials.basic(it.username, it.password))

                    val newRequest = builder.build()
                    chain.proceed(newRequest)
                }
                .addInterceptor(logging)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(GithubApi.ENDPOINT)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        retrofit.create(GithubApi::class.java)
//    }
    }) {
        const val ENDPOINT = "https://api.github.com"
    }

    @GET("user/repos?per_page=100")
    fun getRepos(): Single<List<GithubRepo>>

    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(@Path("owner") owner: String, @Path("repo") repository: String): Single<List<GithubIssue>>

    @POST
    fun postComment(@Url url: String, @Body body: Map<String, String>): Single<ResponseBody>

}

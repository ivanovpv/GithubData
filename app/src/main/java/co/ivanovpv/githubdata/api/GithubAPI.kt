package co.ivanovpv.githubdata.api

import co.ivanovpv.githubdata.api.models.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {
    @GET("users")
    suspend fun getGithubUsers(): Response<List<GithubUser>>

    @GET("users")
    suspend fun getGithubUsersSince(
        @Query("since") since: Int
    ): Response<List<GithubUser>>

    @GET("users/{user_login}/followers")
    suspend fun getFollowers(
        @Path("user_login") login: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 100
    ): Response<List<GithubUser>>

    @GET("users/{user_login}/following")
    suspend fun getFollowing(
        @Path("user_login") login: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 100
    ): Response<List<GithubUser>>
}

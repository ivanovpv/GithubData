package co.ivanovpv.githubdata.repo.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.ivanovpv.githubdata.api.GithubAPI
import co.ivanovpv.githubdata.api.models.ApiError
import co.ivanovpv.githubdata.api.models.ApiResult
import co.ivanovpv.githubdata.api.models.GithubUser
import co.ivanovpv.githubdata.repo.GithubUsersPagingSource
import co.ivanovpv.githubdata.repo.contract.IGithubRepo
import co.ivanovpv.githubdata.utils.ErrorUtils
import co.ivanovpv.githubdata.utils.LoggerManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GithubRepo(
    private val api: GithubAPI,
    private val gson: Gson
) : IGithubRepo {
    override suspend fun getGithubUsers(scope: CoroutineScope): Flow<PagingData<GithubUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubUsersPagingSource(api, gson)
            }
        ).flow.cachedIn(scope)
    }

    override suspend fun getFollowers(
        login: String,
        page: Int,
        perPage: Int
    ): Flow<ApiResult<List<GithubUser>>> {
        val apiResult = ApiResult<List<GithubUser>>()
        val response = api.getFollowers(login, page, perPage)
        if (response.isSuccessful) {
            apiResult.result = response.body()
        } else {
            apiResult.apiError = gson.fromJson(response.errorBody()?.string(), ApiError::class.java)
        }
        return flowOf(apiResult)
    }

    override suspend fun getFollowersCount(login: String): Flow<ApiResult<Int?>> {
        var apiResult = ApiResult<Int?>()
        val perPage = 100
        var page = 1
        var size = 0
        try {
            do {
                val response = api.getFollowers(login, ++page, perPage)
                if (response.isSuccessful)
                    size += response.body()?.size ?: 0
                else {
                    apiResult.apiError =
                        gson.fromJson(response.errorBody()?.string(), ApiError::class.java)
                    break
                }

            } while (response.body()?.size ?: 0 == perPage)
            apiResult.result = size
        } catch (exception: Exception) {
            apiResult.result = null
            LoggerManager.debugJson(this, "Error", exception)
            apiResult.apiError = ErrorUtils.handleError(exception)
        }
        return flowOf(apiResult)
    }

}
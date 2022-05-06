package co.ivanovpv.githubdata.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.ivanovpv.githubdata.api.GithubAPI
import co.ivanovpv.githubdata.api.models.ApiError
import co.ivanovpv.githubdata.api.models.GithubUser
import co.ivanovpv.githubdata.api.models.ResponseItems
import co.ivanovpv.githubdata.utils.LoggerManager
import com.google.gson.Gson

class GithubUsersPagingSource(
    private val api: GithubAPI,
    private val gson: Gson
) : PagingSource<Int, GithubUser>() {
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        var nextKey: Int?
        val prevKey = params.key
        return try {
            val response = if (prevKey == null)
                api.getGithubUsers()
            else
                api.getGithubUsersSince(prevKey)
            if (response.isSuccessful) {
                val responseItems = ResponseItems<GithubUser>(response.body()!!)
                nextKey = responseItems.items.lastOrNull()?.id
                LoadResult.Page<Int, GithubUser>(
                    data = responseItems.items,
                    nextKey = nextKey,
                    prevKey = prevKey
                )
            } else {
                val apiError = gson.fromJson(response.errorBody()?.string(), ApiError::class.java)
                LoadResult.Error<Int, GithubUser>(Exception(apiError.message))
            }
        } catch (ex: Exception) {
            LoggerManager.debug(this, "Error in datasource = ${ex.message}")
            return LoadResult.Error(ex)
        }
    }
}
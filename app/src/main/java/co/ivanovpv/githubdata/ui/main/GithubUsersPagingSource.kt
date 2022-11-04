package co.ivanovpv.githubdata.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.ivanovpv.githubdata.api.model.ApiError
import co.ivanovpv.githubdata.api.model.ResponseItems
import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.data.repository.GithubRepository
import co.ivanovpv.githubdata.model.GithubUser
import co.ivanovpv.githubdata.utils.LoggerManager
import com.google.gson.Gson

class GithubUsersPagingSource(
    private val githubRepository: GithubRepository,
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
                githubRepository.getGithubUsers()
            else
                githubRepository.getGithubUsersSince(prevKey)
            when (response) {
                is DataResultState.Success -> {
                    val responseItems = ResponseItems<GithubUser>(response.data)
                    nextKey = responseItems.items.lastOrNull()?.id
                    LoadResult.Page<Int, GithubUser>(
                        data = responseItems.items,
                        nextKey = nextKey,
                        prevKey = prevKey
                    )
                }
                is DataResultState.Failure -> {
                    val apiError = gson.fromJson(response.reason, ApiError::class.java)
                    LoadResult.Error<Int, GithubUser>(Exception(apiError.message))
                }
            }
        } catch (ex: Exception) {
            LoggerManager.debug(this, "Error in datasource = ${ex.message}")
            return LoadResult.Error(ex)
        }
    }
}
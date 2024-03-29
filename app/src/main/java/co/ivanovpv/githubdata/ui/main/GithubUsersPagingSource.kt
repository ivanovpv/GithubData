package co.ivanovpv.githubdata.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.ivanovpv.githubdata.api.model.ResponseItems
import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.domain.repository.GithubRepository
import co.ivanovpv.githubdata.domain.model.GithubUser
import co.ivanovpv.githubdata.utils.LoggerManager

class GithubUsersPagingSource(
    private val githubRepository: GithubRepository,
) : PagingSource<Int, GithubUser>() {
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        val nextKey: Int?
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
                is DataResultState.Failure  -> {
                    LoadResult.Error<Int, GithubUser>(Exception(response.message))
                }
            }
        } catch (ex: Exception) {
            LoggerManager.debug(this, "Error in datasource = ${ex.message}")
            return LoadResult.Error(ex)
        }
    }
}
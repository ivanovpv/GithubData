package co.ivanovpv.githubdata.repo.contract

import androidx.paging.PagingData
import co.ivanovpv.githubdata.api.models.ApiResult
import co.ivanovpv.githubdata.api.models.GithubUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface IGithubRepo {
    suspend fun getGithubUsers(scope: CoroutineScope): Flow<PagingData<GithubUser>>
    suspend fun getFollowers(
        login: String,
        page: Int,
        perPage: Int
    ): Flow<ApiResult<List<GithubUser>>>

    suspend fun getFollowersCount(login: String): Flow<ApiResult<Int?>>
}
package co.ivanovpv.githubdata.data.repository

import androidx.paging.PagingData
import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.model.GithubUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
	suspend fun getGithubUsers(): DataResultState<List<GithubUser>>
	suspend fun getGithubUsersSince(since: Int): DataResultState<List<GithubUser>>
	suspend fun getPagedGithubUsers(scope: CoroutineScope): Flow<PagingData<GithubUser>>
	suspend fun getFollowers(
		login: String,
		page: Int,
		perPage: Int,
	): Flow<DataResultState<List<GithubUser>>>

}
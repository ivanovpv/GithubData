package co.ivanovpv.githubdata.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.ivanovpv.githubdata.api.GithubService
import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.data.datasource.FailureReason
import co.ivanovpv.githubdata.data.datasource.GithubDataSource
import co.ivanovpv.githubdata.data.toDomain
import co.ivanovpv.githubdata.domain.repository.GithubRepository
import co.ivanovpv.githubdata.ui.main.GithubUsersPagingSource
import co.ivanovpv.githubdata.domain.model.GithubUser
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
	private val githubDataSource: GithubDataSource,
	private val githubService: GithubService,
	private val gson: Gson,
	): GithubRepository {

	override suspend fun getGithubUsers(): DataResultState<List<GithubUser>, FailureReason> {
		/*
		val result = githubDataSource.getGithubUsers().convert {
			it.map { userDto ->
				userDto.toDomain()
			}
		}
		return result*/
		return githubService.getGithubUsers().convert {
			it.map {userDto ->
				userDto.toDomain()
			}
		}

	}

	override suspend fun getGithubUsersSince(since: Int): DataResultState<List<GithubUser>, FailureReason> {
		return githubDataSource.getGithubUsersSince(since).convert {
			it.map { userDto ->
				userDto.toDomain()
			}
		}
	}

	override suspend fun getPagedGithubUsers(scope: CoroutineScope): Flow<PagingData<GithubUser>> {
		return Pager(
			config = PagingConfig(
				pageSize = 25,
				enablePlaceholders = false
			),
			pagingSourceFactory = {
				val pagingSource = GithubUsersPagingSource(this, gson)
				pagingSource
			}
		).flow.cachedIn(scope)
	}

	override suspend fun getFollowers(
		login: String,
		page: Int,
		perPage: Int,
	): Flow<DataResultState<List<GithubUser>, FailureReason>> {
		val result = githubDataSource.getFollowers(login, page, perPage).convert {
			it.map { userDto ->
				userDto.toDomain()
			}
		}
		return flowOf(result)
	}
}
package co.ivanovpv.githubdata.data.datasource

import co.ivanovpv.githubdata.api.GithubAPI
import co.ivanovpv.githubdata.api.model.GithubUserDTO
import javax.inject.Inject

class GithubDataSourceImpl @Inject constructor(
	private val githubAPI: GithubAPI,
): GithubDataSource, BasicRemoteDataSource() {
	override suspend fun getGithubUsers(): DataResultState<List<GithubUserDTO>?> {
		return getBodyResult {
			githubAPI.getGithubUsers()
		}
	}

	override suspend fun getGithubUsersSince(since: Int): DataResultState<List<GithubUserDTO>?> {
		return getBodyResult {
			githubAPI.getGithubUsersSince(since)
		}
	}

	override suspend fun getFollowers(login: String, page: Int, perPage: Int):
		DataResultState<List<GithubUserDTO>?> {
		return getBodyResult {
			githubAPI.getFollowers(login, page, perPage)
		}
	}
}
package co.ivanovpv.githubdata.data.datasource

import co.ivanovpv.githubdata.api.GithubAPI
import co.ivanovpv.githubdata.api.model.GithubUserDto
import javax.inject.Inject

class GithubDataSourceImpl @Inject constructor(
	private val githubAPI: GithubAPI,
): GithubDataSource, BasicRemoteDataSource() {
	override suspend fun getGithubUsers(): DataResultState<List<GithubUserDto>, FailureReason> {
		val result = getBodyResult {
			githubAPI.getGithubUsers()
		}
		return result
	}

	override suspend fun getGithubUsersSince(since: Int): DataResultState<List<GithubUserDto>, FailureReason> {
		return getBodyResult {
			githubAPI.getGithubUsersSince(since)
		}
	}

	override suspend fun getFollowers(login: String, page: Int, perPage: Int):
		DataResultState<List<GithubUserDto>, FailureReason> {
		return getBodyResult {
			githubAPI.getFollowers(login, page, perPage)
		}
	}
}
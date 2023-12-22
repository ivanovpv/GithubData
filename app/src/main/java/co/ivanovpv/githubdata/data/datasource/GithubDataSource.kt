package co.ivanovpv.githubdata.data.datasource

import co.ivanovpv.githubdata.api.model.GithubUserDto

interface GithubDataSource {
	suspend fun getGithubUsers(): DataResultState<List<GithubUserDto>, FailureReason>
	suspend fun getGithubUsersSince(since: Int): DataResultState<List<GithubUserDto>, FailureReason>
	suspend fun getFollowers(login: String, page: Int, perPage: Int):
		DataResultState<List<GithubUserDto>, FailureReason>
}
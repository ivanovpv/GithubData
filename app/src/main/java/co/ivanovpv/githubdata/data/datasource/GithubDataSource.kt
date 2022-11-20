package co.ivanovpv.githubdata.data.datasource

import co.ivanovpv.githubdata.api.model.GithubUserDTO

interface GithubDataSource {
	suspend fun getGithubUsers(): DataResultState<List<GithubUserDTO>, FailureReason>
	suspend fun getGithubUsersSince(since: Int): DataResultState<List<GithubUserDTO>, FailureReason>
	suspend fun getFollowers(login: String, page: Int, perPage: Int):
		DataResultState<List<GithubUserDTO>, FailureReason>
}
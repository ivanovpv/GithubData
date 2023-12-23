package co.ivanovpv.githubdata.api

import co.ivanovpv.githubdata.api.model.GithubUserDto
import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.data.datasource.FailureReason

interface GithubService {
    suspend fun getGithubUsers(): DataResultState<List<GithubUserDto>, FailureReason>
}
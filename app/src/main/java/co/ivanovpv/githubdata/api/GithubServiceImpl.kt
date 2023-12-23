package co.ivanovpv.githubdata.api

import co.ivanovpv.githubdata.api.model.GithubUserDto
import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.data.datasource.FailureReason
import co.ivanovpv.githubdata.data.datasource.TextFailureReason
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class GithubServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): GithubService {
    override suspend fun getGithubUsers(): DataResultState<List<GithubUserDto>, FailureReason> {
        return try {
            val httpResponse = httpClient.get("users")
            DataResultState.Success(httpResponse.body<List<GithubUserDto>>())
        }
        catch(ex: Exception) {
            DataResultState.Failure(TextFailureReason(ex.message ?: ""))
        }
    }

}
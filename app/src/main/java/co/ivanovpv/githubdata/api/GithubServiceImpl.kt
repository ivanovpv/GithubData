package co.ivanovpv.githubdata.api

import co.ivanovpv.githubdata.api.model.GithubUserDto
import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.data.datasource.FailureReason
import co.ivanovpv.githubdata.data.datasource.RemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class GithubServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): GithubService, RemoteDataSource() {
    override suspend fun getGithubUsers(): DataResultState<List<GithubUserDto>, FailureReason> {
        return ktorBodyResult<List<GithubUserDto>> {
            httpClient.get("users")
        }
    }

    override suspend fun getGithubUsersSince(since: Int): DataResultState<List<GithubUserDto>, FailureReason> {
        return ktorBodyResult<List<GithubUserDto>> {
            httpClient.get("users") {
                parameter("since", since)
            }
        }
    }

    //@GET("users/{user_login}/following")
    override suspend fun getFollowers(
        login: String,
        page: Int,
        perPage: Int
    ): DataResultState<List<GithubUserDto>, FailureReason> {
        return ktorBodyResult<List<GithubUserDto>> {
            httpClient.get("users/$login/following")
        }
    }
}
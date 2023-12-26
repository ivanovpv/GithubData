package co.ivanovpv.githubdata.data.datasource

import co.ivanovpv.githubdata.api.model.GithubUserDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import javax.inject.Inject

class GithubDataSourceImpl @Inject constructor(
	private val httpClient: HttpClient,
): GithubDataSource, BasicRemoteDataSource() {

	override suspend fun getGithubUsers(): DataResultState<List<GithubUserDto>> {
		val result = ktorBodyResult<List<GithubUserDto>> {
			httpClient.get("users")
		}
		return result
	}

	override suspend fun getGithubUsersSince(since: Int): DataResultState<List<GithubUserDto>> {
		return ktorBodyResult<List<GithubUserDto>> {
			httpClient.get("users") {
				parameter("since", since)
			}
		}
	}

	//users/{user_login}/following
	override suspend fun getFollowers(login: String, page: Int, perPage: Int):
		DataResultState<List<GithubUserDto>> {
		return ktorBodyResult {
			httpClient.get("users") {
				url {
					appendPathSegments(login)
					appendPathSegments("following")
					parameters.append("page", page.toString())
					parameters.append("per_page", perPage.toString())
				}
			}
		}
	}
}
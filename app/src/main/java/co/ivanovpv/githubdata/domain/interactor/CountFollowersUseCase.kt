package co.ivanovpv.githubdata.domain.interactor

import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.data.datasource.FailureReason
import co.ivanovpv.githubdata.domain.model.GithubUser
import co.ivanovpv.githubdata.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountFollowersUseCase @Inject constructor(private val githubRepo: GithubRepository) {
	suspend operator fun invoke(login: String,
		page: Int,
		perPage: Int,
	): Flow<DataResultState<List<GithubUser>, FailureReason>> {
		return githubRepo.getFollowers(login, page, perPage)
	}
}
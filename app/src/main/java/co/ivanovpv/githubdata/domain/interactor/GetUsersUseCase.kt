package co.ivanovpv.githubdata.domain.interactor

import androidx.paging.PagingData
import co.ivanovpv.githubdata.domain.repository.GithubRepository
import co.ivanovpv.githubdata.domain.model.GithubUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val githubRepo: GithubRepository) {
	suspend operator fun invoke(scope: CoroutineScope): Flow<PagingData<GithubUser>> {
		return githubRepo.getPagedGithubUsers(scope)
	}
}
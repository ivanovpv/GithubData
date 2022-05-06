package co.ivanovpv.githubdata.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import co.ivanovpv.githubdata.api.models.ApiError
import co.ivanovpv.githubdata.api.models.ApiResult
import co.ivanovpv.githubdata.api.models.GithubUser
import co.ivanovpv.githubdata.repo.contract.IGithubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepo: IGithubRepo
) : ViewModel() {

    private val _followersCountState = MutableStateFlow<ApiResult<Int?>>(ApiResult.Loading())
    val followersCountState = _followersCountState.asStateFlow()

    private val _errorState = MutableSharedFlow<ApiError>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val errorState = _errorState.asSharedFlow()

    suspend fun getUsers(): Flow<PagingData<GithubUser>> {
        return githubRepo.getGithubUsers(viewModelScope)
    }

    fun countFollowers(login: String?) {
        if (login == null)
            return
        viewModelScope.launch {
            _followersCountState.value = ApiResult.Loading()
            githubRepo.getFollowersCount(login).collect { apiResult ->
                if (apiResult.isSuccess()) {
                    _followersCountState.value = apiResult
                }
                if (apiResult.isError()) {
                    _errorState.emit(apiResult.apiError!!)
                }
            }
        }
    }
}
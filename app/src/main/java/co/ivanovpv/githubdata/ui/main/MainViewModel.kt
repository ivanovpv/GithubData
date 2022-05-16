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
        var page = 1
        var count = 0
        val perPage = 50
        var size = perPage
        if (login == null)
            return
        viewModelScope.launch {
            _followersCountState.value = ApiResult.Loading()
            do {
                val apiResult = ApiResult<Int?>()
                githubRepo.getFollowers(login, ++page, perPage).collect {
                    if(it.isSuccess()) {
                        size = it.result?.size ?: 0
                        count += size
                        apiResult.result = count
                        _followersCountState.value = apiResult
                    }
                    if (it.isError()) {
                        size = 0
                        _errorState.emit(it.apiError!!)
                    }
                }
            } while (size == perPage)
            _followersCountState.value = ApiResult.Finished(count)
        }
    }
}
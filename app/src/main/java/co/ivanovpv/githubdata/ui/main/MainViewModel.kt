package co.ivanovpv.githubdata.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import co.ivanovpv.githubdata.domain.interactor.GetUsersUseCase
import co.ivanovpv.githubdata.domain.model.GithubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {

    suspend fun getUsers(): Flow<PagingData<GithubUser>> {
        return getUsersUseCase(viewModelScope)
    }

}
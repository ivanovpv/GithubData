package co.ivanovpv.githubdata.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ivanovpv.githubdata.data.datasource.DataResultState
import co.ivanovpv.githubdata.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
	private val githubRepo: GithubRepository
) : ViewModel() {
	private val _followersCountState = MutableStateFlow<CountState>(CountState.Loading)
	val followersCountState = _followersCountState.asStateFlow()

	fun countFollowers(login: String?) {
		var page = 1
		var count = 0
		val perPage = 50
		var size = perPage
		if (login == null)
			return
		viewModelScope.launch {
			_followersCountState.value = CountState.Loading
			do {
				githubRepo.getFollowers(login, ++page, perPage).collect {
					when (it) {
						is DataResultState.Success -> {
							size = it.data.size
							count += size
							_followersCountState.value = CountState.Success(count)
						}
						is DataResultState.Failure -> {
							size = 0
							_followersCountState.value = CountState.Error(it.reason)
						}
					}
				}
			} while (size == perPage)
			_followersCountState.value = CountState.Finished(count)
		}
	}

}
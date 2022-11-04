package co.ivanovpv.githubdata.ui.info

sealed class CountState {
	class Success(val count: Int): CountState()
	class Error(val reason: String): CountState()
	object Loading: CountState()
	class Finished(val count: Int): CountState()
}

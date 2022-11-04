package co.ivanovpv.githubdata.data.datasource

sealed class DataResultState<out T> {
	data class Success<T>(val data: T) : DataResultState<T>()
	data class Failure(val reason: String) : DataResultState<Nothing>()

	fun <R> convert(convert: (T) -> R): DataResultState<R> = when (this) {
		is Failure -> this
		is Success -> Success(convert(this.data))
	}

	fun isSuccess(): Boolean {
		return this is Success
	}

	fun isError(): Boolean {
		return this is Failure
	}
}

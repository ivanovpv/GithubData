package co.ivanovpv.githubdata.data.datasource

import co.ivanovpv.githubdata.api.model.ApiError


sealed class DataResultState<out T, out F> {
	data class Success<T>(val data: T) : DataResultState<T, Nothing>()
	data class Failure<F : FailureReason>(val failureReason: F) : DataResultState<Nothing, F>() {
		val reason get() = failureReason.message
	}

	fun <R> convert(converter: (T) -> R): DataResultState<R, F> = when (this) {
		is Failure -> this
		is Success -> Success(converter(this.data))
	}

	fun isSuccess(): Boolean {
		return this is Success
	}

	fun isError(): Boolean {
		return this is Failure
	}
}

sealed class FailureReason(
	open val message: String,
	open val errorCode: Int? = null,
	val stackTrace: String? = null,
)

class TextFailureReason(override val message: String): FailureReason(message)

class ApiErrorFailureReason(override val message: String, val documentationUrl: String?, override
val errorCode: Int?):
	FailureReason(message, errorCode) {
		constructor(apiError: ApiError, errorCode: Int? = null):
			this(message = apiError.message, documentationUrl = apiError.documentationUrl,
				errorCode = errorCode)
	}


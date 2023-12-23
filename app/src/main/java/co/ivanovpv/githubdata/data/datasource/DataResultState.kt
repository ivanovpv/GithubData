package co.ivanovpv.githubdata.data.datasource

import java.lang.Exception

sealed class DataResultState<out T> {

	data class Success<T>(val data: T) : DataResultState<T>()

	open class Failure<T>(open val message: String, val details: List<String>) : DataResultState<T>() {
		constructor(exception: Exception): this(exception.message ?: "", exception.stackTraceToString().split("\n"))
	}

	data class ApiFailure<T>(
		override val message: String,
		val errorCode: Int,
		val documentationUrl: String?): Failure<T>(message, listOf())

	fun <R> convert(converter: (T) -> R): DataResultState<R> = when (this) {
		is ApiFailure -> ApiFailure(this.message, this.errorCode, this.documentationUrl)
		is Failure -> Failure(this.message, this.details)
		is Success -> Success(converter(this.data))
	}

	fun isSuccess(): Boolean {
		return this is Success
	}

	fun isError(): Boolean {
		return this is Failure
	}
}

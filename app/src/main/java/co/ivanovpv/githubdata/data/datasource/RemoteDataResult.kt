package co.ivanovpv.githubdata.data.datasource

import co.ivanovpv.githubdata.api.model.ApiError
import com.google.gson.Gson
import retrofit2.Response

abstract class RemoteDataSource {
	protected suspend fun <T> getBodyResult(call: suspend () -> Response<T>): DataResultState<T, FailureReason> {
		val result = try {
			val response = call()
			val body = response.body()
			when {
				response.isSuccessful -> {
					when {
						body != null -> DataResultState.Success(body)
						else -> DataResultState.Failure(TextFailureReason("Server returned empty " +
							"response"))
					}
				}
				else -> DataResultState.Failure(parseResponse(response))
			}
		} catch (e: Exception) {
			error(e.message ?: e.toString())
		}
		return result
	}

	private fun <T> error(message: String): DataResultState<T, FailureReason> {
		return DataResultState.Failure(TextFailureReason(message))
	}

	private fun <T> parseResponse(response: Response<T>): ApiErrorFailureReason {
		val reader = response.errorBody()?.charStream()
		val apiError = reader?.use {
			try {
				//пытаемся сначала распарсить как ApiError
				Gson().fromJson(it, ApiError::class.java)
			}
			catch(ex: Exception) {
				//если не получается - значит это Exception, который пихаем как ApiError
				val lines = it.readLines()
				val message = if(lines.isEmpty()) "" else lines[0] //первая строка описание Exception
				ApiError(message)
			}
		}

		return ApiErrorFailureReason(
			apiError = apiError ?: ApiError(message = "", documentationUrl = null),
			errorCode = response.code())
	}

}
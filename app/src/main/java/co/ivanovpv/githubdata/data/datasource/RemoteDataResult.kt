package co.ivanovpv.githubdata.data.datasource

import android.util.Log
import co.ivanovpv.githubdata.api.model.ApiError
import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import retrofit2.Response

abstract class RemoteDataSource {

	protected suspend inline fun <reified T> ktorBodyResult(call: suspend () -> HttpResponse): DataResultState<T> {
		return try {
			val response = call()
			Log.i("ivanovpv", response.toString())
			when (response.status.value) {
				in (200..299) -> {
					DataResultState.Success(response.body())
				}

				else -> {
					val text = response.bodyAsText()
					parseResponse(text, response)
				}
			}
		}
		catch(ex: Exception) {
			error(ex)
		}
	}

	protected suspend fun <T> getBodyResult(call: suspend () -> Response<T>): DataResultState<T> {
		val result = try {
			val response = call()
			val body = response.body()
			when {
				response.isSuccessful -> {
					when {
						body != null -> DataResultState.Success(body)
						else -> DataResultState.Failure("Server returned empty response", listOf())
					}
				}
				else -> parseResponse(response)
			}
		} catch (e: Exception) {
			error(e.message ?: e.toString())
		}
		return result
	}

	private fun <T> error(message: String): DataResultState<T> {
		return DataResultState.Failure<T>(message, listOf())
	}

	private fun <T> parseResponse(response: Response<T>): DataResultState.Failure<T> {
		val reader = response.errorBody()?.charStream()
		return reader?.let {
			try {
				//trying to parse as ApiError
				val result = Gson().fromJson(it, ApiError::class.java)
				DataResultState.ApiFailure<T>(
					result.message,
					response.code(),
					result.documentationUrl
				)
			} catch (ex: Exception) {
				//otherwise it's just exception
				val lines = it.readLines()
				val message = if (lines.isEmpty()) "" else lines[0] //first line of exception
				DataResultState.Failure<T>(message, lines)
			}
		}?: DataResultState.Failure<T>("", listOf()) //unspecified error
	}

	fun <T> parseResponse(body: String, response: HttpResponse): DataResultState.Failure<T> {
		return try {
			//trying to parse as ApiError
			val result = Gson().fromJson(body, ApiError::class.java)
			DataResultState.ApiFailure(
				result.message,
				response.status.value,
				result.documentationUrl
			)
		} catch (ex: Exception) {
			//otherwise it's just exception
			val lines = body.split("\n")
			val message = if (lines.isEmpty()) "" else lines[0] //first line of exception
			DataResultState.Failure(message, lines)
		}
	}

}
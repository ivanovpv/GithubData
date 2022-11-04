package co.ivanovpv.githubdata.data.datasource

import retrofit2.Response

abstract class RemoteDataSource {
	protected suspend fun <T> getBodyResult(call: suspend () -> Response<T>): DataResultState<T> {
		return try {
			val response = call()
			val body= response.body()
			when {
				response.isSuccessful -> {
					when {
						body != null -> DataResultState.Success(body)
						else -> DataResultState.Failure("Server returned empty response")
					}
				}
				else -> error("${response.code()} ${response.message()}")
			}
		} catch (e: Exception) {
			error(e.message ?: e.toString())
		}
	}

	private fun <T> error(message: String): DataResultState<T> {
		return DataResultState.Failure(message)
	}
}
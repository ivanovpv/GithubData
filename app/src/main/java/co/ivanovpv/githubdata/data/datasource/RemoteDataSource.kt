package co.ivanovpv.githubdata.data.datasource

import android.util.Log
import co.ivanovpv.githubdata.api.model.ApiError
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import kotlinx.serialization.json.Json

abstract class RemoteDataSource {

	protected suspend inline fun <reified T> ktorBodyResult(call: () -> HttpResponse): DataResultState<T, FailureReason> {
		return try {
			val response = call()
			when (response.status.value) {
				in (200..299) -> {
					DataResultState.Success(response.body())
				}
				404 -> {
					error(message = "Не найден адрес: ${response.request.url}")
				}

				else -> {
					val apiError = try {
						Json.decodeFromString<ApiError>(response.bodyAsText())
					} catch(ex: Exception) {
						ApiError(message = response.bodyAsText()) //вернулся нестандарный ответ сервера не в виде ErrorResponse
					}
					error(message = apiError.message)
				}
			}
		}
		catch(ex: SocketTimeoutException) {
			Log.w("ktor","Socket timeout exception: ${ex.stackTraceToString()}", ex)
			val message = ex.message ?: "Диагностика отсутствует"
			error(message = message)
		}
		catch(ex: ConnectTimeoutException) {
			Log.w("ktor","Connect timeout exception: ${ex.stackTraceToString()}", ex)
			val message = ex.message ?: "Диагностика отсутствует"
			error(message = message)
		}
		catch(ex: HttpRequestTimeoutException) {
			Log.w("ktor","Request timeout exception: ${ex.stackTraceToString()}", ex)
			val message = ex.message ?: "Диагностика отсутствует"
			error(message = message)
		}
		catch(ex: Exception) {
			Log.w("ktor","Exception parsing server Response: ${ex.stackTraceToString()}", ex)
			val message = ex.message ?: "Диагностика отсутствует"
			error(message = message)
		}
	}

	fun <T> error(message: String): DataResultState<T, FailureReason> {
		return DataResultState.Failure(TextFailureReason(message))
	}

}
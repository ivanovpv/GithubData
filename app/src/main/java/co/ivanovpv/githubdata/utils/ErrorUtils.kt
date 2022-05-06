package co.ivanovpv.githubdata.utils

import co.ivanovpv.githubdata.api.models.ApiError
import com.google.gson.Gson
import retrofit2.HttpException

object ErrorUtils {
    const val STATUS_EXCEPTION = "600"

    fun handleError(exception: Throwable): ApiError {
        return when (exception) {
            is HttpException -> {
                try {
                    val error = exception.response()?.errorBody()?.charStream()
                    return Gson().fromJson(error, ApiError::class.java)
                } catch (e: Exception) {
                    exception.printStackTrace()
                    return convertToApiError(exception)
                }
            }
            else -> {
                exception.printStackTrace()
                convertToApiError(exception)
            }
        }
    }

    fun convertToApiError(exception: Throwable): ApiError {
        val apiError = ApiError().apply {
            message = exception.message
            documentationUrl = ""
        }
        return apiError
    }
}
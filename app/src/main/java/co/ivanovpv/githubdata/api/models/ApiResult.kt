package co.ivanovpv.githubdata.api.models

open class ApiResult<T>(
    var result: T? = null,
    var apiError: ApiError? = null
) {
    class Loading<T> : ApiResult<T>()

    fun isSuccess(): Boolean {
        return (result != null)
    }

    fun isLoading(): Boolean {
        return this is Loading
    }

    fun isError(): Boolean {
        return apiError != null
    }
}

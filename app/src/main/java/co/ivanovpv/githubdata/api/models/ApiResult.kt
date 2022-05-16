package co.ivanovpv.githubdata.api.models

open class ApiResult<T>(
    var result: T? = null,
    var apiError: ApiError? = null
) {
    class Loading<T> : ApiResult<T>()
    class Finished<T>(result: T?) : ApiResult<T>(result)

    fun isSuccess(): Boolean {
        return (result != null)
    }

    fun isLoading(): Boolean {
        return this is Loading
    }

    fun isFinished(): Boolean {
        return this is Finished
    }


    fun isError(): Boolean {
        return apiError != null
    }
}

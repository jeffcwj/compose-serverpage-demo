package compose.serverpage.demo.data.net

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

fun <T, R> Resource<T>.map(
    invoke: (data: T) -> R
): Resource<R> {
    return when (this) {
        is Resource.Success -> {
            Resource.Success(
                invoke(this.data)
            )
        }
        is Resource.Failure -> this
        is Resource.Loading -> this
    }
}

fun <T> Resource<T>.onSuccess(
    invoke: (data: T) -> Unit
): Resource<T> {
    when (this) {
        is Resource.Success -> {
            invoke(this.data)
        }
        else -> {}
    }
    return this
}

fun <T> Resource<T>.onFailure(invoke: (isNetworkError: Boolean,
                                       errorCode: Int?,
                                       errorBody: ResponseBody?) -> Unit): Resource<T> {
    when (this) {
        is Resource.Failure -> invoke(this.isNetworkError, this.errorCode, this.errorBody)
        else -> {}
    }
    return this
}
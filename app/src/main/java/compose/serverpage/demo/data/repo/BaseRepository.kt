package compose.serverpage.demo.data.repo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import compose.serverpage.demo.data.net.Resource

abstract class BaseRepository {

    companion object {
        private const val TAG = "BaseRepository"
    }

    suspend fun <T> safeApiCall(
        maxRetry: Int = 3,
        delayMs: Long = 1000,
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            var currentRetry = 0
            var result: Resource<T>

            while (true) {
                try {
                    result = Resource.Success(apiCall.invoke())
                    break
                } catch (throwable: Throwable) {
                    Log.d(TAG, "safeApiCall: ${throwable.message}")
                    if (throwable is HttpException && throwable.code() == 404) {
                        result = Resource.Failure(false, throwable.code(),
                            throwable.response()?.errorBody()
                        )
                        break
                    }
                    if (currentRetry >= maxRetry) {
                        result = when (throwable) {
                            is HttpException -> {
                                Resource.Failure(false, throwable.code(),
                                    throwable.response()?.errorBody()
                                )
                            }
                            else -> {
                                Resource.Failure(true, null, null)
                            }
                        }
                        break
                    }
                    currentRetry++
                    delay(delayMs * currentRetry)
                }
            }
            result
        }
    }
}
package compose.serverpage.demo.data.net

import android.util.Log
import compose.serverpage.demo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SampRemoteDataSource @Inject constructor() {


    fun <Api> buildApi(
        api: Class<Api>
    ) : Api {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("WpRetrofitLog", message)
        }
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        }

        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor {
                            chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://alynsampmobile.pro")
            .build()
            .create(api)
    }
}
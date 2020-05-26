package cn.sh.changxing.paulweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object PaulWeatherNetwork {
    private val placeService = ServiceCreator.create(PlaceService::class.java)
    suspend fun searchPlace(query:String) = placeService.searchPlace(query).await()
    private suspend fun <T> Call<T>.await():T {
        return suspendCoroutine {continuation ->  
            enqueue(object :Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    }
                    else {
                        continuation.resumeWithException(RuntimeException("Response body is null"))
                    }
                }

            })
        }
    }
}
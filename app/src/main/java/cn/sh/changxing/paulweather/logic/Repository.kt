package cn.sh.changxing.paulweather.logic

import android.util.Log
import androidx.lifecycle.liveData
import cn.sh.changxing.paulweather.logic.dao.PlaceDao
import cn.sh.changxing.paulweather.logic.model.Place
import cn.sh.changxing.paulweather.logic.model.Weather
import cn.sh.changxing.paulweather.logic.network.PaulWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {
    const val TAG = "paul_Repository"

    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavePlace() = PlaceDao.getSavePlace()
    fun isSavedPlace() = PlaceDao.isSavedPlace()
//    fun searchPlaces(query:String) = liveData(Dispatchers.IO) {
//        val result = try {
//            val placeResponse = PaulWeatherNetwork.searchPlace(query)
//            if (placeResponse.status == "ok") {
//                val places = placeResponse.places
//                Result.success(places)
//            } else {
//                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
//            }
//        } catch (e:Exception) {
//            Result.failure<List<Place>>(e)
//        }
//        emit(result)
//    }
//
//    fun refreshWeather(lng:String, lat:String) = liveData(Dispatchers.IO) {
//        val result = try {
//            coroutineScope {
//                val deferredRealtime = async {
//                    PaulWeatherNetwork.getRealtimeWeather(lng, lat)
//                }
//                val deferredDaily = async {
//                    PaulWeatherNetwork.getDailyWeather(lng, lat)
//                }
//                val realtimeResponse = deferredRealtime.await()
//                val dailyResponse = deferredDaily.await()
//                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
//                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
//                    Result.success(weather)
//                } else {
//                    Result.failure(java.lang.RuntimeException("realtime response status is ${realtimeResponse.status}" +
//                            " daily response status is ${dailyResponse.status}"))
//                }
//            }
//        } catch (e:java.lang.Exception) {
//            Result.failure<Weather>(e)
//        }
//        emit(result)
//    }

    // 以下代码是对上面注释代码的try catch统一封装

    fun searchPlaces(query:String) = fire(Dispatchers.IO) {
        val placeResponse = PaulWeatherNetwork.searchPlace(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng:String, lat:String) = fire(Dispatchers.IO) {
        Log.d(TAG, "lng is $lng---lat is $lat")
        coroutineScope {
            val deferredRealtime = async {
                PaulWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                PaulWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(java.lang.RuntimeException("realtime response status is ${realtimeResponse.status}" +
                        " daily response status is ${dailyResponse.status}"))
            }
        }
    }

    private fun <T> fire(context : CoroutineContext, block:suspend ()->Result<T>) = liveData<Result<T>>(context) {
        val result = try {
            block()
        } catch (e:java.lang.Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }
}
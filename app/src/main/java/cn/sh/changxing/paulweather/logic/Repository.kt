package cn.sh.changxing.paulweather.logic

import androidx.lifecycle.liveData
import cn.sh.changxing.paulweather.logic.model.Place
import cn.sh.changxing.paulweather.logic.network.PaulWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlaces(query:String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = PaulWeatherNetwork.searchPlace(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e:Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}
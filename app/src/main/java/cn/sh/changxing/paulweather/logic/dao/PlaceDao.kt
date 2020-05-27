package cn.sh.changxing.paulweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import cn.sh.changxing.paulweather.PaulWeatherApplication
import cn.sh.changxing.paulweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {
    private fun sharedPreferances() = PaulWeatherApplication.context.getSharedPreferences("paul_weather", Context.MODE_PRIVATE)

    fun savePlace(place: Place) {
        sharedPreferances().edit {
            putString("place", Gson().toJson(place))
        }
    }
    fun getSavePlace():Place {
        val placeJson = sharedPreferances().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }
    fun isSavedPlace() = sharedPreferances().contains("place")

}
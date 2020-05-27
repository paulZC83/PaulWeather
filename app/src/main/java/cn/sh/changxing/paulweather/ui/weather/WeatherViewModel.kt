package cn.sh.changxing.paulweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.sh.changxing.paulweather.logic.Repository
import cn.sh.changxing.paulweather.logic.model.Location

class WeatherViewModel:ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""
    val weatherLiveData = Transformations.switchMap(locationLiveData){
        location->
        Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng:String, lat:String) {
        locationLiveData.value = Location(lng, lat)
    }
}
package cn.sh.changxing.paulweather.logic.network

import cn.sh.changxing.paulweather.PaulWeatherApplication
import cn.sh.changxing.paulweather.logic.model.DailyResponse
import cn.sh.changxing.paulweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("v2.5/${PaulWeatherApplication.TOKEN}/{lat},{lng}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng :String, @Path("lat") lat:String) :Call<RealtimeResponse>

    @GET("v2.5/${PaulWeatherApplication.TOKEN}/{lat},{lng}/daily.json")
    fun getDailyWeather(@Path("lng") lng :String, @Path("lat") lat:String):Call<DailyResponse>
}
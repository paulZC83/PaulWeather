package cn.sh.changxing.paulweather.logic.network

import cn.sh.changxing.paulweather.PaulWeatherApplication
import cn.sh.changxing.paulweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${PaulWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query") query: String):Call<PlaceResponse>
}
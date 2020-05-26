package cn.sh.changxing.paulweather.logic.model

import com.google.gson.annotations.SerializedName

data class Location(val lat:String, val lng:String)

// SerializedName建立映射关系
data class Place(val name:String, val location:Location, @SerializedName("formatted_address") val address:String)

data class PlaceResponse(val status:String, val places:List<Place>)
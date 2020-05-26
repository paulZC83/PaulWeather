package cn.sh.changxing.paulweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.sh.changxing.paulweather.logic.Repository
import cn.sh.changxing.paulweather.logic.model.Place

class PlaceViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData) {
        query ->
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query:String) {
        searchLiveData.value = query
    }
}
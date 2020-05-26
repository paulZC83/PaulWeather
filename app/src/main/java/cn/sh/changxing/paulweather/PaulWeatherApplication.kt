package cn.sh.changxing.paulweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class PaulWeatherApplication: Application() {
    companion object{
        const val TOKEN = "1Nm5pLwyY0MkU4Yw"
        @SuppressLint("StaticFiledLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
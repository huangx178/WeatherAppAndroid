package website.huangx.weather

import website.huangx.weather.networking.model.CityWeather

interface MainInterface {
    interface View{
        fun onLoadCityWeatherStarted()
        fun onLoadCityWeatherCompleted()
        fun onLoadCityWeatherSuccess(cityWeather: CityWeather)
        fun onLoadCityWeatherError(error:Throwable)
    }

    interface Presenter{
        fun loadCityWeather(cityName:String)
        fun subscribe(view:View)
        fun unsubscribe(view:View)
        fun onDestroy()
    }
}
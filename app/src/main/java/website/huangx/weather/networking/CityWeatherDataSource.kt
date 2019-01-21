package website.huangx.weather

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import website.huangx.weather.networking.model.CityWeather

interface CityWeatherDataSource{
    @GET("/data/2.5/weather?appid=7c16b5dd0cf908a7689d31dce972563d")
    fun getCityWeather(@Query("q") cityName:String):Observable<CityWeather>
}
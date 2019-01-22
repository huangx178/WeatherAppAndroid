package website.huangx.weather.networking

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import website.huangx.weather.CityWeatherDataSource
import website.huangx.weather.networking.model.CityWeather

class CityWeatherRemoteDataSource : CityWeatherDataSource{
    private val baseUrl = "http://api.openweathermap.org"
    private val api:CityWeatherDataSource

    constructor(){
        val retorfit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        api = retorfit.create(CityWeatherDataSource::class.java)
    }

    override fun getCityWeather(cityName: String): Observable<CityWeather> {
        return api.getCityWeather(cityName)
    }
}
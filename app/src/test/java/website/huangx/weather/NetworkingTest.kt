package website.huangx.weather

import com.google.gson.Gson
import org.junit.Test
import website.huangx.weather.model.CityWeather

class NetworkingTest {
    @Test
    fun canDecodeJson(){
        val gson = Gson();

        val londonWeather = gson.fromJson<CityWeather>("{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":280.32,\"pressure\":1012,\"humidity\":81,\"temp_min\":279.15,\"temp_max\":281.15},\"visibility\":10000,\"wind\":{\"speed\":4.1,\"deg\":80},\"clouds\":{\"all\":90},\"dt\":1485789600,\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0103,\"country\":\"GB\",\"sunrise\":1485762037,\"sunset\":1485794875},\"id\":2643743,\"name\":\"London\",\"cod\":200}", CityWeather::class.java)
        assert(londonWeather.name=="London")
        assert(londonWeather.weather!![0].description!!.isNotEmpty())
        assert(londonWeather.weather!![0].icon!! == "09d")
        assert(londonWeather.main!!.temp == 280.32)
        assert(londonWeather.main!!.pressure == 1012)
        assert(londonWeather.main!!.humidity == 81)
    }
}
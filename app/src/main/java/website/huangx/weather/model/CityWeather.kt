package website.huangx.weather.model

class CityWeather{
    var name=""
    var main:Main? = null
    var weather:Array<Weather>? = null

    class Weather{
        var id = 0
        var description:String? = ""
        var icon:String? = ""
    }

    class Main{
        var temp=0.0
        var pressure=0
        var humidity=0
    }
}
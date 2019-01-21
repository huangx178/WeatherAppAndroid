package website.huangx.weather

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import website.huangx.weather.networking.model.CityWeather

class MainPresenterTest {
    private val view:MainInterface.View = Mockito.mock(MainInterface.View::class.java)
    private val api:CityWeatherDataSource = Mockito.mock(CityWeatherDataSource::class.java)

    @Before
    fun setup(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testLoadCityWeather(){
        val cityWeather = CityWeather()
        cityWeather.name = "Beijing"
        cityWeather.main = CityWeather.Main()
        cityWeather.main!!.temp = 123.45
        cityWeather.main!!.pressure = 1234
        cityWeather.main!!.humidity = 81
        cityWeather.weather = arrayOf(CityWeather.Weather())
        cityWeather.weather!![0].description = "some text"

        Mockito.`when`(api.getCityWeather("Beijing"))
                .thenReturn(Observable.just(cityWeather))

        val presenter = MainPresenter(view, api)
        presenter.loadCityWeather("Beijing")

        val inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onLoadCityWeatherStarted()
        inOrder.verify(view, times(1)).onLoadCityWeatherSuccess(cityWeather)
        inOrder.verify(view, times(1)).onLoadCityWeatherCompleted()
    }

    @Test
    fun testLoadCityWeatherWithError(){
        val throwable = Throwable("an error")

        Mockito.`when`(api.getCityWeather("London"))
                .thenReturn(Observable.error(throwable))

        val presenter = MainPresenter(view, api)
        presenter.loadCityWeather("London")

        val inOrder = Mockito.inOrder(view)
        inOrder.verify(view, times(1)).onLoadCityWeatherError(throwable)
    }
}
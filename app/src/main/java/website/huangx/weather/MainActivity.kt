package website.huangx.weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import website.huangx.weather.networking.CityWeatherRemoteDataSource
import website.huangx.weather.networking.model.CityWeather

class MainActivity : AppCompatActivity(), MainInterface.View, TextWatcher {
    val TAG = "MAIN_ACTIVITY"

    private var cityWeather:CityWeather? = null

    private val presenter = MainPresenter(this, CityWeatherRemoteDataSource())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputCityName.addTextChangedListener(this)
    }

    //MainInterface.View
    override fun onLoadCityWeatherStarted() {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadCityWeatherCompleted() {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadCityWeatherSuccess(cityWeather: CityWeather) {
        this.cityWeather = cityWeather
        txtCityName.text = cityWeather.name
        txtDiscription.text = cityWeather.weather?.get(0)?.description ?: ""
        txtTemperature.text = cityWeather.main?.temp?.toString() ?: ""
        txtHumidity.text = cityWeather.main?.humidity?.toString() ?: ""
        txtPressure.text = cityWeather.main?.pressure?.toString() ?: ""
    }

    override fun onLoadCityWeatherError(error: Throwable) {
        if(BuildConfig.DEBUG)
            Log.e(TAG, error.toString())
        //To change body of created functions use File | Settings | File Templates.
    }

    //TextWatcher
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(s != null)
            presenter.loadCityWeather(s!!.toString())
    }
}

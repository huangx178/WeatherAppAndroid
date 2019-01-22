package website.huangx.weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*
import website.huangx.weather.networking.CityWeatherRemoteDataSource
import website.huangx.weather.networking.model.CityWeather

class MainActivity :AppCompatActivity(),
                    MainInterface.View,
                    TextWatcher,
                    RadioGroup.OnCheckedChangeListener{
    val TAG = "MAIN_ACTIVITY"

    private val favoriteCities = arrayOf("Beijing", "Hong Kong", "Vancouver")

    private var cityWeather:CityWeather? = null

    private val presenter = MainPresenter(this, CityWeatherRemoteDataSource())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputCityName.addTextChangedListener(this)
        rgFavoriteCity.clearCheck()
        rgFavoriteCity.setOnCheckedChangeListener(this)
        for(i in 0..2)
            (rgFavoriteCity.getChildAt(i) as? RadioButton)?.text = favoriteCities[i]
        tbTempUnit.setOnClickListener{
            if(this.cityWeather != null)
                onLoadCityWeatherSuccess(cityWeather!!)
        }
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
        val temp = if (tbTempUnit.isChecked) kelvinToCelsius(cityWeather.main?.temp)
                    else kelvinToFahrenheit(cityWeather.main?.temp)
        txtTemperature.text = if (temp!=null) "%.2f".format(temp!!) else ""
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
        rgFavoriteCity.clearCheck()
        if(s != null)
            presenter.loadCityWeather(s!!.toString())
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        val index = rgFavoriteCity.indexOfChild(rgFavoriteCity.findViewById(checkedId))
        if(index in 0..2)
            presenter.loadCityWeather(favoriteCities[index])
    }

    //helpers
    private fun kelvinToFahrenheit(kelvin: Double?):Double?{
        if(kelvin==null)
            return null
        return kelvinToCelsius(kelvin)!!*9/5+32
    }

    private fun kelvinToCelsius(kelvin:Double?):Double?{
        if(kelvin == null)
            return null
        return kelvin!!-273.15
    }
}

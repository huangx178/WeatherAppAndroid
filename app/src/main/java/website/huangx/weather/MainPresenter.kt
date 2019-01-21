package website.huangx.weather

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import website.huangx.weather.networking.CityWeatherRemoteDataSource

class MainPresenter :MainInterface.Presenter{
    private val api:CityWeatherDataSource
    private var view:MainInterface.View? = null
    private val compositeDisposable = CompositeDisposable()

    constructor(view: MainInterface.View, api:CityWeatherDataSource){
        subscribe(view)
        this.api = api
    }

    override fun subscribe(view:MainInterface.View) {
        if(this.view != null){
            val v = this.view!!
            unsubscribe(v)
        }

        this.view = view
    }

    override fun unsubscribe(view: MainInterface.View) {
        compositeDisposable.clear()
        this.view = null
    }

    override fun onDestroy() {
        if(this.view != null){
            val v = this.view!!
            unsubscribe(v)
        }
    }

    override fun loadCityWeather(cityName: String) {
        view?.onLoadCityWeatherStarted();
        compositeDisposable.clear()

        val disposable = api.getCityWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({view?.onLoadCityWeatherSuccess(it)},
                            {view?.onLoadCityWeatherError(it)},
                            {view?.onLoadCityWeatherCompleted()})
        compositeDisposable.add(disposable)
    }
}
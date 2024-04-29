package app.test.weathertest.network

import app.test.weathertest.model.Weather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("forecast")
    suspend fun getWeatherData(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double,
        @Query("hourly") hourly : String,
        @Query("forecast_days") forecast_days : Int,
        @Query("daily") daily : String
    ) : Response<Weather>

}
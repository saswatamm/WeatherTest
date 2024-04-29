package app.test.weathertest.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.test.weathertest.model.Weather
import app.test.weathertest.network.ApiInterface
import app.test.weathertest.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.gson.GsonConverterFactory.*
import java.net.ConnectException
import java.net.UnknownHostException

class WeatherViewModel : ViewModel(){
    private val _weatherData: MutableLiveData<Weather?> = MutableLiveData()
    fun getWeatherDetails(context: Context)
    {

        GlobalScope.launch(Dispatchers.IO) {
            try{
                val response = RetrofitInstance.api.getWeatherData(28.75, 77.125, "temperature_2m", 4, "temperature_2m_max,precipitation_probability_mean,wind_speed_10m_max,temperature_2m_min")
                if(response.isSuccessful)
                {
                    Log.e("Weather Data : " , "Weather Data : ${response.body()!!.latitude}")
                    _weatherData.postValue(response.body())
                }
                else{
                    Log.e("Unsucessful Attempt", "${response.code()}")
                    showErrorDialog("Error ${response.code()}", context)

                }
            }
            catch (e: ConnectException) {
                Log.e("ConnectException", "Connection Error: ${e.message}")
                showErrorDialog("Connection Error", context)
            } catch (e: UnknownHostException) {
                Log.e("UnknownHostException", "Unknown Host Error: ${e.message}")
                showErrorDialog("Unknown Host Error", context)
            } catch (e: Exception) {
                Log.e("Exception", "Exception occurred: ${e.message}")
                showErrorDialog("Error", context)
            }

        }

    }
    private fun showErrorDialog(message: String, context: Context) {
        GlobalScope.launch(Dispatchers.Main) {
            AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }
    }

    val weatherData: LiveData<Weather?>
        get() = _weatherData





}



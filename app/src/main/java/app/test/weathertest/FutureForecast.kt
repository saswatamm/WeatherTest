package app.test.weathertest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.test.weathertest.adapter.DailyTempatureAdapter
import app.test.weathertest.adapter.HourlyTempatureAdapter
import app.test.weathertest.databinding.ActivityFutureForecastBinding
import app.test.weathertest.databinding.ActivityMainBinding
import app.test.weathertest.model.Weather
import app.test.weathertest.viewmodel.WeatherViewModel

class FutureForecast : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherModel: Weather
    private lateinit var binding : ActivityFutureForecastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFutureForecastBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUI()

    }
    fun setUI()
    {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getData()
    }

    fun getData()
    {
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getWeatherDetails(this@FutureForecast)

        viewModel.weatherData.observe(this, Observer { weather ->

            weather?.let {
                weatherModel = weather
                fillData()
            } ?: run {
                Log.d("Weather Data", "No weather data available yet.")
            }
        })

    }

    fun fillData()
    {
        binding.temperatureDegree.setText("${weatherModel.daily.temperature_2m_max.get(1)}°C")
        binding.rainProbablity.setText("${weatherModel.daily.precipitation_probability_mean.get(1)}%")
        binding.windSpeed.setText("${weatherModel.daily.wind_speed_10m_max.get(1)} km/h")

        binding.daywiseWeatherlist.layoutManager = LinearLayoutManager(this)
        if (weatherModel!=null)
        {
            val adapter = DailyTempatureAdapter(weatherModel.daily.time, weatherModel.daily.temperature_2m_max,weatherModel.daily.temperature_2m_min)
            binding.daywiseWeatherlist.adapter = adapter
        }


    }


}
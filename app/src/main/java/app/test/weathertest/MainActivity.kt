package app.test.weathertest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.test.weathertest.adapter.HourlyTempatureAdapter
import app.test.weathertest.databinding.ActivityMainBinding
import app.test.weathertest.model.Weather
import app.test.weathertest.viewmodel.WeatherViewModel
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherModel: Weather
    private lateinit var binding : ActivityMainBinding
    private lateinit var arrTime : List<String>
    private lateinit var arrTemp : List<Double>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(WeatherViewModel::class.java)

        //viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(WeatherViewModel::class.java)
        //viewModel.getWeatherDetails(applicationContext)

        setupViews()
    }

    fun setupViews() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getWeatherDetails(this@MainActivity)




        viewModel.weatherData.observe(this, Observer { weather ->

            weather?.let {
                Log.d("Weather Data", "Current Latitude: ${it.latitude}")
                weatherModel = weather
                Log.e("WeatherModel", "${weatherModel.daily.wind_speed_10m_max.get(0)}")
                fillData()
            } ?: run {
                Log.d("Weather Data", "No weather data available yet.")
            }
        })

    }

    fun fillData()
    {
        binding.weatherHourlyRecyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        var index = getTime()
        if (weatherModel!=null)
        {
            val adapter = HourlyTempatureAdapter(arrTime, arrTemp)
            binding.weatherHourlyRecyclerview.adapter = adapter
            binding.temperatureDegree.setText("${weatherModel.hourly.temperature_2m.get(index)}°C")
            binding.rainProbablity.setText("${weatherModel.daily.precipitation_probability_mean.get(0)}%")
            binding.windSpeed.setText("${weatherModel.daily.wind_speed_10m_max.get(0)} km/hr")

        }
    }



    fun getTime() : Int
    {
        val date = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val dateStr = dateFormat.format(Date(date))
        Log.e("Current Time", dateStr)

        val currentDate = dateStr.split(" ")[0]
        var currentTime = dateStr.split(" ")[1]
        currentTime = currentTime.split(":")[0] + ":00"
        val currentTimeFinal = "${currentDate}T${currentTime}"
        Log.e("Date Str", currentTimeFinal)
        try {
            val index = weatherModel.hourly.time.indexOf(currentTimeFinal)
            val temp = weatherModel.hourly.temperature_2m[index]
            Log.e("Temperature", temp.toString())
            val length = weatherModel.hourly.time.size
            arrTime = weatherModel.hourly.time.slice(index until length)
            arrTemp = weatherModel.hourly.temperature_2m.slice(index until length)
            val cal: Calendar = Calendar.getInstance()
            val day: Int = cal.get(Calendar.DAY_OF_WEEK)
            binding.dateText.setText(getDay(day) + " " + formatDate(currentDate))


            return index
        } catch (e: Exception) {
            Log.e("Error is", e.localizedMessage)
            return -1
        }

    }



    fun formatDate(date : String) : String
    {
        val parts = date.split("-")
        val day = parts[2]
        val month = parts[1]
        return "$day-$month"
    }

    fun getDay(day : Int) : String
    {
        return when (day) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> "Invalid day"
        }
    }

    fun NavigateNextScreen(view : View){

        val intent = Intent(this, FutureForecast::class.java)
        startActivity(intent)
    }

}


package app.test.weathertest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.test.weathertest.databinding.DailyWeatherListBinding

class DailyTempatureAdapter(private val timeList : List<String>, private val minTemperatureList : List<Double>,private val maxTemperatureList : List<Double>) : RecyclerView.Adapter<DailyTempatureAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: DailyWeatherListBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var date = timeList.get(position)
            val minTemperature = minTemperatureList.get(position)
            val maxTemperature = maxTemperatureList.get(position)
            holder.binding.minTemperature.setText("${minTemperature}°C")
            holder.binding.maxTemperature.setText("${maxTemperature}°C")
            date = formatDate(date)
            holder.binding.date.setText("${date}")



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DailyWeatherListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return timeList.size
    }

    fun formatDate(date : String) : String
    {
        val parts = date.split("-")
        val day = parts[2]
        val month = parts[1]
        return "$day-$month"
    }


}
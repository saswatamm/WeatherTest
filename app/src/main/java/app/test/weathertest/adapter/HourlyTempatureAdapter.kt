package app.test.weathertest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.test.weathertest.R
import app.test.weathertest.databinding.HourlyWeatherListBinding

class HourlyTempatureAdapter(private val timeList : List<String>, private val temparatureList : List<Double>) : RecyclerView.Adapter<HourlyTempatureAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding:HourlyWeatherListBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HourlyWeatherListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hourData = timeList.get(position)
        val tempData = temparatureList.get(position)
        holder.binding.time.setText("${hourData.substring(hourData.length-5)}")
        holder.binding.temperatureDegree.setText("${tempData}°C")
    }



}
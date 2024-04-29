package app.test.weathertest.model

data class DailyData(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val precipitation_probability_mean: List<Int>,
    val wind_speed_10m_max: List<Double>,
    val temperature_2m_min : List<Double>
)



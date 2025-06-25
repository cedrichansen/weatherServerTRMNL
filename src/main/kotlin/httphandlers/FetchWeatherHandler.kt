package org.example.httphandlers

import org.example.HttpClient

/**
 * https://open-meteo.com/
 */
class FetchWeatherHandler() {
    companion object {
        val baseURL = "https://api.open-meteo.com/v1/forecast"


        fun fetchWeather(lat : String, lng : String) : Unit{
            val params = mapOf(
                "latitude" to lat,
                "longitude" to lng,
//                "hourly" to "temperature_2m,uv_index,apparent_temperature,precipitation_probability,precipitation",
                "forecast_days" to "1",
                "timezone" to "auto"
            )
            val resp = HttpClient.get(baseURL, params = params)
            println(resp)
            return resp
        }
    }


}

//https://api.open-meteo.com/v1/forecast?latitude=45.4112&longitude=-75.6981&hourly=temperature_2m,uv_index,apparent_temperature,precipitation_probability,precipitation&timezone=auto&forecast_days=1
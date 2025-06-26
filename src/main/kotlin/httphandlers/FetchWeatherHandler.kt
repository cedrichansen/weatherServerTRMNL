package org.example.httphandlers

import com.google.inject.Inject
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import org.example.HttpClient

/**
 * https://open-meteo.com/
 */
class  FetchWeatherHandler {
    @Inject
    lateinit var httpClient: HttpClient

    companion object {
        val sampleURL = "https://api.open-meteo.com/v1/forecast?latitude=45.4112&longitude=-75.6981&hourly=temperature_2m,uv_index,apparent_temperature,precipitation_probability,precipitation&timezone=auto&forecast_days=1";
        val baseURL = "https://api.open-meteo.com/v1/forecast"
    }

    suspend fun handle(call : RoutingCall) {
        val qp = call.request.queryParameters
        val lat = qp["lat"]
        val lng = qp["lng"]
        if (lat == null || lng == null) {
            call.respond(HttpStatusCode.BadRequest, "Must provide lat & lng params")
        }
        val getWeatherDetails =  fetchWeather(lat!!, lng!!)
        if (getWeatherDetails == null) {
            call.respond(HttpStatusCode.InternalServerError)
        }
        call.respond("$getWeatherDetails")
    }


    private fun fetchWeather(lat : String, lng : String) : String? {
        val params = mapOf(
            "latitude" to lat,
            "longitude" to lng,
            "hourly" to "temperature_2m,uv_index,apparent_temperature,precipitation_probability,precipitation",
            "forecast_days" to "1",
            "timezone" to "auto"
        )
        println("Making weather request")

        try {
            val resp = httpClient.get(baseURL, params = params)
            println(resp)
            return resp.bodyString()
        } catch (e : Exception) {
            println("Error $e")
            return null
        }
    }
}
package org.example

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.example.httphandlers.FetchWeatherHandler

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println("Starting Weather server for TRMNL")

    embeddedServer(Netty, configure = {
        connectors.add(EngineConnectorBuilder().apply {
            host = "127.0.0.1"
            port = 8080
        })
        connectionGroupSize = 10
        workerGroupSize = 10
        callGroupSize = 10
        shutdownGracePeriod = 2000
        shutdownTimeout = 3000
    }) {
        routing {
            get("/weather") {
                val qp = call.request.queryParameters
                val lat = qp["lat"]
                val lng = qp["lng"]
                if (lat == null || lng == null) {
                    call.respond(HttpStatusCode.BadRequest, "Must provide lat & lng params")
                }
                val getWeatherDetails = FetchWeatherHandler.fetchWeather(lat as String, lng as String)
                call.respond("Got ${getWeatherDetails}")
            }
        }
    }.start(wait = true)
}
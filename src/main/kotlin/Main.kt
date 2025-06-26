package org.example

import com.google.inject.Guice
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.example.httphandlers.FetchWeatherHandler

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("Starting Weather server for TRMNL")
    val injector = Guice.createInjector(MainGuiceModule())
    var weatherHandler = injector.getInstance(FetchWeatherHandler::class.java)

    startServer(weatherHandler)
}


fun startServer(weatherHandler : FetchWeatherHandler) {
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
                weatherHandler.handle(call)
            }
        }
    }.start(wait = true)
}
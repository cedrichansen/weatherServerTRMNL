package org.example

import com.google.inject.AbstractModule
import org.example.httphandlers.FetchWeatherHandler

class MainGuiceModule : AbstractModule() {

    override fun configure() {
        bind(HttpClient::class.java).toInstance(HttpClient())
        bind(FetchWeatherHandler::class.java).toInstance(FetchWeatherHandler())
    }
}
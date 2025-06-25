package org.example

class HttpClient {
    companion object {
        fun get(url: String, params: Map<String, String> = emptyMap()) {
            khttp.get(url = url, params = params)
        }
    }
}
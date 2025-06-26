package org.example

import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response

class HttpClient {

    val client = ApacheClient()

    fun get(url: String, params: Map<String, String> = emptyMap()): Response {
        var req = Request(Method.GET, url)
        params.entries.forEach { (k, v) -> req = req.query(k, v) }
        println("Executing req: $req")
        var resp = client.invoke(req)
        return resp
    }

}


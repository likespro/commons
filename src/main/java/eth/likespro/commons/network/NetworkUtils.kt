package eth.likespro.commons.network

import org.json.JSONObject
import org.minidns.hla.ResolverApi
import java.io.IOException
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class NetworkUtils {
    companion object{
        fun URL.getSRVRecordAddress(): String? {
            val srv = this.readText()
            try {
                val result = ResolverApi.INSTANCE.resolveSrv(srv)
                if (result.wasSuccessful()) {
                    val srvRecords = result.sortedSrvResolvedAddresses
                    for (record in srvRecords) {
                        return record.srv.target.toString()+":"+record.srv.port.toString()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * Sends a GET request to the given URI and returns the response as a JSONObject.
         *
         * @return The response body as a JSONObject.
         */
        fun URI.get(): JSONObject {
            val response = HttpClient.newBuilder()
                .build()
                .send(
                    HttpRequest.newBuilder(this)
                        .GET()
                        .build(),
                    HttpResponse.BodyHandlers.ofString()
                )
            return JSONObject(response.body())
        }

        /**
         * Sends a POST request to the given URI with the specified JSON body.
         *
         * @param body The JSON body to be sent in the POST request.
         */
        fun URI.post(body: JSONObject) {
            HttpClient.newBuilder()
                .build()
                .send(
                    HttpRequest.newBuilder(this)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(JSONObject().apply {
                            put("functionName", "ping")
                            put("functionArgs", JSONObject())
                        }.toString()))
                        .build(),
                    HttpResponse.BodyHandlers.ofString()
                )
        }
    }
}
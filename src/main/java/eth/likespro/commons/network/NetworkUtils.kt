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
         * Sends a GET request to the given URI and returns the response as a String.
         *
         * @return The response body as String.
         */
        fun URI.get(): String {
            val response = HttpClient.newBuilder()
                .build()
                .send(
                    HttpRequest.newBuilder(this)
                        .GET()
                        .build(),
                    HttpResponse.BodyHandlers.ofString()
                )
            return response.body()
        }

        /**
         * Sends a POST request to the given URI with the specified body and returns the response as a String.
         *
         * @param body The body of the POST request.
         * @return The response body as String.
         */
        fun URI.post(body: String): String {
            return HttpClient.newBuilder()
                .build()
                .send(
                    HttpRequest.newBuilder(this)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build(),
                    HttpResponse.BodyHandlers.ofString()
                ).body()
        }
    }
}
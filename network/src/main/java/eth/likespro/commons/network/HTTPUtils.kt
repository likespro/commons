/*
 * Copyright 2025 likespro.
 *
 * From https://github.com/likespro/commons
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eth.likespro.commons.network

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object HTTPUtils {
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
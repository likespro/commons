package eth.likespro.commons.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RESTAPIResponse<T: @Serializable Any?>(
    val success: Boolean,
    val data: T? = null,
    val error: Error? = null,
    val metadata: Map<String, String?> = emptyMap(),
) {
    @Serializable
    data class Error(
        val exceptionClass: String,
        val stackTrace: String,
        val message: String?,
        val cause: String?,
        val localizedMessage: String?
    )
}
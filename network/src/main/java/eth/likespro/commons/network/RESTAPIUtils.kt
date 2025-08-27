package eth.likespro.commons.network

import eth.likespro.commons.models.EncodableResult
import eth.likespro.commons.network.models.RESTAPIResponse
import eth.likespro.commons.reflection.ObjectEncoding.encodeObject
import kotlinx.serialization.Serializable

object RESTAPIUtils {
    fun <TValue, TData: @Serializable Any?> EncodableResult<TValue>.toResponse(valueMapper: (TValue) -> TData): RESTAPIResponse<TData> {
        return if (isSuccess) RESTAPIResponse(
            success = true,
            data = valueMapper(getOrThrow()),
            metadata = metadata.mapValues { it.value?.encodeObject() }
        ) else RESTAPIResponse(
            success = false,
            error = RESTAPIResponse.Error(
                exceptionClass = failure!!.exceptionClass.name,
                stackTrace = failure!!.stackTrace,
                message = failure!!.message,
                cause = failure!!.cause,
                localizedMessage = failure!!.localizedMessage
            ),
            metadata = metadata.mapValues { it.value?.encodeObject() }
        )
    }
}
package eth.likespro.commons.models

import eth.likespro.commons.JSONConversions.Companion.toJSONObject

class Result(
    val status: Status,
    val details: Any? = null
) {
    override fun toString(): String {
        return this.toJSONObject().toString()
    }

    companion object{

    }
}
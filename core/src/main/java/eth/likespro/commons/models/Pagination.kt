package eth.likespro.commons.models

import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val page: Int,
    val itemsPerPage: Int,
    val search: String? = null,
    val period: Period? = null,
    val sort: Sort? = null
) {
    class PaginationIsInvalidException(override val message: String) : RuntimeException()
    class TooManyItemsRequestedException(override val message: String) : RuntimeException()

    init {
        if(page < 0)
            throw PaginationIsInvalidException("Page Index must be greater than or equal to 0")
        if(itemsPerPage <= 0)
            throw PaginationIsInvalidException("Items per page must be greater than 0")
    }

    @Serializable
    data class Sort(
        val by: String,
        val order: Order
    )

    enum class Order {
        ASC, DESC
    }

    @Serializable
    data class Period(
        val from: Timestamp,
        val to: Timestamp
    )

    @Serializable
    open class Result<T : Any>(
        val items: List<T>,
        val total: Long
    )

    companion object {
        val ALL = Pagination(0, Int.MAX_VALUE)
    }

    val offset get() = page * itemsPerPage.toLong()
}
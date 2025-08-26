package eth.likespro.commons.models

import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable
import kotlin.math.min

@Serializable
data class Pagination(
    val page: Int,
    val itemsPerPage: Int,
    val search: String? = null,
    val period: Period? = null,
    val sort: Sort? = null
) {
    class PaginationIsInvalidException(override val message: String) : RuntimeException()
    class TooManyItemsRequestedException(override val message: String = "Too many items requested. Limit is 1000") : RuntimeException()

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

    val offset get() = page * itemsPerPage.toLong()

    companion object {
        val ALL = Pagination(0, Int.MAX_VALUE)

        fun <T> List<T>.applyPagination(pagination: Pagination): List<T> = if(pagination.offset > size) {
            emptyList()
        } else {
            val end = min(pagination.offset + pagination.itemsPerPage, size.toLong())
            subList(pagination.offset.toInt(), end.toInt())
        }
    }

    fun next() = copy(page = page + 1)

    fun hasPrevious() = page > 0
    fun previous() = copy(page = page - 1)
}
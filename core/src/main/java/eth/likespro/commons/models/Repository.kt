package eth.likespro.commons.models

interface Repository<Entity : eth.likespro.commons.models.Entity<ID>, ID : Any> {
    suspend fun findById(id: ID): Entity?

    suspend fun isExisting(id: ID): Boolean

    suspend fun findAll(): List<Entity>

    suspend fun findAll(pagination: Pagination): Pagination.Result<Entity>

    suspend fun create(entity: Entity): Entity

    suspend fun upsert(entity: Entity): Entity

    suspend fun update(entity: Entity): Entity?

    suspend fun delete(id: ID)

    suspend fun count(pagination: Pagination? = null): Long
}
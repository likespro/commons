package eth.likespro.commons.mongodb

import com.mongodb.client.model.IndexOptions
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document
import reactor.core.publisher.Mono

class MongoDBUtils {
    companion object {
        fun<T> MongoCollection<T>.createUniqueIndex(vararg names: String): Mono<String>{
            val doc = Document(); names.forEach { doc.append(it, 1) }
            return Mono.from(this.createIndex(doc, IndexOptions().unique(true)))
        }
    }
}
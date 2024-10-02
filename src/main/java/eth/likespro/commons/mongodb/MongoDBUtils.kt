package eth.likespro.commons.mongodb

import com.mongodb.client.model.ReplaceOptions
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

class MongoDBUtils {
    companion object{
        val upsertTrue = ReplaceOptions().upsert(true)
    }
}
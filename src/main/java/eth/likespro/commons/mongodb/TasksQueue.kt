package eth.likespro.commons.mongodb

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document
import reactor.core.publisher.Mono

class TasksQueue(val collection: MongoCollection<Document>){
    fun pushTask(task: String, payload: Document, priority: Long = 0L, lockedUntil: Long = 0L): Mono<InsertOneResult?> {
        return Mono.from(collection.insertOne(
            Document()
            .append("task", task)
            .append("status", "pending")
            .append("payload", payload)
            .append("createdAt", System.currentTimeMillis())
            .append("updatedAt", System.currentTimeMillis())
            .append("priority", priority)
            .append("processingBy", null)
            .append("lockedAt", null)
            .append("lockedUntil", lockedUntil)
        ))
    }
    fun getTask(task: String, workerName: String = "default"): Document?{
        return Mono.from(collection.findOneAndUpdate(
            Document("status", "pending").append("lockedAt", null),
            Document("\$set", Document()
                .append("status", "processing")
                .append("lockedAt", System.currentTimeMillis())
                .append("updatedAt", System.currentTimeMillis())
                .append("processingBy", workerName)),
            FindOneAndUpdateOptions().sort(Document().append("priority", 1).append("createdAt", 1)))).block()
    }
    //fun pushTask(task: String, payload: Document, priority: Long = 0L, lockedUntil: Long = 0L)
}
package MongoDB

import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

class MongoDB {
}
object MongoDB {

  def insertDocument() {
    val mongoClient: MongoClient = MongoClient()

    val database: MongoDatabase = mongoClient.getDatabase("test")
    val collection : MongoCollection[Document] = database.getCollection("testcollection")

    val doc : Document = Document("_id" -> 2, "name" -> "MongoDB", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

    val observable: Observable[Completed] = collection.insertOne(doc)

    val promise = Promise[Boolean]
    observable.subscribe(new Observer[Completed] {

      override def onNext(result: Completed): Unit = println("Inserted")

      override def onError(e: Throwable): Unit = {
        println("Failed")
        promise.success(false)
      }

      override def onComplete(): Unit =  {
        println("Completed")
        promise.success(true)
      }
    })

    val future = promise.future
    Await.result(future, Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()
  }

}

package MongoDB

import StructureCSV._
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

class MongoDB {
}
object MongoDB {

  def insertRunways(list : List[Runways]) : Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val collection : MongoCollection[Document] = database.getCollection("runways")

    //IF List insertMane and IF One insertOne
    val documents  = list.map( runway => Document("_id" -> runway.id.toString,
      "airportRef" -> runway.airportRef.toString,
      "airportIdent" -> runway.airportIdent.toString,
      "surface" -> runway.surface,
      "leIdent" -> runway.leIdent))

    //val observable : Observable[Completed] = collection.insertOne(Document())
    val observable : Observable[Completed] = collection.insertMany(documents)

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

  def insertAirport(list : List[Airport]) : Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val collection : MongoCollection[Document] = database.getCollection("airports")

    //IF List insertMane and IF One insertOne
    val documents  = list.map( airport => Document("_id" -> airport.id.toString,
      "ident" -> airport.airportIdent.toString,
      "type" -> airport.airportType,
      "name" -> airport.name,
      "isoCountry" -> airport.isoCountry.toString))

    //val observable : Observable[Completed] = collection.insertOne(Document())
    val observable : Observable[Completed] = collection.insertMany(documents)

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

  def insertCountry(list : List[Country]): Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val collection : MongoCollection[Document] = database.getCollection("countries")

    //IF List insertMane and IF One insertOne
    val documents  = list.map( country => Document("_id" -> country.id.toString,
                                                   "code" -> country.code.toString,
                                                   "name" -> country.name,
                                                   "continent" -> country.continent.getOrElse(None).toString,
                                                   "keywords" -> country.keywords))

    //val observable : Observable[Completed] = collection.insertOne(Document())
    val observable : Observable[Completed] = collection.insertMany(documents)

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

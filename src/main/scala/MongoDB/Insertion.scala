package MongoDB

import StructureCSV._
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

object Insertion {

  def insertRunways(list : List[Runways]) : Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val runwaysCollection : MongoCollection[Document] = database.getCollection("runways")

    val runwaysDocument  = list.map( runway => Document("_id" -> runway.id.toString,
      "airportRef" -> runway.airportRef.toString,
      "airportIdent" -> runway.airportIdent.toString,
      "surface" -> runway.surface,
      "leIdent" -> runway.leIdent))

    val runwayObservable : Observable[Completed] = runwaysCollection.insertMany(runwaysDocument)

    val promise = Promise[Boolean]
    runwayObservable.subscribe(new Observer[Completed] {

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
    Await.result(promise.future, Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()
  }

  def insertAirport(list : List[Airport]) : Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val airportsCollection : MongoCollection[Document] = database.getCollection("airports")

    val airportsDocument  = list.map( airport => Document("_id" -> airport.id.toString,
                                                          "ident" -> airport.airportIdent.toString,
                                                          "type" -> airport.airportType,
                                                          "name" -> airport.name,
                                                          "isoCountry" -> airport.isoCountry.toString))

    val airportObservable : Observable[Completed] = airportsCollection.insertMany(airportsDocument)

    val promise = Promise[Boolean]
    airportObservable.subscribe(new Observer[Completed] {

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

    Await.result(promise.future, Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()
  }

  def insertCountry(list : List[Country]): Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val countriesCollection : MongoCollection[Document] = database.getCollection("countries")

    val countriesDocument  = list.map( country => Document("_id" -> country.id.toString,
                                                   "code" -> country.code.toString,
                                                   "name" -> country.name,
                                                   "continent" -> country.continent.getOrElse(None).toString,
                                                   "keywords" -> country.keywords))

    val countriesObservable : Observable[Completed] = countriesCollection.insertMany(countriesDocument)

    val promise = Promise[Boolean]
    countriesObservable.subscribe(new Observer[Completed] {

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

    Await.result(promise.future, Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()
  }
}

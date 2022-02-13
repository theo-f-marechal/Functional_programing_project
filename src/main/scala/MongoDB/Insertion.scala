package MongoDB

import StructureCSV._
import org.mongodb.scala.bson.collection.immutable
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

object Insertion {

  def insertRunways(list : List[Runways]) : Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val runwaysCollection : MongoCollection[Document] = database.getCollection("runways")

    val runwaysDocument  = list.map( runway => Document(
      "_id" -> runway.id.id.toString,
      "airportRef" -> runway.airportRef.id.toString,
      "airportIdent" -> runway.airportIdent.ident,
      "surface" -> {runway.surface match {
        case None => "None"
        case Some(surface) => surface.surface}},
      "leIdent" -> {runway.leIdent match {
        case None => "None"
        case Some(leIdent) => leIdent.leIdent}}
    ))

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

    val airportsDocument  = list.map( airport => Document(
      "_id" -> airport.id.id.toString,
      "ident" -> airport.airportIdent.ident,
      "type" -> airport.airportType.airportType,
      "name" -> airport.name.name,
      "isoCountry" -> airport.isoCountry.isoCountry))

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

    val countriesDocument  = list.map( country => Document(
      "_id" -> country.id.id.toString,
      "code" -> country.code.code,
      "name" -> country.name.name,
      "continent" -> {country.continent match {
        case None => "None"
        case Some(continent) => continent.name}},
      "keywords" -> {country.keywords match {
        case None => "None"
        case Some(keywords) => keywords.keywords}}
    ))

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

  def emptyDb(): Unit = {
    val mongoClient : MongoClient = MongoClient()
    val database : MongoDatabase = mongoClient.getDatabase("test")

    val runwaysCollection : MongoCollection[Document] = database.getCollection("runways")
    Await.result(
      runwaysCollection.deleteMany(immutable.Document.empty).toFuture(),
      Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    val airportsCollection : MongoCollection[Document] = database.getCollection("airports")
    Await.result(airportsCollection.deleteMany(immutable.Document.empty).toFuture(),
      Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    val countriesCollection : MongoCollection[Document] = database.getCollection("countries")
    Await.result(
      countriesCollection.deleteMany(immutable.Document.empty).toFuture(),
      Duration(5, java.util.concurrent.TimeUnit.SECONDS))
  }
}

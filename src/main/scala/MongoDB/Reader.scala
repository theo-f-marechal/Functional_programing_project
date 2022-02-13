package MongoDB

import com.mongodb.client.model.Projections.{exclude, excludeId, fields}
import org.mongodb.scala.model.Accumulators.sum
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Sorts.{ascending, descending}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object Reader{

  def query(inputCountry : String, isName : Boolean): Observable[Document] = { //Seq[Document] = {
    val mongoClient : MongoClient = MongoClient()
    val database : MongoDatabase = mongoClient.getDatabase("test")
    val countriesCollection : MongoCollection[Document] = database.getCollection("countries")

    val queryObservable: Observable[Document] = countriesCollection.aggregate(List(
      `match`(equal({if (isName) "name" else "code"}, inputCountry)),
      lookup(
        "airports",
        "code",
        "isoCountry",
        "airports"
      ),
      lookup(
        "runways",
        "airports.ident",
        "airportIdent",
        "runways"
      )
    ))
    queryObservable
  }

  def getReport1: (List[(String,String)],List[(String,String)]) = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val airportsCollection : MongoCollection[Document] = database.getCollection("airports")

    //TOP 10
    val top10CountryObservable : Observable[Document] = airportsCollection.aggregate(List(
      group(
        "$isoCountry",
        sum("count",1)
      ),
      sort(descending("count")),
      limit(10)
    ))

    val top10CountryList = Await.result(top10CountryObservable.toFuture(), Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    // BOTTOM 10
    val bottom10CountryObservable : Observable[Document] = airportsCollection.aggregate(List(
      group(
        "$isoCountry",
        sum("count",1)
      ),
      sort(ascending("count")),
      limit(10)
    ))

    val bottom10CountryList = Await.result(bottom10CountryObservable.toFuture(), Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()

    val top10CountryFinalList = top10CountryList.flatten
      .grouped(2)
      .map { case List(key, value) => (key._2.toString.split("=")
        .last
        .replaceAll("^'|'|}$", ""),
        value._2.toString.split("=")
          .last
          .replaceAll("^|}$", ""))
      }.toList
    val bottom10CountryFinalList = bottom10CountryList.flatten
      .grouped(2)
      .map { case List(key, value) => (key._2.toString.split("=")
        .last
        .replaceAll("^'|'|}$", ""),
        value._2.toString.split("=")
          .last
          .replaceAll("^|}$", ""))
      }.toList

    (top10CountryFinalList, bottom10CountryFinalList)
  }

  def getReport2: Seq[Document] = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")

    val countriesCollection : MongoCollection[Document] = database.getCollection("countries")
    val queryObservable : Observable[Document] = countriesCollection.aggregate(List(
      lookup(
        "airports",
        "code",
        "isoCountry",
        "airports"
      ),
      lookup(
        "runways",
        "airports.ident",
        "airportIdent",
        "runways"
      ),
      project(fields(excludeId(), exclude("airports._id",
        "airports.type",
        "airports.name",
        "airports.isoCountry",
        "airports.ident",
        "runways._id",
        "runways.airportRef",
        "runways.airportIdent",
        "runways.leIdent")))
    ))

    val report2Result = Await.result(queryObservable.toFuture(), Duration(10, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()

    report2Result
  }

  def getReport3: List[(String,String)] = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val airportCollection : MongoCollection[Document] = database.getCollection("runways")

    val top10RunwayTypeObservable : Observable[Document] = airportCollection.aggregate(List(
      group(
        "$leIdent",
        sum("count", 1)),
      sort(descending("count")),
      limit(10)
    ))

    val report3Result = Await.result(top10RunwayTypeObservable.toFuture(), Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()


    val report3 = report3Result.flatten
      .grouped(2)
      .map { case List(key, value) => (key._2.toString.split("=")
        .last
        .replaceAll("^'|'|}$", ""),
        value._2.toString.split("=")
          .last
          .replaceAll("^|}$", ""))
      }.toList

    println(report3Result)
    report3
  }

}

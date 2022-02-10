package MongoDB

import com.mongodb.client.model.BsonField
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, Observable, Observer, model}
import org.mongodb.scala.model.Accumulators.sum
import org.mongodb.scala.model.Aggregates.{`match`, count, group, limit, lookup, sort}
import org.mongodb.scala.model.BsonField
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Sorts.{ascending, descending}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Reader{

  def query(countryCode : String, countryName : String): Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")

    val countriesCollection : MongoCollection[Document] = database.getCollection("countries")

    // Case Country name as input
    /**
    val queryObservable : Observable[Document] = countriesCollection.aggregate(List(
      `match`(equal("name", countryName)),
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
    **/

    // Case Country code as given
    val queryObservable : Observable[Document] = countriesCollection.aggregate(List(
      `match`(equal("code", countryCode)),
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

    val queryResult = Await.result(queryObservable.toFuture(), Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()

    println(queryResult)
  }

  def getReport1(): Unit = {
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

    val countryList = List.concat(top10CountryList, bottom10CountryList)
    val report1 = countryList.flatMap(x => x)
                          .grouped(2)
                          .map { case List(key, value) => (key._2.toString.split("=")
                                                                          .last
                                                                          .replaceAll("^'|'|}$", ""),
                                                           value._2.toString.split("=")
                                                                            .last
                                                                            .replaceAll("^|}$", ""))
                          }.toList

    println(countryList)
    println(report1)
  }

  def getReport2(): Unit = {
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
      )
    ))

    val report2Result = Await.result(queryObservable.toFuture(), Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()

    println(report2Result)

  }

  def getReport3(): Unit = {
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


    val report3 = report3Result.flatMap(x => x)
                               .grouped(2)
                               .map { case List(key, value) => (key._2.toString.split("=")
                                                                               .last
                                                                               .replaceAll("^'|'|}$", ""),
                                                               value._2.toString.split("=")
                                                                                .last
                                                                                .replaceAll("^|}$", ""))
                               }.toList

    println(report3Result)
    println(report3)
  }

}

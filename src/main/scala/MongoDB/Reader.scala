package MongoDB

import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}
import org.mongodb.scala.model.Accumulators.sum
import org.mongodb.scala.model.Aggregates.{group, limit, sort}
import org.mongodb.scala.model.Sorts.{ascending, descending}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Reader{

  def query(country : String): Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")

  }

  def getReport1(): Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val airportsCollection : MongoCollection[Document] = database.getCollection("airports")

    //TOP 10
    val top10CountryObservable : Observable[Document] = airportsCollection.aggregate(List(
      group(
        Document("isoCountry" -> "$isoCountry"),
        sum("count", 1)
      ),
      sort(descending("count")),
      limit(10)
    ))

    val top10CountryList = Await.result(top10CountryObservable.toFuture(), Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    // BOTTOM 10
    val bottom10CountryObservable : Observable[Document] = airportsCollection.aggregate(List(
      group(
        Document("isoCountry" -> "$isoCountry"),
        sum("count", 1)
      ),
      sort(ascending("count")),
      limit(10)
    ))

    val bottom10CountryList = Await.result(bottom10CountryObservable.toFuture(), Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()

    println(top10CountryList)
    println(bottom10CountryList)
  }

  def getReport3(): Unit = {
    val mongoClient : MongoClient = MongoClient()

    val database : MongoDatabase = mongoClient.getDatabase("test")
    val airportCollection : MongoCollection[Document] = database.getCollection("runways")

    val top10RunwayTypeObservable : Observable[Document] = airportCollection.aggregate(List(
      group(
        Document("leIdent" -> "$leIdent"),
        sum("count", 1)),
      sort(descending("count")),
      limit(10)
    ))

    val runwaysTypeList = Await.result(top10RunwayTypeObservable.toFuture(), Duration(5, java.util.concurrent.TimeUnit.SECONDS))

    mongoClient.close()

    print(runwaysTypeList)
  }

}

package Swing

import org.mongodb.scala.Document
import org.mongodb.scala.bson.{BsonArray, BsonString}

import scala.annotation.tailrec
import scala.jdk.CollectionConverters.ListHasAsScala


object Formatting {

  def parseDocQuery(str : String): String = {
    str.replaceAll("[\\[\\]{}]","")
      .split(',').toList
      .map(x => x.replaceAll("\"", "").split(':').last)
      .distinct.foldLeft("")((acc, elt) => acc + elt + ",").dropRight(1)
  }

  def parseDocReport2(str : String): List[String] =
    str.replaceAll("[{}]","")
      .split(',').toList
      .map(x => x.replaceAll("\"", ""))

  def query(sDoc: Seq[Document], name: Boolean): String = sDoc match {
    case Nil => s"That ${if (name) "name" else "code"} isn't in the data base"
    case h::_ => h.get[BsonArray]("airports") match {
      case None => "None"
      case Some(bs) =>
        bs.getValues.asScala.toList.map(x => parseDocQuery(x.toString))
          .foldLeft("")((acc,elt) => acc + elt + "\n")
    }
  }

  //"### Error make sure that the database is populated ###"

  def report1(strs: (List[(String,String)],List[(String,String)])): String = {
    if (strs._1.isEmpty || strs._2.isEmpty) "### Error make sure that the database is populated ###"
    else {
      val ttm = strs._1.map(x => x._1 + " : " + x._2)
        .foldLeft((1, "Top ten country with the most airports :\n"))(
          (acc, elt) => (acc._1 + 1, acc._2 + " " + acc._1 + " -- " + elt + "\n"))._2
      val ttl = strs._2.map(x => x._1 + " : " + x._2)
        .foldLeft((1, "Top ten country with the less airports :\n"))(
          (acc, elt) => (acc._1 + 1, acc._2 + " " + acc._1 + " -- " + elt + "\n"))._2
      ttm + "\n" + ttl
    }
  }

  def report2(sDoc: Seq[Document]): String = {
    @tailrec
    def aux(sDocAux: Seq[Document], acc: String, first: Boolean): String = (sDocAux, first) match {
      case (Nil, true) => "### Error make sure that the database is populated ###"
      case (Nil, false) => "Type of runways per Country :\n" + acc
      case (h::t,_) =>
        val country = h.get[BsonString]("name") match {
          case None => "None"
          case Some(bs) => bs.getValue
        }
        val surfaces = h.get[BsonArray]("runways") match {
          case None => ""
          case Some(bs) => parseDocQuery(bs.getValues.toString)
        }
        aux(t, acc + " -- " + country + " : " + surfaces + "\n", first = false)
    }
    aux(sDoc,"",first = true)
  }

  def report3(strs: List[(String,String)]): String = {
    if (strs.isEmpty) "### Error make sure that the database is populated ###"
    else {
      strs
        .map(x => x._1 + " : " + x._2)
        .foldLeft((1, "The top 10 most common runway latitude are :\n"))(
          (acc, elt) => (acc._1 + 1, acc._2 + " " + acc._1 + " -- " + elt + " airports\n"))._2
    }
  }
}

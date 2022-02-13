package Swing

import org.mongodb.scala.Document
import org.mongodb.scala.bson.BsonArray

import scala.jdk.CollectionConverters.ListHasAsScala


object Formatting {

  def parseDoc(str : String): List[String] =
    str.replaceAll("[{}]","")
      .split(',').toList
      .map(x => x.replaceAll("\"", ""))

  def query(sDoc: Seq[Document], name: Boolean): String = sDoc match {
    case Nil => s"That ${if (name) "name" else "code"} isn't in the data base"
    case h::_ => h.get[BsonArray]("airports") match {
      case None => "None"
      case Some(bs) =>
        bs.getValues.asScala.toList.map(x => parseDoc(x.toString))
          .foldLeft("")((acc,elt) => acc + elt.foldLeft("")((acc1, elt1) => acc1 + elt1 + "  ") + "\n")
    }
  }

  def report1(strs: (List[(String,String)],List[(String,String)])): String = {
    val ttm = strs._1.map(x => x._1 + " : " + x._2)
      .foldLeft((1,"Top ten country with the most airports :\n"))(
        (acc,elt) => (acc._1 +1 ,acc._2 + " " + acc._1 + " -- " + elt + "\n"))._2
    val ttl = strs._2.map(x => x._1 + " : " + x._2)
      .foldLeft((1,"Top ten country with the less airports :\n"))(
        (acc,elt) => (acc._1 +1 ,acc._2 + " " + acc._1 + " -- " + elt + "\n"))._2
    ttm + "\n" + ttl
  }

  def report3(strs: List[(String,String)]): String = {
    strs
      .map(x => x._1 + " : " + x._2)
      .foldLeft((1,"The top 10 most common runway latitude are :\n"))(
        (acc,elt) => (acc._1 + 1,acc._2 + " " + acc._1 + " -- " + elt + " airports\n"))._2
  }
}

package StructureCSV

import StructureCSV.Code.validateCode
import StructureCSV.Continent.validateContinent
import StructureCSV.CountryId.validateId
import StructureCSV.Error.AllErrorsOr
import StructureCSV.Keywords.validateKeywords
import StructureCSV.Name.validateName
import cats.implicits.{catsSyntaxTuple5Semigroupal, catsSyntaxValidatedId}

final case class Country(id : CountryId,
                         code : Code,
                         name : Name,
                         continent : Option[Continent],
                         keywords : Option[Keywords])

object Country{
  def deserialization(args : List[String]): AllErrorsOr[Country] = args match {
    case Nil => Error("No arguments were given.").invalidNel
    case _ => (
      validateId(args.headOption),
      validateCode(args.lift(1)),
      validateName(args.lift(2)),
      validateContinent(args.lift(3)),
      validateKeywords(args.lift(4))
    ).mapN((validId,validCode,validName,validContinent,validKeywords) =>
      Country(validId,validCode,validName,validContinent,validKeywords))
  }
}

final case class CountryId private(id: Long) extends AnyVal
object CountryId {
  def validateId(idO: Option[String]): AllErrorsOr[CountryId] = idO match {
    case None => Error("Country Id can't be empty.").invalidNel
    case Some(id) => id.toLongOption match {
      case None => Error("Country Id must be a Long.").invalidNel
      case Some(idL) =>
        if (idL < 0) Error("Country Id can't be negative.").invalidNel
        else CountryId(idL).validNel
    }
  }
}

final case class Code private(code: String) extends AnyVal
object Code {
  def validateCode(codeO: Option[String]): AllErrorsOr[Code] = codeO match {
    case None => Error("Country name can't be empty.").invalidNel
    case Some(code) =>
      val pattern = """^[A-Z]{2}$""".r
      val codeU = code.toUpperCase
      codeU match {
        case pattern(_*) => Code(codeU).validNel
        case _ => Error("Country code must follow the pattern.").invalidNel
      }
  }
}

final case class Name private(name: String) extends AnyVal
object Name {
  def validateName(nameO: Option[String]): AllErrorsOr[Name] = nameO match {
    case None => Error("Country name can't be empty.").invalidNel
    case Some(name) => Name(name).validNel
  }
}

final case class Continent private(name: String) extends AnyVal
object Continent {
  def validateContinent(continentO: Option[String]): AllErrorsOr[Option[Continent]] = continentO match {
    case None => None.validNel
    case Some(continent) =>
      val pattern = """^AF|AN|AS|EU|NA|OC|SA$""".r
      val continentU = continent.toUpperCase
      continentU match {
        case pattern(_*) => Some(Continent(continentU)).validNel
        case _ => Error("Country continent is unknown.").invalidNel
      }
  }
}

final case class Keywords private(keywords: String) extends AnyVal
object Keywords{
  def validateKeywords(keywordsO: Option[String]): AllErrorsOr[Option[Keywords]] = keywordsO match {
    case None => None.validNel
    case Some(keywords) => Some(Keywords(keywords)).validNel
  }
}

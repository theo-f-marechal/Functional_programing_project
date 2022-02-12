package StructureCSV

import StructureCSV.AirportId.validateAirportId
import StructureCSV.AirportIdent.validateAirportIdent
import StructureCSV.AirportName.validateAirportName
import StructureCSV.AirportType.validateAirportType
import StructureCSV.Error.AllErrorsOr
import StructureCSV.IsoCountry.validateIsoCountry
import cats.implicits.{catsSyntaxTuple5Semigroupal, catsSyntaxValidatedId}


final case class Airport(id : AirportId,
                         airportIdent : AirportIdent,
                         airportType : AirportType,
                         name : AirportName,
                         isoCountry : IsoCountry)

object Airport{
  def deserialization(args : List[String]): AllErrorsOr[Airport] = args match {
    case Nil => Error("No arguments were given.").invalidNel
    case _ => (
      validateAirportId(args.headOption),
      validateAirportIdent(args.lift(1)),
      validateAirportType(args.lift(2)),
      validateAirportName(args.lift(3)),
      validateIsoCountry(args.lift(8)))
      .mapN((validId,validAirportIdent,validAirportType,validName,validIsoCountry) =>
        Airport(validId,validAirportIdent,validAirportType,validName,validIsoCountry))
  }
}


// Value class definition

final case class AirportId private(id: Long) extends AnyVal
object AirportId {
  def validateAirportId(idO : Option[String]): AllErrorsOr[AirportId] = idO match {
    case None => Error("Airport Id must exist.").invalidNel // Should never be trigger when creating csv
    case Some(id) =>
      if (id.isEmpty || id.isBlank) Error("Airport can't be empty or blank").invalidNel
      else id.toLongOption match {
        case None => Error("Airport Id must be a Long.").invalidNel
        case Some(idL) =>
          if (idL < 0) Error("Airport Id can't be negative.").invalidNel
          else AirportId(idL).validNel
      }
  }
}

final case class AirportIdent private(ident: String) extends AnyVal
object AirportIdent {
  def validateAirportIdent(identO: Option[String]): AllErrorsOr[AirportIdent] = identO match {
    case None => Error("Airport Ident must exist.").invalidNel
    case Some(ident) =>
      if (ident.isEmpty || ident.isBlank) Error("Airport Ident can't be empty or blank.").invalidNel
      else AirportIdent(ident).validNel
  }
}

final case class AirportType private(airportType: String) extends AnyVal
object AirportType{
  def validateAirportType(typeO: Option[String]): AllErrorsOr[AirportType] = typeO match {
    case None => Error("Airport type must exist.").invalidNel
    case Some(typ) =>
      if (typ.isEmpty || typ.isBlank) Error("Airport type can't be empty or blank.").invalidNel
      else AirportType(typ).validNel
  }
}

final case class AirportName private(name: String) extends AnyVal
object AirportName{
  def validateAirportName(nameO: Option[String]): AllErrorsOr[AirportName] = nameO match {
    case None => Error("Airport name must exist.").invalidNel
    case Some(name) =>
      if (name.isEmpty || name.isBlank) Error("Airport name can't be empty or blank.").invalidNel
      else AirportName(name).validNel
  }
}

final case class IsoCountry private(isoCountry: String) extends AnyVal
object IsoCountry {
  def validateIsoCountry(isoO: Option[String]): AllErrorsOr[IsoCountry] = isoO match {
    case None => Error("Country iso must exist.").invalidNel
    case Some(iso) =>
      if (iso.isEmpty || iso.isBlank) Error("Country iso can't be empty or blank.").invalidNel
      else {
        val pattern = """^[A-Z]{2}$""".r
        val isoU = iso.toUpperCase()
        isoU match {
          case pattern(_*) => IsoCountry(isoU).validNel
          case _ => Error("Country iso's format is incorrect.").invalidNel
        }
      }
  }
}





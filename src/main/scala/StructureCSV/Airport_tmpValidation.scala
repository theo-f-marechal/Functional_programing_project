package StructureCSV

import StructureCSV.AirportId.validateAirportId
import StructureCSV.AirportIdent.validateAirportIdent
import StructureCSV.AirportName.validateAirportName
import StructureCSV.AirportType.validateAirportType
import StructureCSV.Airport_tmpValidation.AllErrorsOr
import StructureCSV.IsoCountry.validateIsoCountry
import cats.data.ValidatedNel
import cats.implicits.{catsSyntaxTuple5Semigroupal, catsSyntaxValidatedId}


final case class Airport_tmpValidation(id : AirportId,
                                       airportIdent : AirportIdent,
                                       airportType : AirportType,
                                       name : AirportName,
                                       isoCountry : IsoCountry)
object Airport_tmpValidation{
  type AllErrorsOr[A] = ValidatedNel[AirportError, A]

  def deserialization(args : List[String]): AllErrorsOr[Airport_tmpValidation] = args match {
    case Nil => AirportError("No arguments were given.",EmptyListAirport).invalidNel
    case _ => (
      validateAirportId(args.headOption),
      validateAirportIdent(args.lift(1)),
      validateAirportType(args.lift(2)),
      validateAirportName(args.lift(3)),
      validateIsoCountry(args.lift(4)))
      .mapN((validId,validAirportIdent,validAirportType,validName,validIsoCountry) =>
        Airport_tmpValidation(validId,validAirportIdent,validAirportType,validName,validIsoCountry))
  }
}



// Error definition

sealed trait AirportErrorType
case object AirportIdInvalid extends AirportErrorType
case object AirportIdentInvalid extends AirportErrorType
case object AirportTypeInvalid extends AirportErrorType
case object AirportNameInvalid extends AirportErrorType
case object IsoCountryInvalid extends AirportErrorType
case object EmptyListAirport extends AirportErrorType

final case class AirportError(value: String, errorType: AirportErrorType)


// Value class definition

final case class AirportId private(id: Long) extends AnyVal
object AirportId {
  def validateAirportId(idO : Option[String]): AllErrorsOr[AirportId] = idO match {
    case None => AirportError("Airport Id can't be empty.",AirportIdInvalid).invalidNel
    case Some(id) => id.toLongOption match {
      case None => AirportError("Airport Id isn't a Long.",AirportIdInvalid).invalidNel
      case Some(idL) =>
        if (idL < 0) AirportError("Airport Id can't be negative.",AirportIdInvalid).invalidNel
        else AirportId(idL).validNel
    }
  }
}

final case class AirportIdent private(ident: String) extends AnyVal
object AirportIdent {
  def validateAirportIdent(identO: Option[String]): AllErrorsOr[AirportIdent] = identO match {
    case None => AirportError("Airport Ident can't be empty.",AirportIdentInvalid).invalidNel
    case Some(ident) => AirportIdent(ident).validNel
  }
}

final case class AirportType private(isoCountry: String) extends AnyVal
object AirportType{
  def validateAirportType(typeO: Option[String]): AllErrorsOr[AirportType] = typeO match {
    case None => AirportError("Airport type can't be empty.",AirportTypeInvalid).invalidNel
    case Some(typ) => AirportType(typ).validNel
  }
}

final case class AirportName private(name: String) extends AnyVal
object AirportName{
  def validateAirportName(nameO: Option[String]): AllErrorsOr[AirportName] = nameO match {
    case None => AirportError("Airport name can't be empty.",AirportNameInvalid).invalidNel
    case Some(name) => AirportName(name).validNel
  }
}

final case class IsoCountry private(isoCountry: String) extends AnyVal
object IsoCountry {
  def validateIsoCountry(isoO: Option[String]): AllErrorsOr[IsoCountry] = isoO match {
    case None => AirportError("County iso can't be empty.",IsoCountryInvalid).invalidNel
    case Some(iso) =>
      val pattern = """^[A-Z]{2}$""".r
      val isoU = iso.toUpperCase()
      isoU match {
        case pattern(_*) => IsoCountry(isoU).validNel
        case _ => AirportError("County iso's format i incorrect.",IsoCountryInvalid).invalidNel
      }
  }
}





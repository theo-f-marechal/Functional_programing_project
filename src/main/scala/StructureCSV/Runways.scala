package StructureCSV

import StructureCSV.AirportIdent.validateAirportIdent
import StructureCSV.AirportRef.validateAirportRef
import StructureCSV.Error.AllErrorsOr
import StructureCSV.LeIdent.validateLeIdent
import StructureCSV.RunwayId.validateRunwayId
import StructureCSV.Surface.validateSurface
import cats.implicits.{catsSyntaxTuple5Semigroupal, catsSyntaxValidatedId}

final case class Runways(id : RunwayId,
                          airportRef : AirportRef,
                          airportIdent : AirportIdent,
                          surface : Option[Surface],
                          leIdent : Option[LeIdent]
                        )
object Runways{
  def deserialization(args : List[String]): AllErrorsOr[Runways] = args match {
    case Nil => Error("No arguments were given.").invalidNel
    case _ => (
      validateRunwayId(args.headOption),
      validateAirportRef(args.lift(1)),
      validateAirportIdent(args.lift(2)),
      validateSurface(args.lift(3)),
      validateLeIdent(args.lift(4)))
      .mapN((validId, validAirportRef, validAirportIdent, validSurface, validLeIdent) =>
      Runways(validId,validAirportRef,validAirportIdent,validSurface,validLeIdent))
  }
}


case class RunwayId private(id: Long) extends AnyVal
object RunwayId {
  def validateRunwayId(idO: Option[String]): AllErrorsOr[RunwayId] = idO match {
    case None => Error("Runway Id can't be empty.").invalidNel
    case Some(id) => id.toLongOption match {
      case None => Error("Runway Id must be a Long.").invalidNel
      case Some(idL) =>
        if (idL < 0) Error("Runway Id can't be negative.").invalidNel
        else RunwayId(idL).validNel
    }
  }
}

case class AirportRef private(ref: Long) extends AnyVal
object AirportRef {
  def validateAirportRef(refO: Option[String]): AllErrorsOr[AirportRef] = refO match {
    case None => Error("Airport ref can't be empty.").invalidNel
    case Some(ref) => ref.toLongOption match {
      case None => Error("Airport Ref must be a Long.").invalidNel
      case Some(refL) =>
        if (refL < 0) Error("Airport ref can't be negative.").invalidNel
        else AirportRef(refL).validNel
    }
  }
}

final case class Surface private(surface: String) extends AnyVal
object Surface{
  def validateSurface(surfaceO: Option[String]): AllErrorsOr[Option[Surface]] = surfaceO match {
    case None => None.validNel
    case Some(surface) => Some(Surface(surface)).validNel
  }
}

final case class LeIdent private(leIdent: String) extends AnyVal
object LeIdent{
  def validateLeIdent(leIdentO: Option[String]): AllErrorsOr[Option[LeIdent]] = leIdentO match {
    case None => None.validNel
    case Some(leIdent) => Some(LeIdent(leIdent)).validNel
  }
}
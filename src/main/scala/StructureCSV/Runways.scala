package StructureCSV

import StructureCSV.AirportId.validateAirportId
import StructureCSV.AirportIdent.validateAirportIdent
import StructureCSV.Error.AllErrorsOr
import StructureCSV.LeIdent.validateLeIdent
import StructureCSV.RunwayId.validateRunwayId
import StructureCSV.Surface.validateSurface
import cats.implicits.{catsSyntaxTuple5Semigroupal, catsSyntaxValidatedId}

final case class Runways(id : RunwayId,
                         airportRef : AirportId,
                         airportIdent : AirportIdent,
                         surface : Option[Surface],
                         leIdent : Option[LeIdent]
                        )
object Runways{
  def deserialization(args : List[String]): AllErrorsOr[Runways] = args match {
    case Nil => Error("No arguments were given.").invalidNel
    case _ => (
      validateRunwayId(args.headOption),
      validateAirportId(args.lift(1)),
      validateAirportIdent(args.lift(2)),
      validateSurface(args.lift(5)),
      validateLeIdent(args.lift(8)))
      .mapN((validId, validAirportRef, validAirportIdent, validSurface, validLeIdent) =>
        Runways(validId,validAirportRef,validAirportIdent,validSurface,validLeIdent))
  }
}


case class RunwayId private(id: Long) extends AnyVal
object RunwayId {
  def validateRunwayId(idO: Option[String]): AllErrorsOr[RunwayId] = idO match {
    case None => Error("Runway Id must exist.").invalidNel
    case Some(id) =>
      if (id.isEmpty || id.isBlank) Error("Runway Id can't be empty or blank.").invalidNel
      else {
        id.toLongOption match {
          case None => Error("Runway Id must be a Long.").invalidNel
          case Some(idL) =>
            if (idL < 0) Error("Runway Id can't be negative.").invalidNel
            else RunwayId(idL).validNel
        }
      }
  }
}

final case class Surface private(surface: String) extends AnyVal
object Surface{
  def validateSurface(surfaceO: Option[String]): AllErrorsOr[Option[Surface]] = surfaceO match {
    case None => None.validNel
    case Some(surface) =>
      if (surface.isEmpty || surface.isBlank) None.validNel
      else Some(Surface(surface)).validNel
  }
}

final case class LeIdent private(leIdent: String) extends AnyVal
object LeIdent{
  def validateLeIdent(leIdentO: Option[String]): AllErrorsOr[Option[LeIdent]] = leIdentO match {
    case None => None.validNel
    case Some(leIdent) =>
      if (leIdent.isEmpty || leIdent.isBlank) None.validNel
      else Some(LeIdent(leIdent)).validNel
  }
}
package Structure_CSV

sealed trait Runway_argument

case class Runways(
                    id : RunawayId,
                    airportRef : AirportRef,
                    airportIdent : AirportIdent,
                    surface : Option[String],
                    leIdent : Option[String]
                  )


object Runways{

  def getArgument[A,B](elt : String)(fun1 : String => Option[A])(fun2 : A => Option[B]): Option[B] = {
    fun1(elt) match {
      case None => None
      case Some(x) => fun2(x)
    }
  }

  def deserialization(args: List[String]): Either[String, Runways] = {
    args match {
      case Nil => Left("ERROR => No argument was given")
      case a::b::c::t =>
        List(
          RunawayId.newId(a.toLongOption),
          AirportRef.newAirportRef(b.toLongOption),
          AirportIdent.newAirportIdent(Some(c)),
          t.lift(3), t.lift(4)
        ) match {
          case Nil => Left("ERROR => something went horribly wrong")
          case
            Some(id : RunawayId)::Some(ref : AirportRef)::Some(ident: AirportIdent)::
              (surface : Option[String])::(leIdent : Option[String])::_ =>
            Right(Runways(id,ref, ident, surface, leIdent))
          case _ =>
            Left("ERROR => The id, the airport reference, the airport ident," +
              " or a combination of the three is incorrect")
        }
      case _ =>
        Left("ERROR => The id, the airport reference, the airport ident," +
          " or a combination of the three is missing")
    }
  }
}



case class RunawayId private(id: Long) extends AnyVal
object RunawayId {
  def newId(id: Option[Long]): Option[RunawayId] = id match {
    case None => None
    case Some(x) if x < 0 => None
    case Some(x) => Some(RunawayId(x))
  }
}

case class AirportRef private(ref: Long) extends AnyVal
object AirportRef {
  def newAirportRef(ref: Option[Long]): Option[AirportRef] = ref match {
    case None => None
    case Some(x) => Some(AirportRef(x))
  }
}

case class AirportIdent private(ident: String) extends AnyVal
object AirportIdent {
  def newAirportIdent(ident: Option[String]): Option[AirportIdent] = ident match {
    case None => None
    case Some(x) => Some(AirportIdent(x))
  }
}
package StructureCSV

case class Airport(id : AirportId,
                    airportIdent : AirportIdent,
                    airportType : String,
                    name : String,
                    isoCountry : IsoCountry)
object Airport {
  def deserialization(args : List[String]) : Either[String, Airport] = {
    args match {
      case Nil => Left("ERROR : There is no argument")
      case id::ident::airportType::name::t =>
        List(
          AirportId.newId(id.toLongOption),
          AirportIdent.newAirportIdent(Option(ident)),
          airportType,
          name,
          IsoCountry.newIsoCountry(t.lift(4))
        ) match {
          case Nil => Left("ERROR : ID, Ident, Type, Name or Country Iso code are missing or wrong")
          case Some(id : AirportId) :: Some(ident : AirportIdent) ::
               (airportType : String) :: (name : String) ::
               Some(isoCountry: IsoCountry) :: _ => Right(Airport(id,ident,airportType,name,isoCountry))
          case _ => Left("ERROR : ID, Ident, Type, Name or Country Iso code are missing or wrong")
        }
      case _ => Left("ERROR : ID, Ident, Type, Name or Country Iso code are missing or wrong")
    }
  }
}

case class AirportId private(id: Long) extends AnyVal {
  override def toString: String = id.toString
}
object AirportId {
  def newId(x: Option[Long]): Option[AirportId] = x match {
    case None => None
    case Some(x) if x < 0 => None
    case Some(x) => Some(AirportId(x))
  }
}

case class AirportIdent private(ident: String) extends AnyVal {
  override def toString: String = ident
}
object AirportIdent {
  def newAirportIdent(ident: Option[String]): Option[AirportIdent] = ident match {
    case None => None
    case Some(x) => Some(AirportIdent(x))
  }
}

case class IsoCountry private(isoCountry: String) extends AnyVal {
  override def toString: String = isoCountry
}
object IsoCountry {
  def newIsoCountry(x: Option[String]): Option[IsoCountry] = {
    val pattern = """^[A-Z]{2}$""".r
    x match {
      case None => None
      case Some(x) => x.toUpperCase() match {
        case pattern(_*) => Some(IsoCountry(x.toUpperCase()))
        case _ => None
      }
    }
  }
}
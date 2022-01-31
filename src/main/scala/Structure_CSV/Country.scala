package Structure_CSV

case class Country(id : CountryId,
                   code : Code,
                   name : String,
                   continent : Option[Continent],
                   internetLink : Option[InternetLink],
                   keywords : Option[String])
object Country {
  def deserialization(args : List[String]) : Either[String, Country] = {
    args match {
      case Nil => Left("ERROR : There is no argument")
      case id::code::name::t =>
        List(
          CountryId.newId(id.toLongOption),
          Code.newCode(Option(code)),
          name,
          Continent.newContinent(t.lift(0)),
          InternetLink.newInternetLink(t.lift(1)),
          t.lift(2)
        ) match {
          case Nil => Left("ERROR : ID, country code or name is missing or wrong")
          case Some(id : CountryId) :: Some(code : Code) :: (name : String) ::
               Some(continent : Option[Continent]) :: Some(internetLink : Option[InternetLink]) ::
               Some(keywords : Option[String]) :: _ => Right(Country(id,code,name,continent,internetLink,keywords))
          case _ => Left("ERROR : ID, country code or name is missing or wrong")
        }
      case _ => Left("ERROR : ID, country code or name is missing or wrong")
    }
  }
}

case class CountryId private(id: Long) extends AnyVal
object CountryId {
  def newId(x: Option[Long]): Option[CountryId] = x match {
    case None => None
    case Some(x) if x < 0 => None
    case Some(x) => Some(CountryId(x))
  }
}

case class Code private(codeName: String) extends AnyVal
object Code {
  def newCode(x: Option[String]): Option[Code] = {
    val pattern = """^[A-Z]{2}$""".r
    x match {
      case None => None
      case Some(x) => x.toUpperCase() match {
        case pattern(_*) => Some(Code(x.toUpperCase()))
        case _ => None
      }
    }
  }
}

case class Continent private(continentName: String) extends AnyVal
object Continent {
  def newContinent(x: Option[String]): Option[Continent] = {
    val pattern = """^AF|AN|AS|EU|NA|OC|SA$""".r
    x match {
      case None => None
      case Some(x) => x.toUpperCase() match {
        case pattern(_*) => Some(Continent(x.toUpperCase()))
        case _ => None
      }
    }
  }
}

case class InternetLink private(link: String) extends AnyVal
object InternetLink {
  def newInternetLink(x: Option[String]): Option[InternetLink] = {
    val pattern = """^http[s]?:\/\/[a-z]*\.?[a-z]+[\.[a-z]+]?[\/[a-z]*]?$""".r
    x match {
      case None => None
      case Some(x) => x match {
        case pattern(_*) => Some(InternetLink(x))
        case _ => None
      }
    }
  }
}

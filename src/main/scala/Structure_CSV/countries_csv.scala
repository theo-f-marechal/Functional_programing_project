package Structure_CSV

case class countries_csv(id : Country_id,
                         code : Code,
                         name : String,
                         continent : Option[Continent],
                         internet_link : Option[Internet_link],
                         keywords : Option[String])

case class Country_id private(Id: Long) extends AnyVal
object Id {
  def new_id(x: Option[Long]): Option[Country_id] = x match {
    case None => None
    case Some(x) if x < 0 => None
    case Some(x) => Some(Country_id(x))
  }
}

case class Code private(code_name: String) extends AnyVal
object code {
  def new_code(x: Option[String]): Option[Code] = {
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

case class Continent private(continent_name: String) extends AnyVal
object continent {
  def new_continent(x: Option[String]): Option[Continent] = {
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

case class Internet_link private(link: String) extends AnyVal
object internet_link {
  def new_internet_link(x: Option[String]): Option[Internet_link] = {
    val pattern = """^http[s]?:\/\/[a-z]*\.?[a-z]+[\.[a-z]+]?[\/[a-z]*]?$""".r
    x match {
      case None => None
      case Some(x) => x match {
        case pattern(_*) => Some(Internet_link(x))
        case _ => None
      }
    }
  }
}

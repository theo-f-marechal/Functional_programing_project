package Structure_CSV

case class countries_csv(id : Option[id],
                         code : Option[code],
                         name : Option[name],
                         continent : Option[continent],
                         wikipedia_link : Option[wikipedia_link],
                         keywords : Option[keywords])

case class id private(id: Long) extends AnyVal
object id {
  def new_id(x: Option[Long]): Option[id] = x match {
    case None => None
    case _ if x.get < 0 => None
    case _ => Some(id(x.get))
  }
}

case class code private(code_name: String) extends AnyVal
object code {
  def new_code(x: Option[String]): Option[code] = x match {
    case None => None
    case _ => Some(code(x.get))
  }
}

case class name private(country_name: String) extends AnyVal
object name {
  def new_name(x: Option[String]): Option[name] = x match {
    case None => None
    case _ => Some(name(x.get))
  }
}

case class continent private(continent_name: String) extends AnyVal
object continent {
  def new_continent(x: Option[String]): Option[continent] = x match {
    case None => None
    case _ => Some(continent(x.get))
  }
}

case class wikipedia_link private(link: String) extends AnyVal
object wikipedia_link {
  def new_wikipedia_link(x: Option[String]): Option[wikipedia_link] = x match {
    case None => None
    case _ => Some(wikipedia_link(x.get))
  }
}

case class keywords private(key: String) extends AnyVal
object keywords {
  def new_keywords(x: Option[String]): Option[keywords] = x match {
    case None => None
    case _ => Some(keywords(x.get))
  }
}

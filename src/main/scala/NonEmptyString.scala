final class NonEmptyString private(val str: String) extends AnyVal
object NonEmptyString{
  def newNES(str : String): Option[NonEmptyString] = {
    if (str.isEmpty)
      None
    else
      Some(new NonEmptyString(str))
  }
}

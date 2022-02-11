package StructureCSV

import cats.data.ValidatedNel

final case class Error(value: String) extends AnyVal

object Error{
  type AllErrorsOr[A] = ValidatedNel[Error, A]

  def separateErrorsResults[E](listErrors : List[AllErrorsOr[E]]): (Int, List[(Int,String)], List[E]) = ???

  def formatError(errors : List[(Int, String)]): String = ???
}
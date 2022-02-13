package StructureCSV

import cats.data.Validated.{Invalid, Valid}
import cats.data.ValidatedNel

import scala.annotation.tailrec

final case class Error(value: String) extends AnyVal

object Error{
  type AllErrorsOr[A] = ValidatedNel[Error, A]

  def separateErrorsResults[E](listErrors : List[AllErrorsOr[E]]): (Long, List[String], List[E]) = {
    @tailrec
    def aux(list : List[AllErrorsOr[E]], nbLines: Long,
            nbV: Long, iList: List[String], vList: List[E]): (Long, List[String], List[E]) =
      list match {
        case Nil => (((nbV.toFloat / nbLines.toFloat) * 100).toLong, iList.reverse, vList.reverse) //
        case Valid(elt)::t => aux(t, nbLines+1, nbV+1, iList, elt::vList)
        case Invalid(elt)::t => aux(t, nbLines+1, nbV,
          (elt.foldLeft(" - Line no°" + nbLines.toString + " : ")((acc, x) => acc + x.value + " ") + " | end")::iList,
          vList)
      }
    aux(listErrors, 0, 0, Nil, Nil)
  }

  def formatError(errors : List[String], collection: String , pathCSV: String): String = {
    errors.foldLeft("### Errors for " + pathCSV + " as " + collection + " ###\n")((acc, elt) => acc + elt + "\n") +
      "### End of logs ###\n\n"
  }
}
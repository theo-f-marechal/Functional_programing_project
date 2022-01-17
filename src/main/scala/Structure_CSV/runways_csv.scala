package Structure_CSV

import scala.annotation.tailrec

sealed trait Runway_argument

case class Runways_csv(id : Runaway_id,
                        airport_ref : Airport_ref,
                        airport_ident : Airport_ident,
                        length_ft : Option[Length_ft],
                        width_ft : Option[Width_ft],
                        surface : Option[Surface],
                        lighted : Option[Lighted],
                        closed : Option[Closed],
                        le_ident : Option[Le_ident])


object Runways_csv{
  def deserialization(args: List[String]): Either[List[String], Runways_csv] = {
    @tailrec
    def transformArgs[Runway_argument](args: List[String],
                                       listF: List[String => Option[Runway_argument]],
                                       listArgT: List[Option[Runway_argument]] = Nil):
    List[Option[Runway_argument]] = {
      (args,listF,listArgT) match {
        case (Nil,_,Nil) => List()
        case (Nil,_,_) => listArgT.reverse
        case (arg::tArg,function::tF,_) => transformArgs(tArg,tF,function(arg)::listArgT)
      }
    }

    @tailrec
    def checkCondition(argT : List[Option[Runway_argument]],
                       possibleErrors: List[String],
                       errorsFound : List[String] = Nil): List[String] =
      (argT, possibleErrors) match {
        case (Nil,_) => errorsFound.reverse
        case (None::t1, h::t2) => checkCondition(t1,t2,h::errorsFound)
        case (_::t1, _::t2) => checkCondition(t1,t2,errorsFound)
    }

    def makeOptionalArgument[A,B](fun : A => B, x : Option[A]) : Option[B] = x match{
      case None => None
      case Some(v) => Some(fun(v))
    }

    val listF = List(
      {x : String => Runaway_id.new_id(x.toLongOption)},
      {x : String => Airport_ref.new_airport_ref(x.toLongOption)},
      {x : String => Airport_ident.new_airport_ident(Option(x))},
      {x : String => makeOptionalArgument(Length_ft,x.toIntOption)},
      {x : String => makeOptionalArgument(Width_ft,x.toIntOption)},
      {x : String => makeOptionalArgument(Surface,Option(x))},
      {x : String => makeOptionalArgument(Lighted,x.toIntOption)},
      {x : String => makeOptionalArgument(Closed,x.toIntOption)},
      {x : String => makeOptionalArgument(Le_ident,Option(x))})
    val argsT = transformArgs(args,listF)

    val possibleErrors = List(
      "Missing id", "Missing ref", "Missing ident",
      "Missing length_ft", "Missing width_ft",
      "Missing surface", "Missing lighted",
      "Missing closed", "Missing le_ident")

    val errors = checkCondition(argsT, possibleErrors)

    def liftOptionalArg(t : List[Option[Runway_argument]], i : Int): Option[Runway_argument] = {
      val re = t.lift(i)
      re match {
        case None => None
        case Some(x) => x
      }
    }

    argsT match {
      case Nil => Left("No argument given" :: errors)
      case l if l.length < 3 => Left(errors)
      case a::b::c::_ if a.isEmpty | b.isEmpty | c.isEmpty => Left(errors)
      case Some(id : Runaway_id)::Some(ref : Airport_ref)::Some(ident : Airport_ident) ::
        Some(lenght : Length_ft) :: Some(width : Width_ft) :: Some(surface : Surface) ::
        Some(light : Lighted) :: Some(closed : Closed) :: Some(le_ident : Le_ident) :: _
      => Right(Runways_csv(id,ref,ident,Some(lenght),Some(width),Some(surface),Some(light),Some(closed),Some(le_ident)))
    }
  }
}



case class Runaway_id private(id: Long) extends AnyVal with Runway_argument
object Runaway_id {
  def new_id(id: Option[Long]): Option[Runaway_id] = id match {
    case None => None
    case Some(x) if x < 0 => None
    case Some(x) => Some(Runaway_id(x))
  }
}

case class Airport_ref private(ref: Long) extends AnyVal with Runway_argument
object Airport_ref {
  def new_airport_ref(ref: Option[Long]): Option[Airport_ref] = ref match {
    case None => None
    case Some(x) => Some(Airport_ref(x))
  }
}

case class Airport_ident private(ident: String) extends AnyVal with Runway_argument
object Airport_ident {
  def new_airport_ident(ident: Option[String]): Option[Airport_ident] = ident match {
    case None => None
    case Some(x) => Some(Airport_ident(x))
  }
}

case class Length_ft(len : Int) extends AnyVal with Runway_argument
case class Width_ft(len : Int) extends AnyVal with Runway_argument
case class Surface(len : String) extends AnyVal with Runway_argument
case class Lighted(len : Int) extends AnyVal with Runway_argument
case class Closed(len : Int) extends AnyVal with Runway_argument
case class Le_ident(len : String) extends AnyVal with Runway_argument
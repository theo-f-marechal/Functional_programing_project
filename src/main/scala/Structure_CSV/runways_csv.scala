package Structure_CSV

case class runways_csv(id : Option[id],
                        airport_ref : Option[airport_ref],
                        airport_ident : Option[airport_ident],
                        length_ft : Option[length_ft],
                        width_ft : Option[width_ft],
                        surface : Option[surface],
                        lighted : Option[lighted],
                        closed : Option[closed],
                        le_ident : Option[le_ident],
                        le_latitude_deg : Option[le_latitude_deg],
                        le_longitude_deg : Option[le_longitude_deg],
                        le_elevation_ft : Option[le_elevation_ft],
                        le_heading_degT : Option[le_heading_degT],
                        le_displaced_threshold_ft : Option[le_displaced_threshold_ft],
                        he_ident : Option[he_ident],
                        he_latitude_deg : Option[he_latitude_deg],
                        he_longitude_deg : Option[he_longitude_deg],
                        he_elevation_ft : Option[he_elevation_ft],
                        he_heading_degT : Option[he_heading_degT],
                        he_displaced_threshold_ft : Option[he_displaced_threshold_ft])


case class id private(id: Long) extends AnyVal
object id {
  def new_id(x: Option[Long]): Option[id] = x match {
    case None => None
    case _ if x.get < 0 => None
    case _ => Some(id(x.get))
  }
}

case class airport_ref private(ref: Long) extends AnyVal
object airport_ref {
  def new_airport_ref(x: Option[Long]): Option[airport_ref] = x match {
    case None => None
    case _ => Some(airport_ref(x.get))
  }
}

case class airport_ident private(ident: String) extends AnyVal
object airport_ident {
  def new_airport_ident(x: Option[String]): Option[airport_ident] = x match {
    case None => None
    case _ => Some(airport_ident(x.get))
  }
}

case class length_ft private(length: Float) extends AnyVal
object length_ft {
  def new_length_ft(x: Option[Float]): Option[length_ft] = x match {
    case None => None
    case _ if x.get < 0 => None
    case _ => Some(length_ft(x.get))
  }
}

case class width_ft private(width: Float) extends AnyVal
object width_ft {
  def new_width_ft(x: Option[Float]): Option[width_ft] = x match {
    case None => None
    case _ if x.get < 0 => None
    case _ => Some(width_ft(x.get))
  }
}

case class surface private(typeSurface: String) extends AnyVal
object surface {
  def new_surface(x: Option[String]): Option[surface] = x match {
    case None => None
    case _ => Some(surface(x.get))
  }
}

case class lighted private(light: Int) extends AnyVal
object lighted {
  def new_lighted(x: Option[String]): Option[airport_ident] = x match {
    case None => None
    case _ => Some(airport_ident(x.get))
  }
}

case class closed private(close: Int) extends AnyVal
object closed {
  def new_closed(x: Option[Int]): Option[closed] = x match {
    case None => None
    case _ => Some(closed(x.get))
  }
}

case class le_ident private(indent: Float) extends AnyVal
object le_ident {
  def new_le_ident(x: Option[Float]): Option[le_ident] = x match {
    case None => None
    case _ => Some(le_ident(x.get))
  }
}

case class le_latitude_deg private(lat_d: Float) extends AnyVal
object le_latitude_deg {
  def new_le_latitude_deg(x: Option[Float]): Option[le_latitude_deg] = x match {
    case None => None
    case _ => Some(le_latitude_deg(x.get))
  }
}

case class le_longitude_deg private(long_d: Float) extends AnyVal
object le_longitude_deg {
  def new_le_longitude_deg(x: Option[Float]): Option[le_longitude_deg] = x match {
    case None => None
    case _ => Some(le_longitude_deg(x.get))
  }
}

case class le_elevation_ft private(elevation: Float) extends AnyVal
object le_elevation_ft {
  def new_le_elevation_ft(x: Option[Float]): Option[le_elevation_ft] = x match {
    case None => None
    case _ => Some(le_elevation_ft(x.get))
  }
}

case class le_heading_degT private(heading: Float) extends AnyVal
object le_heading_degT {
  def new_le_heading_degT(x: Option[Float]): Option[le_heading_degT] = x match {
    case None => None
    case _ => Some(le_heading_degT(x.get))
  }
}

case class le_displaced_threshold_ft private(displaced_thr: Float) extends AnyVal
object le_displaced_threshold_ft {
  def new_le_displaced_threshold_ft(x: Option[Float]): Option[le_displaced_threshold_ft] = x match {
    case None => None
    case _ => Some(le_displaced_threshold_ft(x.get))
  }
}

case class he_ident private(indent: Float) extends AnyVal
object he_ident {
  def new_he_ident(x: Option[Float]): Option[he_ident] = x match {
    case None => None
    case _ => Some(he_ident(x.get))
  }
}

case class he_latitude_deg private(lat_d: Float) extends AnyVal
object he_latitude_deg {
  def new_he_latitude_deg(x: Option[Float]): Option[he_latitude_deg] = x match {
    case None => None
    case _ => Some(he_latitude_deg(x.get))
  }
}

case class he_longitude_deg private(lon_d: Float) extends AnyVal
object he_longitude_deg {
  def new_he_longitude_deg(x: Option[Float]): Option[he_longitude_deg] = x match {
    case None => None
    case _ => Some(he_longitude_deg(x.get))
  }
}

case class he_elevation_ft private(elevation: Float) extends AnyVal
object he_elevation_ft {
  def new_he_elevation_ft(x: Option[Float]): Option[he_elevation_ft] = x match {
    case None => None
    case _ => Some(he_elevation_ft(x.get))
  }
}

case class he_heading_degT private(heading: Float) extends AnyVal
object he_heading_degT {
  def new_he_heading_degT(x: Option[Float]): Option[he_heading_degT] = x match {
    case None => None
    case _ => Some(he_heading_degT(x.get))
  }
}

case class he_displaced_threshold_ft private(displaced_thr: Float) extends AnyVal
object he_displaced_threshold_ft {
  def new_he_displaced_threshold_ft(x: Option[Float]): Option[he_displaced_threshold_ft] = x match {
    case None => None
    case _ => Some(he_displaced_threshold_ft(x.get))
  }
}
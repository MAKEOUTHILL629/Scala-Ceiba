package co.com.infraestructura.error

trait TipoError extends Product with Serializable

trait TipoErrorAplicacion extends TipoError

object TipoErrorAplicacion {
  def apply(value: String): TipoErrorAplicacion = value match {
    case "Tecnico" => Tecnico
    case "Negocio" => Negocio
    case "Aplicacion" => Aplicacion
    case _ => TipoErrorDesconocido
  }

  def unapply(value: TipoErrorAplicacion): String = value match {
    case Tecnico => "Tecnico"
    case Negocio => "Negocio"
    case Aplicacion => "Aplicacion"
    case _ => "Error no reconocido"
  }
}

case object Tecnico extends TipoErrorAplicacion

case object Negocio extends TipoErrorAplicacion

case object Aplicacion extends TipoErrorAplicacion

case object TipoErrorDesconocido extends TipoErrorAplicacion

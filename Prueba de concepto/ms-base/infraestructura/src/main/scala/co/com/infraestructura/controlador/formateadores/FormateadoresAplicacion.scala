package co.com.infraestructura.controlador.formateadores

import co.com.aplicacion.comando.ComandoUsuario
import co.com.dominio.dtos.DtoUsuario
import play.api.libs.json.{ Format, Json }

trait FormateadoresAplicacion {

  implicit val dtoUsuarioFormat: Format[DtoUsuario] = Json.format[DtoUsuario]

  implicit val comandoUsuarioFormat: Format[ComandoUsuario] = Json.format[ComandoUsuario]

}


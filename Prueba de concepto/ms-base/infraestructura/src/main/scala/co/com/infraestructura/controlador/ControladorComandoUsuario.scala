package co.com.infraestructura.controlador

import co.com.aplicacion.comando.ComandoUsuario
import co.com.aplicacion.manejador.{ ManejadorActualizarUsuario, ManejadorCrearUsuario, ManejadorEliminarUsuario }
import co.com.infraestructura.controlador.formateadores.FormateadoresAplicacion
import io.swagger.annotations.{ Api, ApiOperation }
import javax.inject.Inject
import play.api.mvc.{ AbstractController, AnyContent, ControllerComponents, Request }

@Api(value = "/usuarios", tags = Array("Controlador comando de  usuarios"))
class ControladorComandoUsuario @Inject() (
  cc: ControllerComponents,
  manejadorCrearUsuario: ManejadorCrearUsuario,
  manejadorEliminarUsuario: ManejadorEliminarUsuario,
  manejadorActualizarUsuario: ManejadorActualizarUsuario)
  extends AbstractController(cc)
  with FormateadoresAplicacion {

  @ApiOperation("Crear usuario")
  def crear() = Action.async(parse.json[ComandoUsuario]) { implicit request: Request[ComandoUsuario] =>
    manejadorCrearUsuario.ejecutar(request.body)
  }

  @ApiOperation("eliminar usuario")
  def eliminar(id: Long) = Action.async { implicit request: Request[AnyContent] =>
    manejadorEliminarUsuario.ejecutar(id)
  }

  @ApiOperation("actualizar usuario")
  def actualizar() = Action.async(parse.json[ComandoUsuario]) { implicit request: Request[ComandoUsuario] =>
    manejadorActualizarUsuario.ejecutar(request.body)
  }

}

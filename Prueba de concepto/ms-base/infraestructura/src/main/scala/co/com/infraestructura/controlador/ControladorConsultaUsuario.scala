package co.com.infraestructura.controlador

import co.com.aplicacion.manejador.{ ManejadorConsultaUsuario, ManejadorListaUsuario }
import co.com.infraestructura.controlador.formateadores.FormateadoresAplicacion
import io.swagger.annotations.{ Api, ApiOperation,  }
import javax.inject.Inject
import play.api.mvc.{ AbstractController, AnyContent, ControllerComponents, Request }

@Api(value = "/usuarios", tags = Array("Controlador consulta de  usuarios"))
class ControladorConsultaUsuario @Inject() (
  cc: ControllerComponents,
  manejadorListaUsuario: ManejadorListaUsuario,
  manejadorConsultaUsuario: ManejadorConsultaUsuario)
  extends AbstractController(cc) with FormateadoresAplicacion {

  @ApiOperation("listar todos usuario")
  def listar() = Action.async { implicit request: Request[AnyContent] =>
    manejadorListaUsuario.ejecutar()
  }

  @ApiOperation("buscar usuario por id")
  def consultar(id: Long) = Action.async { implicit request: Request[AnyContent] =>
    manejadorConsultaUsuario.ejecutar(id)
  }

}

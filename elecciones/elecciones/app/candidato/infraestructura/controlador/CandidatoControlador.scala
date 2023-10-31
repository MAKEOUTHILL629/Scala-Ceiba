package candidato.infraestructura.controlador

import candidato.aplicacion.ObtenerCantidados
import candidato.dominio.modelos.Candidato
import play.api.cache.Cached
import play.api.mvc._
import play.api.libs.json.Json

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class CandidatoControlador @Inject()(val controllerComponents: ControllerComponents,  obtenerCantidados: ObtenerCantidados, cached: Cached)(implicit executionContext: ExecutionContext)  extends BaseController {


  def obtenerCantidadosElecciones() = cached("candidatos") {
    Action.async { implicit request: Request[AnyContent] =>
      val fCandidatos: Future[Seq[Candidato]] = obtenerCantidados.all()

      fCandidatos.map(s => Ok(Json.toJson(s)))
    }
  }
}

package candidato.infraestructura.controlador


import akka.stream.Materializer
import candidato.aplicacion.ObtenerCantidados
import candidato.dominio.modelos.Candidato
import play.api.cache.Cached
import play.api.mvc._
import play.api.libs.json.Json

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class CandidatoControlador @Inject()(val cc: ControllerComponents,  obtenerCantidados: ObtenerCantidados, cached: Cached)(implicit executionContext: ExecutionContext, implicit val materializer: Materializer)  extends AbstractController(cc){


  def obtenerCantidadosElecciones(): EssentialAction = cached("candidatos") {
    Action.async { implicit request: Request[AnyContent] =>
      val fCandidatos: Future[Seq[Candidato]] = obtenerCantidados.all()

      fCandidatos.map(s => Ok(Json.toJson(s)))
    }
  }
}

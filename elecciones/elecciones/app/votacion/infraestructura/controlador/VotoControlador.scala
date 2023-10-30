package votacion.infraestructura.controlador

import play.api.mvc.{BaseController, ControllerComponents}
import votacion.aplicacion.VotarCandidato
import votacion.dominio.modelos.Voto

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class VotoControlador @Inject()(val controllerComponents: ControllerComponents, votarCandidatoCaso: VotarCandidato)(implicit executionContext: ExecutionContext)  extends BaseController{
  def votarCandidato() = Action.async(parse.json[Voto]) { implicit request =>

    val fVoto =  votarCandidatoCaso.votar(request.body)

    fVoto.map(resultado=> Created("Voto guardado"))
      .recover{
        case e: IllegalArgumentException => BadRequest(e.getMessage)
      }



  }
}
package votacion.aplicacion
import votacion.dominio.modelos.Voto
import votacion.dominio.repositorio.VotoRepositorio

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
class VotarCandidato @Inject()(repositorio: VotoRepositorio)(implicit ec: ExecutionContext){

  def votar(voto: Voto): Future[Future[Unit]] = {

    repositorio.findByCedula(voto.cedula).map {
      case Some(voto) => throw new IllegalArgumentException(s"La cedula ${voto.cedula} ya voto")
      case None => repositorio.insertar(voto)
    }

  }

}

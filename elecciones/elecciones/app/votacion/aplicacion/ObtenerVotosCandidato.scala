package votacion.aplicacion

import compartido.modelos.VotosCandidato
import votacion.dominio.repositorio.VotoRepositorio

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ObtenerVotosCandidato @Inject()(repositorio: VotoRepositorio)(implicit ec: ExecutionContext){

    def totalVotosCandidatos(): Future[Seq[VotosCandidato]] = repositorio.totalVotosCandidatos()
}

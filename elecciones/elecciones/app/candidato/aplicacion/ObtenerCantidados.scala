package candidato.aplicacion

import candidato.dominio.modelos.Candidato
import candidato.dominio.repositorio.CandidatoRepositorio

import javax.inject.Inject
import scala.concurrent.Future

class ObtenerCantidados @Inject()(repositorio: CandidatoRepositorio){

  def all(): Future[Seq[Candidato]] = repositorio.all()

}

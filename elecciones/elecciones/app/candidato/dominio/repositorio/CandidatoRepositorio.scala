package candidato.dominio.repositorio


import candidato.dominio.modelos.Candidato
import scala.concurrent.Future

trait CandidatoRepositorio {
  def all() : Future[Seq[Candidato]]
}

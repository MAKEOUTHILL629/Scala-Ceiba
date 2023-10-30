package votacion.dominio.repositorio

import compartido.modelos.VotosCandidato
import votacion.dominio.modelos.Voto

import scala.concurrent.Future

trait VotoRepositorio {
  def insertar(voto: Voto): Future[Unit]


  def findByCedula(cedula: String): Future[Option[Voto]]
    // Retorna un fururo de la lista de votos es decir un map con nombreCandiato tipo string, totalVotos tipo int
  def totalVotosCandidatos(): Future[Seq[VotosCandidato]]
}

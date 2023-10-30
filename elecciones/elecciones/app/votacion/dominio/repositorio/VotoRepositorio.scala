package votacion.dominio.repositorio

import votacion.dominio.modelos.Voto

import scala.concurrent.Future

trait VotoRepositorio {
  def insertar(voto: Voto): Future[Unit]


  def findByCedula(cedula: String): Future[Option[Voto]]
}

package co.com.dominio.persistencia.dao

import co.com.dominio.dtos.DtoUsuario
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.modelo.Usuario.IdUsuario
import monix.eval.Task

import scala.concurrent.Future

/**
 * clase encargada de realizar consultas de usuarios
 */
trait DaoUsuario {

  /**
   * Permite listar usuarios
   *
   * @return los usuarios
   */
  def listar: Task[List[DtoUsuario]]

  /**
   * permite consultar usuario por id
   * @param id
   * @return
   */
  def buscarPorId(id: IdUsuario): Respuesta[DtoUsuario]

}

package co.com.dominio.persistencia.repositorio

import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.modelo.Usuario
import co.com.dominio.modelo.Usuario.IdUsuario
import co.com.dominio.persistencia.RepositorioBase
import monix.eval.Task

import scala.concurrent.Future

/**
 * repositorio encargado de la persistencia de los usuarios
 */
trait RepositorioUsuario extends RepositorioBase[Usuario] {

  /**
   * Permite insertar un usuario
   *
   * @param usuario
   */
  def insertar(usuario: Usuario): Respuesta[IdUsuario]

  /**
   * Permite actualizar un usuario
   *
   * @param usuario
   */
  def actualizar(usuario: Usuario): Respuesta[Long]

  /**
   * Permite eliminar un usuario
   *
   * @param id
   */
  def eliminar(id: IdUsuario): Respuesta[Long]

  /**
   * Permite validar si existe un usuario con un nombre
   *
   * @param id
   * @return si existe o no
   */
  def existe(id: IdUsuario): Task[Boolean]

}

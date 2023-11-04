package co.com.aplicacion.fabrica

import co.com.aplicacion.comando.ComandoUsuario
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Usuario
import monix.eval.Task

object FabricaUsuario {

  def crear(comandoUsuario: ComandoUsuario): Task[Either[ErrorDominio, Usuario]] = {
    Task.now(Usuario.validarCrearUsuario(comandoUsuario.id, comandoUsuario.nombre, comandoUsuario.clave, comandoUsuario.fechaCreacion))
  }

}

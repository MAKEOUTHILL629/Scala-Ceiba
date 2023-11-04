package co.com.dominio.modelo

import java.time.LocalDateTime

import cats.data.Validated._
import cats.implicits._
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Usuario.IdUsuario

final case class Usuario(id: IdUsuario, nombre: String, clave: String, fechaCreacion: LocalDateTime)

object Usuario {
  type IdUsuario = Long

  def validarCrearUsuario(id: IdUsuario, nombre: Option[String], clave: Option[String], fechaCreacion: Option[LocalDateTime]): Either[ErrorDominio, Usuario] =
    (
      toValidatedNel(id),
      validarObligatorio(nombre, "nombre obligatorio"),
      validarObligatorio(clave, "clave obligatorio"),
      validarObligatorio(fechaCreacion, "fechaCreacion obligatorio")).mapN(Usuario(_, _, _, _))

}
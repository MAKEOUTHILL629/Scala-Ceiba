package co.com.aplicacion.comando

import java.time.LocalDateTime

import co.com.dominio.modelo.Usuario.IdUsuario

case class ComandoUsuario(id: IdUsuario, nombre: Option[String], clave: Option[String], fechaCreacion: Option[LocalDateTime])
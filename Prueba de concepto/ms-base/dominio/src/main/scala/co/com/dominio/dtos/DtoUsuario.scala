package co.com.dominio.dtos

import java.time.LocalDateTime

case class DtoUsuario(nombre: Option[String], clave: Option[String], fechaCreacion: Option[LocalDateTime])

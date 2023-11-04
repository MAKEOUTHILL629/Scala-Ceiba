package co.com.infraestructura.persistencia.dao

import co.com.dominio.dtos.DtoUsuario

object TransformadorDtoUsuario {
  implicit def fromRegistroToDtoUsuario(datos: (Long, String, String, Option[java.sql.Date])): DtoUsuario =
    DtoUsuario(Some(datos._2), Some(datos._3), datos._4.map(d => d.toLocalDate.atStartOfDay()))

  implicit def toGeneracionMap(register: Seq[(Long, String, String, Option[java.sql.Date])]): List[DtoUsuario] =
    register.map(fromRegistroToDtoUsuario).toList
}

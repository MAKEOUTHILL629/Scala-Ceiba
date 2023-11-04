package co.com.infraestructura.persistencia.tablas

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait Tablas {
  self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._

  val usuarios = TableQuery[UserTable]

  class UserTable(tag: Tag) extends Table[(Long, String, String, Option[java.sql.Date])](tag, "USUARIO") {

    def id = column[Long]("ID", O.PrimaryKey)
    def nombre = column[String]("NOMBRE")
    def clave = column[String]("CLAVE")
    def fecreacion = column[Option[java.sql.Date]]("FECHACREACION")

    def * = (id, nombre, clave, fecreacion)

  }

}

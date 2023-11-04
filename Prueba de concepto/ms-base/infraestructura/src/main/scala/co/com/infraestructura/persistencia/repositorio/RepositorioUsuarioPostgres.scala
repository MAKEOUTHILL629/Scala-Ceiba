package co.com.infraestructura.persistencia.repositorio

import cats.data.EitherT
import cats.implicits._
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.modelo.Usuario
import co.com.dominio.modelo.Usuario.IdUsuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario
import co.com.infraestructura.persistencia.tablas.Tablas
import co.com.infraestructura.repositorio.SlickRepositorio
import javax.inject.Inject
import monix.eval.Task
import play.api.db.slick.DatabaseConfigProvider

class RepositorioUsuarioPostgres @Inject() (val dbConfigProvider: DatabaseConfigProvider) extends RepositorioUsuario
  with SlickRepositorio with Tablas {

  import co.com.infraestructura.FutureTransformador._
  import profile.api._

  override def insertar(usuario: Usuario): Respuesta[IdUsuario] = EitherT {
    val datos = obtenerdatos(usuario)
    val query = usuarios += (datos)
    db.run(query.transactionally).deferFuture.map(_ => usuario.id.asRight[ErrorDominio])
  }

  override def actualizar(usuario: Usuario): Respuesta[Long] = EitherT {
    val datos = obtenerdatos(usuario)
    val query = usuarios.filter(b => b.id === usuario.id).update(datos)
    db.run(query.transactionally).deferFuture.map(_ => usuario.id.asRight[ErrorDominio])
  }

  override def eliminar(id: Long): Respuesta[Long] = EitherT {
    db.run(usuarios.filter(_.id === id).delete).deferFuture.map(_ => id.asRight[ErrorDominio])
  }

  override def existe(id: Long): Task[Boolean] = {
    val s = usuarios.filter(b => b.id === id).exists
    db.run(s.result).deferFuture
  }

  private def obtenerdatos(usuario: Usuario) = {
    (usuario.id, usuario.nombre, usuario.clave, Some(java.sql.Date.valueOf(usuario.fechaCreacion.toLocalDate)))
  }

}

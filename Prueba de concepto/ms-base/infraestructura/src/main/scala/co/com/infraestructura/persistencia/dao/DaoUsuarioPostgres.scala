package co.com.infraestructura.persistencia.dao

import cats.data.EitherT
import cats.implicits._
import co.com.dominio.dtos.DtoUsuario
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.persistencia.dao.DaoUsuario
import co.com.infraestructura.persistencia.tablas.Tablas
import co.com.infraestructura.repositorio.SlickDao
import javax.inject.Inject
import monix.eval.Task
import play.api.db.slick.DatabaseConfigProvider

class DaoUsuarioPostgres @Inject() (val dbConfigProvider: DatabaseConfigProvider) extends DaoUsuario
  with SlickDao with Tablas {

  import co.com.infraestructura.FutureTransformador._
  import co.com.infraestructura.persistencia.dao.TransformadorDtoUsuario._

  import profile.api._

  override def listar: Task[List[DtoUsuario]] = {
    val query = usuarios.result
    db.run(query).deferFuture.map(toGeneracionMap)
  }

  override def buscarPorId(id: Long): Respuesta[DtoUsuario] = EitherT {
    val query = usuarios.filter(usuario => usuario.id === id).result
    db.run(query).deferFuture.map(x => x.headOption match {
      case None => ErrorDominio.noExiste().asLeft
      case Some(dato) => fromRegistroToDtoUsuario(dato).asRight
    })

  }

}

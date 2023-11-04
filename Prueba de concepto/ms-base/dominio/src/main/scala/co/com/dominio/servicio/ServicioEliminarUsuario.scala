package co.com.dominio.servicio

import cats.data.EitherT
import cats.implicits._
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.modelo.Usuario.IdUsuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario

class ServicioEliminarUsuario {

  def eliminar(id: Long): RepoReaderT[Long, ErrorDominio] = reader {
    case repo: RepositorioUsuario =>
      val r = for {
        _ <- validarExiste(id)(repo)
        creado <- repo.eliminar(id)
      } yield creado
      r
  }

  private def validarExiste(id: IdUsuario)(repo: RepositorioUsuario): Respuesta[Boolean] = EitherT {
    repo.existe(id).map(
      existe => existe match {
        case true => true.asRight
        case false => ErrorDominio.noExiste().asLeft

      })
  }
}

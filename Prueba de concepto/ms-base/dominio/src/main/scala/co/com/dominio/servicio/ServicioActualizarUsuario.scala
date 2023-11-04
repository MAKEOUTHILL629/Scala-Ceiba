package co.com.dominio.servicio

import cats.data.EitherT
import cats.implicits._
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.modelo.Usuario
import co.com.dominio.modelo.Usuario.IdUsuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario

class ServicioActualizarUsuario {

  def actualizar(usuario: Usuario): RepoReaderT[Long, ErrorDominio] = reader {
    case repo: RepositorioUsuario =>
      for {
        _ <- validarExiste(usuario.id)(repo)
        creado <- repo.actualizar(usuario)
      } yield creado
  }

  private def validarExiste(id: IdUsuario)(repo: RepositorioUsuario): Respuesta[Boolean] = EitherT {
    repo.existe(id).map(
      existe => existe match {
        case true => true.asRight
        case false => ErrorDominio.noExiste().asLeft

      })
  }
}

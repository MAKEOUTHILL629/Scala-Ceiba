package co.com.dominio.servicio

import cats.data.EitherT
import cats.implicits._
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.modelo.Usuario
import co.com.dominio.modelo.Usuario.IdUsuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario

class ServicioCrearUsuario {

  def crear(usuario: Usuario): RepoReaderT[IdUsuario, ErrorDominio] = reader {
    case repo: RepositorioUsuario =>
      for {
        _ <- validarExiste(usuario.id)(repo)
        creado <- repo.insertar(usuario)
      } yield creado
  }

  private def validarExiste(id: IdUsuario)(repo: RepositorioUsuario): Respuesta[Boolean] = EitherT {
    repo.existe(id).map(
      existe => existe match {
        case true => ErrorDominio.existe().asLeft
        case false => false.asRight
      })
  }

}

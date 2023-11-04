package co.com.dominio

import cats.data.{ EitherT, NonEmptyList, Reader }
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.persistencia.RepositorioBase
import monix.eval.Task

import scala.concurrent.Future
import scala.language.higherKinds

package object servicio {

  type RepoReaderFuture[D] = Reader[RepositorioBase[_], Future[D]]

  type RepoReaderT[D, E <: ErrorDominio] = Reader[RepositorioBase[_], EitherT[Task, E, D]]

  def readerFuture[D](f: RepositorioBase[_] => Future[D]): RepoReaderFuture[D] =
    Reader[RepositorioBase[_], Future[D]](f)

  def reader[D, E <: ErrorDominio](f: RepositorioBase[_] => EitherT[Task, E, D]): RepoReaderT[D, E] =
    Reader[RepositorioBase[_], EitherT[Task, E, D]](f)

  def toEmptyList(error: ErrorDominio): NonEmptyList[ErrorDominio] = NonEmptyList.one(error)
}

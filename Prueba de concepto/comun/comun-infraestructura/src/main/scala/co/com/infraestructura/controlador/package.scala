package co.com.infraestructura

import co.com.aplicacion.ComandoRespuesta
import co.com.aplicacion.manejador.RespuestaComando
import co.com.dominio.errores.ErrorDominio.{ ElementoDuplicado, ElementoNoExiste }
import co.com.dominio.errores.{ ErrorDominio, ErroresDominio }
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.infraestructura.error.Error._
import co.com.infraestructura.error.{ Aplicacion, Negocio }
import monix.execution.ExecutionModel.AlwaysAsyncExecution
import monix.execution.Scheduler
import play.api.libs.json.{ Json, Writes }
import play.api.mvc.Result

import scala.concurrent.Future

package object controlador {
  import play.api.mvc.Results._

  implicit val scheduler = Scheduler.fixedPool(name = "main-pool", poolSize = 10, executionModel = AlwaysAsyncExecution)

  implicit def searchResultsWrites[T](implicit fmt: Writes[T]): Writes[ComandoRespuesta[T]] = new Writes[ComandoRespuesta[T]] {
    def writes(ts: ComandoRespuesta[T]) = Json.obj(
      "respuesta" -> ts.respuesta)
  }

  implicit def tranformarComando[T](s: RespuestaComando[T])(implicit tjs: play.api.libs.json.Writes[T]): Future[Result] = {
    s.fold(
      { err => clasificarError(err)
      },
      { respuesta => Ok(Json.toJson(respuesta)) }).runToFuture
  }

  implicit def tranformar[T](s: Respuesta[T])(implicit tjs: play.api.libs.json.Writes[T]): Future[Result] = {
    s.fold(
      { err => clasificarError(err) },
      { respuesta => Ok(Json.toJson(respuesta)) }).runToFuture
  }

  private def clasificarError[T](err: ErrorDominio) = {
    err match {
      case error: ElementoDuplicado => BadRequest(generarMensajeError(error.mensaje, Negocio))
      case error: ElementoNoExiste => NotFound(generarMensajeError(error.mensaje, Negocio))
      case error: ErroresDominio => BadRequest(generarMensajeError(error.errores.toList.map(_.mensaje).mkString(" - "), Negocio))
      case _ => InternalServerError(generarMensajeError("Error, contacte al administrador", Aplicacion))
    }
  }
}

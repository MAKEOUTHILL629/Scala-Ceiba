package co.com.infraestructura.error

import co.com.infraestructura.error.Error._
import javax.inject._
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router

import scala.concurrent._

@Singleton
class ErrorHandler @Inject() (
  env: Environment,
  config: Configuration,
  sourceMapper: OptionalSourceMapper,
  router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {

    val message = s"Internal server error, for (${request.method}) [${request.uri}]"
    Logger.logger.error(message, exception)
    val result = exception match {
      case e: ExcepcionTecnica => InternalServerError(generarMensajeError(s"Error Servidor: ${e.getMessage}", Tecnico))
      case e: Throwable => InternalServerError(generarMensajeError(s"Peticion invalida: ${e.getMessage}", Tecnico))
    }

    Future.successful(result)
  }

  override protected def onBadRequest(request: RequestHeader, message: String): Future[Result] = {
    Logger.logger.error(message)
    if (message.contains("Invalid Json:")) {
      Future.successful(
        BadRequest(generarMensajeError("La peticion no contiene un json valido", Negocio)))
    } else if (message.contains("Json validation error")) {
      Future.successful(
        BadRequest(generarMensajeError("Campo con invalido", Negocio)))
    } else {
      super.onBadRequest(request, message)
    }

  }

}
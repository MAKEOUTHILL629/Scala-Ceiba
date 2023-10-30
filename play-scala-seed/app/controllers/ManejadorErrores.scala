package controllers

import play.api.http.HttpErrorHandler
import play.api.mvc.Results.{InternalServerError, Status}
import play.api.mvc.{RequestHeader, Result}

import javax.inject.Singleton
import scala.concurrent.Future

@Singleton
class ManejadorErrores extends HttpErrorHandler{
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)(s"Error en el cliente $message")
      )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful(
      InternalServerError("Error en el servidor: " + exception.getMessage)
    )
  }
}

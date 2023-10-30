package controllers

import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request, ResponseHeader, Result}

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class PruebasController  @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def okkk(): Action[AnyContent] =Action { implicit request: Request[AnyContent] =>
    Ok("Estatus 200")
  }

  def clientError(): Action[AnyContent] = Action {
    Result(
      header = ResponseHeader(401, Map.empty),
      body = HttpEntity.Strict(ByteString("Error en el cliente"), Some("text/plain"))
    )
  }

  def errorServer() = Action{
    InternalServerError("Error en el servidor")
  }

  def pdf() = Action{
    // Obtener el pdf de la carpeta public/pdf-get.pdf
    val path = "public/pdf-get.pdf"
    val file = new java.io.File(path)
    val pathFile = file.toPath
    val source = java.nio.file.Files.readAllBytes(pathFile)
    val contentType = "application/pdf"
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString(source), Some(contentType))
    )
  }

  def params(param1: Int, param2: String) = Action{
    Ok(s"param1: $param1, param2: $param2")
  }

  def headersImpresion() = Action{ request =>
    val headers = request.headers
    val contentType = headers.get("Content-Type")
    val userAgent = headers.get("User-Agent")
    val candidato = headers.get("Candidato")
    Ok(s"Content-Type: $contentType, User-Agent: $userAgent, Candidato: $candidato")
  }

  def cookiesImpresor() = Action{ request =>
    val cookies = request.cookies
    val candidato = cookies.get("Candidato")
    Ok(s"Candidato: $candidato")
  }

  def future() = Action.async{
    Future.successful(Ok("Estatus 200  en future"))
  }
}

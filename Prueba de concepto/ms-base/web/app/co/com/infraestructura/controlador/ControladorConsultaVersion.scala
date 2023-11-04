package co.com.infraestructura.controlador

import co.com.servicio_base.BuildInfo
import com.typesafe.config.ConfigFactory
import javax.inject.Inject
import play.api.Configuration
import play.api.mvc.{ AbstractController, Action, AnyContent, ControllerComponents, Request }

class ControladorConsultaVersion @Inject() (cc: ControllerComponents, conf: Configuration) extends AbstractController(cc) {

  def version = Action { implicit request: Request[AnyContent] =>
    Ok(s"$BuildInfo")
  }

  def redirectDocs = Action { implicit request: Request[AnyContent] =>
    val protocol = if (request.secure) "https" else "http"
    val basePath = conf.get[String]("play.http.context")
    Redirect(s"$basePath/assets/lib/swagger-ui/index.html?/url=$protocol://${request.host}$basePath/swagger.json")
  }

}

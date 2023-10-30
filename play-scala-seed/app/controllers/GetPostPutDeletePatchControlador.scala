package controllers


import play.api.mvc.Results.Forbidden
import play.api.mvc.{AbstractController, ActionBuilderImpl, ActionFilter, BodyParsers, ControllerComponents, Cookie, Request, Result, Results}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AuthAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    if(request.headers.get("nombre").contains("tkn")){
      block(request)

    }else{
      Future.successful(Forbidden("No tienes permisos"))
    }
  }
}

class GetPostPutDeletePatchControlador @Inject()(authAction: AuthAction,cc: ControllerComponents) extends AbstractController(cc) {


  def get() = Action {
    Ok("Estatus 200").as(HTML)
  }

  def post() = Action {
    Ok("Estatus 200").withHeaders("Location" -> "http://localhost:9000/okkk").withCookies(Cookie("candidato", "Luis"))
  }

  def put() = authAction {
    Ok("Estatus 200")
  }

  def delete() = Action {
    Ok("Estatus 200")
  }

  def patch() = Action {
    Ok("Estatus 200")
  }
}



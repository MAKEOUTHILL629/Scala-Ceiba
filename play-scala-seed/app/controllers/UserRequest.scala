package controllers

import play.api.mvc.{ActionBuilder, ActionTransformer, AnyContent, BodyParsers, Request, WrappedRequest}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserRequest[A](val username: Option[String], request: Request[A]) extends WrappedRequest[A](request)

class UserAction @Inject()(val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent]
    with ActionTransformer[Request, UserRequest] {
  def transform[A](request: Request[A]) = Future.successful {
    if (request.headers.get("nombre").contains("tkn")) {
      new UserRequest(Some("tkn"), request)
    }else{
      new UserRequest(None, request)
    }
  }
}
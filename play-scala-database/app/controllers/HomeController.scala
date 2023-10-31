package controllers

import javax.inject._

import models.Coffee
import persistence.CoffeeRepository
import play.api._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class HomeController @Inject()(cc: ControllerComponents, coffeeRepository: CoffeeRepository)(implicit executionContext: ExecutionContext) extends AbstractController(cc) {

  populateDate()


  def index() = Action.async { implicit request: Request[AnyContent] =>

    val fCoffees: Future[Seq[Coffee]] = coffeeRepository.all()

    fCoffees.map(s => Ok( Json.toJson(s) ))
  }

  def lower(limit: Int) = Action.async { implicit request: Request[AnyContent] =>

    val fCoffees: Future[Seq[Coffee]] = coffeeRepository.lower(limit)

    fCoffees.map(s => Ok( Json.toJson(s) ))
  }

  def add() = Action.async(parse.json[Coffee]) { request =>
    insertCoffee(request.body)
  }

  private def insertCoffee(coffee: Coffee): Future[Result] = {
    coffeeRepository.insert(coffee)
      .map(_ => Ok(""))
      .recoverWith {
        case _: Exception => Future.successful( InternalServerError("No pudo guardarse el registro") )
      }
  }

  private def populateDate() {
    insertCoffee(new Coffee("Expresso", 1200, ""))
    insertCoffee(new Coffee("Tostado", 1500, ""))
    insertCoffee(new Coffee("Negro", 2000, ""))
  }

}

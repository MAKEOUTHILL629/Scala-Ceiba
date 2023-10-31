package models

import play.api.libs.json.Json

case class Coffee(name: String, price: Double, farm: String)

object Coffee {
  implicit val cofffeFormat = Json.format[Coffee]
}

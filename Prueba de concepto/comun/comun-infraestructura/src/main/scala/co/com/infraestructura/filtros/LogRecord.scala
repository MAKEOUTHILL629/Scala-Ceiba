package co.com.infraestructura.filtros

import play.api.libs.json.{ Json, OFormat }

case class LogRecord(path: String, statusCode: Int, responseTime: Long)

object LogRecord {

  implicit val recordFormat: OFormat[LogRecord] = Json.format[LogRecord]

}
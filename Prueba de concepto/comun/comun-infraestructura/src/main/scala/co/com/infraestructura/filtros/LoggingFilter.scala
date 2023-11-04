package co.com.infraestructura.filtros

import akka.stream.Materializer
import co.com.infraestructura.filtros.LogRecord._
import javax.inject.{ Inject, Singleton }
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class LoggingFilter @Inject() (implicit val mat: Materializer) extends Filter {
  implicit val ec = ExecutionContext.global
  def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {

    val startTime = System.currentTimeMillis

    nextFilter(requestHeader).map { result =>
      val responseTime: Long = System.currentTimeMillis - startTime
      if (!requestHeader.path.contains("version")
        && !requestHeader.path.contains("assets")
        && !requestHeader.path.contains("swagger")) {
        val record = LogRecord(requestHeader.path, result.header.status, responseTime)
        Logger.logger.info(Json.toJson(record).toString())

        result.withHeaders("Ms-Response-Time" -> responseTime.toString)
      } else {
        result
      }
    }

  }

}

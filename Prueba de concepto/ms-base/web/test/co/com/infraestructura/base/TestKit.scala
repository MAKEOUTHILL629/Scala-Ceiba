package co.com.infraestructura.base

import co.com.dominio.persistencia.dao.DaoUsuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario
import co.com.infraestructura.persistencia.dao.DaoUsuarioPostgres
import co.com.infraestructura.persistencia.repositorio.RepositorioUsuarioPostgres
import com.typesafe.config.ConfigFactory
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api._
import play.api.http.Port
import play.api.inject.DefaultApplicationLifecycle
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc._
import play.core.server.{ Server, ServerConfig, ServerProvider }
import slick.basic.DatabaseConfig
import slick.jdbc.H2Profile
import play.api.inject.bind

import scala.concurrent.duration._
import scala.concurrent.{ Await, ExecutionContext }

abstract class TestKit extends PlaySpec {
  implicit val ec = ExecutionContext.global
}

abstract class DatabaseTestKit extends AppTestKit {

  import H2Profile.api._
  val dbConfig: DatabaseConfig[H2Profile]

  val schema: slick.jdbc.H2Profile.DDL

  def testDbConfig(dbName: String): DatabaseConfig[H2Profile] =
    DatabaseConfig.forConfig[H2Profile](s"slick.dbs.$dbName", conf.underlying)

  def shutdownDb = {
    Logger.logger.debug(s"DATABASE SHUTDOWN")
    val execute = for {
      _ <- dbConfig.db.run(schema.drop)
      _ <- dbConfig.db.shutdown
    } yield ()

    Await.result(execute, 5 seconds)
  }

  def initializeDb = {
    Logger.logger.debug(s"INITIALIZING DATABASE")
    Await.result[Unit](dbConfig.db.run(schema.create), 5 seconds)
  }

}

import play.api.routing.Router

abstract class AppTestKit extends TestKit with GuiceOneAppPerSuite {

  final protected val appBuilder = new GuiceApplicationBuilder()

  final val conf: Configuration = Configuration(ConfigFactory.load("application-test.conf"))

  override def fakeApplication(): Application = appBuilder.configure(conf)
    .overrides(
      bind(classOf[RepositorioUsuario]).to(classOf[RepositorioUsuarioPostgres]),
      bind(classOf[DaoUsuario]).to(classOf[DaoUsuarioPostgres])).build()

}

object TestKit {

  def withRouter[T](config: ServerConfig = ServerConfig(port = Some(0), mode = Mode.Test))(routes: PartialFunction[RequestHeader, Handler])(block: Port => T)(implicit provider: ServerProvider): T = {

    val conf = Configuration(ConfigFactory.load("application-test.conf"))

    val context = ApplicationLoader.Context(
      environment = Environment.simple(path = config.rootDir, mode = config.mode),
      initialConfiguration = conf,
      lifecycle = new DefaultApplicationLifecycle,
      devContext = None)
    val application = new BuiltInComponentsFromContext(context) with NoHttpFiltersComponents {
      def router = Router.from(routes)
    }.application

    Server.withApplication(application, config)(block)
  }
}


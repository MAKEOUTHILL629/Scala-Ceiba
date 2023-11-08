package configuracion







import akka.stream.Materializer
import candidato.dominio.repositorio.CandidatoRepositorio
import candidato.infraestructura.persistencia.CandidatoRepositorioMysql
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.{Application, inject}
import play.api.db.DBApi
import play.api.db.evolutions.Evolutions
import play.api.inject.guice.GuiceApplicationBuilder
import votacion.dominio.repositorio.VotoRepositorio
import votacion.infraestructura.persistencia.VotoRepositorioMysql






class TestKit extends  PlaySpec with GuiceOneAppPerSuite with BeforeAndAfterAll{


  override def fakeApplication(): Application = {
    val guiceApp = new GuiceApplicationBuilder().configure("config.file" -> "/resources/test-application.conf")
      .overrides( inject.bind[CandidatoRepositorio].to[CandidatoRepositorioMysql], inject.bind[VotoRepositorio].to[VotoRepositorioMysql]).build()

    guiceApp
  }

  override def beforeAll(): Unit = {
    super.beforeAll()
    val dbApi = app.injector.instanceOf[DBApi]
    val database = dbApi.database("default")
    Evolutions.applyEvolutions(database)
  }

  // Limpiar evoluciones después de las pruebas
  override def afterAll(): Unit = {
    val dbApi = app.injector.instanceOf[DBApi]
    val database = dbApi.database("default")
    Evolutions.cleanupEvolutions(database)
    super.afterAll()
  }
}

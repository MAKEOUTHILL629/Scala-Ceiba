package votacion.infraestructura.controlador


import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play.materializer
import play.api.http.Status.CREATED
import play.api.libs.json.Json
import play.api.test.Helpers.{POST, defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Injecting}
import play.api.test.Helpers._

import scala.concurrent.ExecutionContext.Implicits.global
class VotoControladorSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "VotoControlador POST" should {
    "retornar un 201, cuando se crea un voto nuevo" in {
      val controller = inject[VotoControlador]

      val home = controller.votarCandidato()
        .apply(FakeRequest(POST, "/votar")
          .withHeaders("Content-Type" -> "application/json")
          .withJsonBody(
            Json.parse(
              s"""{
                 |"cedula": "${cedulaAleatoria()}",
                 |"candidato": 1
                 |}""".stripMargin)
            ))


      val result = contentAsString(home)
      status(home) mustBe CREATED
    }
  }

  def cedulaAleatoria(): String = {
    val r = scala.util.Random
    val numero = r.nextLong(9999999999l)
    numero.toString
  }
}

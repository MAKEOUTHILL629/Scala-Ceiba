package votacion.infraestructura.controlador


import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play.materializer
import play.api.http.Status.CREATED
import play.api.test.Helpers.{POST, defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Injecting}
import play.api.test.Helpers._
class VotoControladorSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "VotoControlador POST" should {
    "retornar un 201, cuando se crea un voto nuevo" in {
      val controller = inject[VotoControlador]
      //contentype de typo json
      val home = controller.votarCandidato().apply(FakeRequest(POST, "/votar", FakeRequest().withHeaders(newHeaders = ("Content-Type", "application/json")).headers, FakeRequest().withBody(
        s"""
           |{
           | "cedula": "${cedulaAleatoria()}",
           | "candidato": 3
           |}
           |""".stripMargin
      )))




      status(home) mustBe CREATED
    }
  }

  def cedulaAleatoria(): String = {
    val r = scala.util.Random
    val numero = r.nextInt(999999999)
    numero.toString
  }
}

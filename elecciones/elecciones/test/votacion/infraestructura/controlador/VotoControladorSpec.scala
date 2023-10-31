package votacion.infraestructura.controlador


import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play.materializer
import play.api.http.Status.CREATED
import play.api.libs.json.{JsString, Json}
import play.api.test.Helpers.{POST, defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Injecting}
import play.api.test.Helpers._

import scala.annotation.tailrec

class VotoControladorSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "VotoControlador POST" should {
    "retornar un 201, cuando se crea un voto nuevo" in {
      val controller = inject[VotoControlador]
      val body =  Json.obj(
        "cedula" -> cedulaAleatoria(),
        "candidato" -> 1
      )

      val resultado = call(controller.votarCandidato(), FakeRequest(POST, "/votar").withHeaders((CONTENT_TYPE, "application/json")).withJsonBody(body))




      status(resultado) mustBe CREATED
      contentAsString(resultado) mustBe ("Voto guardado")
    }

    "Retornar estatus 400, cuando la cedula no tiene exactamente 10 caracteres numericos" in {

      val controller = inject[VotoControlador]
      val body = Json.obj(
        "cedula" -> "10000000f",
        "candidato" -> 1
      )

      val resultado = call(controller.votarCandidato(), FakeRequest(POST, "/votar").withHeaders((CONTENT_TYPE, "application/json")).withJsonBody(body))

      val mensajeError =( contentAsJson(resultado) \\ "msg" ).head(0).asInstanceOf[JsString].value
      status(resultado) mustBe BAD_REQUEST
      mensajeError mustBe ("La cedula debe tener exactamente 10 caracteres numéricos")

    }
  }

  @tailrec
  private  def cedulaAleatoria(): String = {
    val r = scala.util.Random
    val numero = r.nextLong(9999999999L)

    if (numero.toString.length != 10) cedulaAleatoria() else numero.toString
  }
}

package controllers

import candidato.dominio.modelos.Candidato
import candidato.infraestructura.controlador.CandidatoControlador
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play.materializer
import play.api.test._
import play.api.test.Helpers._


class CandidatoControladorSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {


  "CandidatoControlador GET" should {
    "retornar todos los candidatos" in {
      val controller = inject[CandidatoControlador]
      val home = controller.obtenerCantidadosElecciones().apply(FakeRequest(GET, "/candidatos"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include ("[")
      contentAsJson(home).as[Seq[Candidato]].length must be > 0
    }
  }

}

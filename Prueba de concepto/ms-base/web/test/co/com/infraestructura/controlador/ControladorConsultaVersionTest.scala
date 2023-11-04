package co.com.infraestructura.controlador

import co.com.aplicacion.comando.ComandoUsuario
import co.com.infraestructura.base.AppTestKit
import co.com.infraestructura.controlador.formateadores.FormateadoresAplicacion
import org.joda.time.DateTime
import org.scalatest.{ BeforeAndAfterAll, MustMatchers }
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.test.Helpers

class ControladorConsultaVersionTest extends AppTestKit with MustMatchers with FormateadoresAplicacion with BeforeAndAfterAll {

  "ControladorConsultaVersion" can {
    "consulta la version" when {
      "se invoca el servicio" in {

        val request = FakeRequest(Helpers.GET, "/ms-base/version")
        val response = route(app, request).get
        status(response) mustBe (OK)

      }
    }
  }

}

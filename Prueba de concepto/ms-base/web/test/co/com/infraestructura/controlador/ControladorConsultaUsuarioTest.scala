package co.com.infraestructura.controlador

import java.time.LocalDateTime

import co.com.aplicacion.comando.ComandoUsuario
import co.com.dominio.dtos.DtoUsuario
import co.com.infraestructura.base.AppTestKit
import co.com.infraestructura.controlador.formateadores.FormateadoresAplicacion
import org.joda.time.DateTime
import org.scalatest.{ BeforeAndAfterAll, MustMatchers }
import play.api.libs.json.{ JsError, JsSuccess }
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.test.Helpers

class ControladorConsultaUsuarioTest extends AppTestKit with MustMatchers with FormateadoresAplicacion with BeforeAndAfterAll {

  "ControladorConsultaUsuario" can {
    "consulta los usuario" when {
      "se invoca el servicio" in {

        val request = FakeRequest(Helpers.GET, "/ms-base/usuarios")
        val response = route(app, request).get

        status(response) mustBe (OK)
        contentAsJson(response).validate[List[DtoUsuario]] match {
          case JsError(errors) => fail(s"respuesta inesperada  ${errors.map(s => s" path ${s._1} ${s._2}")}")
          case JsSuccess(Nil, path) => fail("lista vacia")
          case JsSuccess(value: List[DtoUsuario], path) => value.head.nombre.getOrElse("") mustBe "pepe"
        }

      }
    }

    "consulta usuario por id" when {
      "se invoca el servicio pero usuario no existe" in {

        val request = FakeRequest(Helpers.GET, "/ms-base/usuarios/1")
        val response = route(app, request).get

        status(response) mustBe (NOT_FOUND)

      }
      "se invoca el servicio y el usuario existe" in {

        val request = FakeRequest(Helpers.GET, "/ms-base/usuarios/2")
        val response = route(app, request).get

        status(response) mustBe (OK)
        contentAsJson(response).validate[DtoUsuario] match {
          case JsError(errors) => fail(s"respuesta inesperada  ${errors.map(s => s" path ${s._1} ${s._2}")}")
          case JsSuccess(value, path) => value.nombre.getOrElse("") mustBe "pepe"
        }

      }
    }
  }

  override protected def beforeAll(): Unit = {
    val dtoUsuario = ComandoUsuario(2L, Some("pepe"), Some("pepe"), Some(LocalDateTime.now()))
    val requestGuardar = FakeRequest(POST, "/ms-base/usuarios").withJsonBody(comandoUsuarioFormat.writes(dtoUsuario))
    val responseGuardar = route(app, requestGuardar).get

    val jsonContent = contentAsJson(responseGuardar)
    (jsonContent \ "respuesta").get.toString mustBe "2"
  }
}

package co.com.infraestructura.controlador

import java.time.LocalDateTime

import co.com.aplicacion.comando.ComandoUsuario
import co.com.infraestructura.base.AppTestKit
import co.com.infraestructura.controlador.formateadores.FormateadoresAplicacion
import org.scalatest.MustMatchers
import play.api.test.FakeRequest
import play.api.test.Helpers.{ POST, _ }

class ControladorComandoUsuarioTest extends AppTestKit with MustMatchers with FormateadoresAplicacion {

  // evaluar el contenido del mensaje
  "ControladorComandoUsuario" can {
    "guardar usuario" when {
      "se invoca el servicio" in {

        val dtoUsuario = ComandoUsuario(1L, Some("pepe"), Some("pepe"), Some(LocalDateTime.now()))
        val request = FakeRequest(POST, "/ms-base/usuarios").withJsonBody(comandoUsuarioFormat.writes(dtoUsuario))

        val response = route(app, request).get

        status(response) mustBe (OK)
        val jsonContent = contentAsJson(response)
        (jsonContent \ "respuesta").get.toString mustBe "1"

      }

    }

    "actualizar usuario" when {
      "se invoca el servicio" in {

        val dtoUsuario = ComandoUsuario(1L, Some("pepe"), Some("pepe"), Some(LocalDateTime.now()))
        val request = FakeRequest(PUT, "/ms-base/usuarios").withJsonBody(comandoUsuarioFormat.writes(dtoUsuario))

        val response = route(app, request).get

        status(response) mustBe (OK)
        val jsonContent = contentAsJson(response)
        (jsonContent \ "respuesta").get.toString mustBe "1"

      }

    }

    "eliminar usuario" when {
      "se invoca el servicio" in {

        val dtoUsuario = ComandoUsuario(1L, Some(""), Some(""), Some(LocalDateTime.now()))
        val request = FakeRequest(DELETE, "/ms-base/usuarios/1")

        val response = route(app, request).get

        status(response) mustBe (OK)
        val jsonContent = contentAsJson(response)
        (jsonContent \ "respuesta").get.toString mustBe "1"

      }

    }
  }
}


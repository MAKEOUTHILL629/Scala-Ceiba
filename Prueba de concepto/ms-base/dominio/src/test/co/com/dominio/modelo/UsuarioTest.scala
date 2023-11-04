package co.com.dominio.modelo

import java.time.LocalDateTime

import co.com.dominio.errores.ErroresDominio
import org.scalatest.{ AsyncWordSpec, MustMatchers }

class UsuarioTest extends AsyncWordSpec with MustMatchers {

  "Usuario" can {
    "generar error" when {
      "no se envia nombre" in {
        val respuesta = Usuario.validarCrearUsuario(1L, None, Some(""), None)

        respuesta match {
          case Right(s) => fail()
          case Left(errorDominio) => errorDominio.asInstanceOf[ErroresDominio].errores.head.mensaje mustBe "nombre obligatorio"
        }

      }
      "no se envia clave" in {
        val respuesta = Usuario.validarCrearUsuario(1L, Some(""), None, None)

        respuesta match {
          case Right(s) => fail()
          case Left(errorDominio) => errorDominio.asInstanceOf[ErroresDominio].errores.head.mensaje mustBe "clave obligatorio"
        }
      }
    }
    "crearse" when {
      "crearse cando se envian parametros correctos" in {
        val respuesta = Usuario.validarCrearUsuario(1L, Some(""), Some(""), Some(LocalDateTime.now()))

        respuesta match {
          case Right(s) => s.id mustBe 1L
          case Left(_) => fail("respuesta inesperada")
        }
      }
    }

  }

}

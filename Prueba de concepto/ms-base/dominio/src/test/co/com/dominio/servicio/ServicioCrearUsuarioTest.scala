package co.com.dominio.servicio

import java.time.LocalDateTime

import cats.data.EitherT
import cats.implicits._
import co.com.dominio.errores.{ DetalleErrorDominio, ErrorDominio }
import co.com.dominio.modelo.Usuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario
import monix.eval.Task
import monix.execution.Scheduler
import org.scalatest.{ AsyncWordSpec, MustMatchers }
import org.specs2.mock.Mockito

import scala.language.postfixOps

class ServicioCrearUsuarioTest extends AsyncWordSpec with MustMatchers with Mockito {

  val mockRepoUsuario = mock[RepositorioUsuario]
  implicit val s: Scheduler = Scheduler.global

  val servicio = new ServicioCrearUsuario

  "ServicioCrearUsuario" can {
    "genera error" when {
      "se va a crear un usuario y existe previamente" in {
        val usuario = Usuario(1L, "", "", LocalDateTime.now())
        mockRepoUsuario.insertar(any[Usuario]()) returns EitherT(Task(1L.asRight[ErrorDominio]))
        mockRepoUsuario.existe(anyLong) returns Task(true)

        val respuesta = servicio.crear(usuario).run(mockRepoUsuario).value

        respuesta.runToFuture.map(resultado => {
          resultado.isLeft mustBe true
          resultado match {
            case Right(_) => fail("resultado inesperado")
            case Left(error) => error.asInstanceOf[DetalleErrorDominio].mensaje mustBe "Elemento duplicado"
          }
        })
      }

    }

    "crear usuario" when {
      "usuario no existe previamente" in {
        val usuario = Usuario(1L, "", "", LocalDateTime.now())
        mockRepoUsuario.insertar(any[Usuario]()) returns EitherT(Task(1L.asRight[ErrorDominio]))
        mockRepoUsuario.existe(anyLong) returns Task(false)

        val respuesta = servicio.crear(usuario).run(mockRepoUsuario).value
        respuesta.runToFuture.map(resultado => {
          resultado match {
            case Right(valor) => valor mustBe 1L
            case Left(_) => fail("resultado inesperado")
          }
        });
      }
    }
  }

}

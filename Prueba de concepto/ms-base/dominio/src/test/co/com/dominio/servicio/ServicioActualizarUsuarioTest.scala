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

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class ServicioActualizarUsuarioTest extends AsyncWordSpec with MustMatchers with Mockito {

  val mockRepoUsuario = mock[RepositorioUsuario]
  implicit val s: Scheduler = Scheduler.global

  val servicio = new ServicioActualizarUsuario

  "ServicioActualizarUsuario" can {
    "genera error" when {
      "se va a actualizar un usuario y no existe previamente" in {
        val usuario = Usuario(1L, "", "", LocalDateTime.now())
        mockRepoUsuario.actualizar(any[Usuario]()) returns EitherT(Task(1L.asRight[ErrorDominio]))
        mockRepoUsuario.existe(anyLong) returns Task(false)

        val respuesta = servicio.actualizar(usuario).run(mockRepoUsuario).value
        val resultado = Await.result(respuesta.runToFuture, 2 seconds)

        resultado.isLeft mustBe true
        resultado match {
          case Right(_) => fail("resultado inesperado")
          case Left(error) => error.asInstanceOf[DetalleErrorDominio].mensaje mustBe "Elemento no existe"
        }
      }

    }

    "actualiza usuario" when {
      "usuario existe previamente" in {
        val usuario = Usuario(1L, "", "", LocalDateTime.now())
        mockRepoUsuario.actualizar(any[Usuario]()) returns EitherT(Task(1L.asRight[ErrorDominio]))
        mockRepoUsuario.existe(anyLong) returns Task(true)

        val respuesta = servicio.actualizar(usuario).run(mockRepoUsuario).value
        val resultado = Await.result(respuesta.runToFuture, 2 seconds)

        resultado match {
          case Right(valor) => valor mustBe 1L
          case Left(_) => fail("resultado inesperado")
        }
      }
    }
  }

}

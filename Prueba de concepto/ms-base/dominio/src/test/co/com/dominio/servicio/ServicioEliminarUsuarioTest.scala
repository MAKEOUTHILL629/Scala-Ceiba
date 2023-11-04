package co.com.dominio.servicio

import cats.data.EitherT
import cats.implicits._
import co.com.dominio.errores.{ DetalleErrorDominio, ErrorDominio }
import co.com.dominio.persistencia.repositorio.RepositorioUsuario
import monix.eval.Task
import monix.execution.Scheduler
import org.scalatest.{ AsyncWordSpec, MustMatchers }
import org.specs2.mock.Mockito

import scala.concurrent.Await
import scala.concurrent.duration._

class ServicioEliminarUsuarioTest extends AsyncWordSpec with MustMatchers with Mockito {

  val mockRepoUsuario = mock[RepositorioUsuario]

  val servicio = new ServicioEliminarUsuario

  implicit val s: Scheduler = Scheduler.global

  "ServicioEliminarUsuario" can {
    "genera error" when {
      "el usuario no existe previamente" in {
        val idUsuario = 1L;
        mockRepoUsuario.existe(anyLong) returns Task(false)

        val respuesta = servicio.eliminar(idUsuario).run(mockRepoUsuario).value
        val resultado = Await.result(respuesta.runToFuture, 2 seconds)

        resultado.isLeft mustBe true
        resultado match {
          case Right(_) => fail("resultado inesperado")
          case Left(error) => error.asInstanceOf[DetalleErrorDominio].mensaje mustBe "Elemento no existe"
        }

      }
    }

    "elimina usuario" when {
      "el usuario existe previamente" in {
        val idUsuario = 1L;
        mockRepoUsuario.existe(anyLong) returns Task(true)
        mockRepoUsuario.eliminar(anyLong) returns EitherT(Task(1L.asRight[ErrorDominio]))

        val respuesta = servicio.eliminar(idUsuario).run(mockRepoUsuario).value
        val resultado = Await.result(respuesta.runToFuture, 2 seconds)

        resultado.isRight mustBe true
        resultado.toOption.isDefined mustBe true
        resultado.toOption.get mustBe idUsuario

      }

    }

  }

}

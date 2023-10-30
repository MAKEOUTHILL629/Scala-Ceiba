package votacion.aplicacion

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.concurrent.Futures.whenReady
import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import votacion.dominio.modelos.Voto
import votacion.dominio.repositorio.VotoRepositorio

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class VotarCandidatoSpec extends PlaySpec with MockitoSugar {

  "VotarCandidato#votar" should {
    "No retorna nada, cuando se guarda el voto" in {

      val votoRepositorio = mock[VotoRepositorio]
      when(votoRepositorio.insertar(any[Voto])) thenReturn (Future(()))
      when(votoRepositorio.findByCedula(any[String])) thenReturn (Future(None))
      val votarCandidato = new VotarCandidato(votoRepositorio)
      val voto = Voto(None, "1000784940", 3)


      val resultado = votarCandidato.votar(voto).flatten

      resultado.futureValue mustBe (())
    }

    "Lanzar una excepcion de tipo IllegalArgumentException, cuando la cedula ya voto" in {
      val votoRepositorio = mock[VotoRepositorio]
      when(votoRepositorio.findByCedula(any[String])) thenReturn (Future(Some(Voto(None, "1000784940", 3))))
      val votarCandidato = new VotarCandidato(votoRepositorio)
      val voto = Voto(None, "1000784940", 3)


      val resultado = votarCandidato.votar(voto).failed

      resultado.futureValue.getMessage mustBe ("La cedula 1000784940 ya voto")
    }
  }


}

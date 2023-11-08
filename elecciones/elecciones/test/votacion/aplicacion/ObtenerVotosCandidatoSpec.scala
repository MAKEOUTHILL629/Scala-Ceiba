package votacion.aplicacion


import org.scalatestplus.play.PlaySpec
import votacion.dominio.repositorio.VotoRepositorio

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ObtenerVotosCandidatoSpec extends PlaySpec  {

  "ObtenerVotosCandidato#totalVotosCandidatos" should {
    "Retornar una lista de votos por candidato" in {
//      val votos = Seq(VotosCandidato(1, "Candidato 1", 10),VotosCandidato(2, "Candidato 2", 5))
//      val votoRepositorio = mock[VotoRepositorio]
//      when(votoRepositorio.totalVotosCandidatos()) thenReturn (Future(votos))
//      val obtenerVotosCandidato = new ObtenerVotosCandidato(votoRepositorio)
//
//      val resultado = obtenerVotosCandidato.totalVotosCandidatos()
//
//      resultado.futureValue mustBe (votos)
    }
  }
}

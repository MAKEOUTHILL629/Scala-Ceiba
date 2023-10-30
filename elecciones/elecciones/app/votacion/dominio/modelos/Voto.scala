package votacion.dominio.modelos

import play.api.libs.json.Json

case class Voto(id: Option[Int], cedula: String, candidato: Int)

object Voto {


  implicit val candidatoFormat = Json.format[Voto]


}


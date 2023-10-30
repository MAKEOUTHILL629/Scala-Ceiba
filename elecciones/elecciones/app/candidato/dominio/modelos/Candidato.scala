package candidato.dominio.modelos

import play.api.libs.json.Json

case class Candidato(id: Int, nombre: String, partido: String)


object Candidato {
  implicit val candidatoFormat = Json.format[Candidato]
}

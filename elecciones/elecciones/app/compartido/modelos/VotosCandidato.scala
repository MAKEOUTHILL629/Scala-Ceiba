package compartido.modelos

import play.api.libs.json.Json


case class VotosCandidato(id:Int, nombre:String, votos:Int)

object VotosCandidato{
  implicit val votosCandidatoFormat = Json.format[VotosCandidato]
}


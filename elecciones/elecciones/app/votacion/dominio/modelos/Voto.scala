package votacion.dominio.modelos

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{Format, JsPath, Json, JsonValidationError, Reads, Writes}

case class Voto(id: Option[Int], cedula: String, candidato: Int)

object Voto {
   val votoRead: Reads[Voto] = (
    (JsPath \ "id").readNullable[Int] and
      (JsPath \ "cedula").read[String].filter(JsonValidationError("La cedula debe tener exactamente 10 caracteres numéricos")) { value => value.length == 10 && value.forall(_.isDigit) } and
      (JsPath \ "candidato").read[Int]
    )(Voto.apply _)

   val votoWrite: Writes[Voto] = (
    (JsPath \ "id").writeNullable[Int] and
      (JsPath \ "cedula").write[String] and
      (JsPath \ "candidato").write[Int]
    )( unlift(Voto.unapply))

  implicit val votoFormat: Format[Voto] = Format(votoRead, votoWrite)

//  implicit val votoFormat = Json.format[Voto]


}


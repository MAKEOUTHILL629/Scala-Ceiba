package candidato.infraestructura.persistencia

import candidato.dominio.modelos.Candidato
import candidato.dominio.repositorio.CandidatoRepositorio
import slick.jdbc.MySQLProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class CandidatoRepositorioMysql @Inject()(implicit ec: ExecutionContext) extends CandidatoRepositorio{
  private val db = Database.forConfig("mysql")

  private class CandidatosTable(tag: Tag) extends Table[Candidato](tag, "candidatos") {
    def id = column[Int]("id", O.PrimaryKey)

    def nombre = column[String]("nombre")

    def partido = column[String]("partido")

    def * = (id, nombre, partido) <> ((Candidato.apply _).tupled, Candidato.unapply)
  }

  private val candidatos = TableQuery[CandidatosTable]
  override def all(): Future[Seq[Candidato]] = db.run(candidatos.result)
}

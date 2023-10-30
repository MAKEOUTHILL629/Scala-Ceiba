package votacion.infraestructura.persistencia

import com.google.inject.Singleton
import compartido.modelos.VotosCandidato
import slick.jdbc.GetResult
import votacion.dominio.modelos.Voto
import votacion.dominio.repositorio.VotoRepositorio
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
@Singleton
class VotoRepositorioMysql @Inject()(implicit ec: ExecutionContext) extends VotoRepositorio{
  private val db = Database.forConfig("mysql")
  implicit val votosCandidatoGetResult: GetResult[VotosCandidato] = GetResult(r => VotosCandidato(r.nextInt(), r.nextString(), r.nextInt()))
  private class VotosTable(tag: Tag) extends Table[Voto](tag, "votos") {
    def id: Rep[Option[Int]] = column[Int]("id", O.PrimaryKey)

    def cedula = column[String]("cedula")

    def candidato = column[Int]("id_candidato")

    def * = (id, cedula, candidato) <> ((Voto.apply _).tupled, Voto.unapply)
  }

  private val votos = TableQuery[VotosTable]

  override def insertar(voto: Voto): Future[Unit] = db.run(votos += voto).map { _ => () }

  override def findByCedula(cedula: String): Future[Option[Voto]] = db.run(votos.filter(_.cedula === cedula).result.headOption)

  override def totalVotosCandidatos(): Future[Seq[VotosCandidato]] = {
    val query = sql"""
      SELECT c.id, c.nombre, COUNT(v.id_candidato) as totalVotos
      FROM votos v
      INNER JOIN candidatos c ON v.id_candidato = c.id
      GROUP BY v.id_candidato
      ORDER BY totalVotos DESC
      """.as[VotosCandidato]
    db.run(query)
  }
}





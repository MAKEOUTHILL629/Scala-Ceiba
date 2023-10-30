package votacion.infraestructura.persistencia

import com.google.inject.Singleton
import votacion.dominio.modelos.Voto
import votacion.dominio.repositorio.VotoRepositorio
import slick.jdbc.MySQLProfile.api._
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
@Singleton
class VotoRepositorioMysql @Inject()(implicit ec: ExecutionContext) extends VotoRepositorio{
  private val db = Database.forConfig("mysql")

  private class VotosTable(tag: Tag) extends Table[Voto](tag, "votos") {
    def id: Rep[Option[Int]] = column[Int]("id", O.PrimaryKey)

    def cedula = column[String]("cedula")

    def candidato = column[Int]("id_candidato")

    def * = (id, cedula, candidato) <> ((Voto.apply _).tupled, Voto.unapply)
  }

  private val votos = TableQuery[VotosTable]

  override def insertar(voto: Voto): Future[Unit] = db.run(votos += voto).map { _ => () }

  override def findByCedula(cedula: String): Future[Option[Voto]] = db.run(votos.filter(_.cedula === cedula).result.headOption)
}

package compartido.modulos

import candidato.dominio.repositorio.CandidatoRepositorio
import candidato.infraestructura.persistencia.CandidatoRepositorioMysql
import com.google.inject.AbstractModule
import votacion.dominio.repositorio.VotoRepositorio
import votacion.infraestructura.persistencia.VotoRepositorioMysql

class AppModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[CandidatoRepositorio]).to(classOf[CandidatoRepositorioMysql])

    bind(classOf[VotoRepositorio]).to(classOf[VotoRepositorioMysql])
  }
}

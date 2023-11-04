package co.com.infraestructura.modulo

import co.com.dominio.persistencia.dao.DaoUsuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario
import co.com.infraestructura.persistencia.dao.DaoUsuarioPostgres
import co.com.infraestructura.persistencia.repositorio.RepositorioUsuarioPostgres
import com.google.inject.AbstractModule

class ModuloDependencias extends AbstractModule {

  override def configure() = {
    bind(classOf[RepositorioUsuario]).to(classOf[RepositorioUsuarioPostgres]).asEagerSingleton()
    bind(classOf[DaoUsuario]).to(classOf[DaoUsuarioPostgres]).asEagerSingleton()
  }

}

package co.com.aplicacion.manejador

import co.com.dominio.dtos.DtoUsuario
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.modelo.Usuario.IdUsuario
import co.com.dominio.persistencia.dao.DaoUsuario
import javax.inject.Inject

class ManejadorConsultaUsuario @Inject() (daoUsuario: DaoUsuario) extends ManejadorConsultaParametro[IdUsuario, DtoUsuario] {

  override def ejecutar(idUsuario: IdUsuario): Respuesta[DtoUsuario] = {
    daoUsuario.buscarPorId(idUsuario)
  }
}

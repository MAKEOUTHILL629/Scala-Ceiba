package co.com.aplicacion.manejador

import cats.data.EitherT
import cats.implicits._
import co.com.dominio.dtos.DtoUsuario
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Resultado.Respuesta
import co.com.dominio.persistencia.dao.DaoUsuario
import javax.inject.Inject

class ManejadorListaUsuario @Inject() (daoUsuario: DaoUsuario) extends ManejadorConsulta[List[DtoUsuario]] {

  override def ejecutar(): Respuesta[List[DtoUsuario]] = {
    EitherT {
      daoUsuario.listar.map(s => s.asRight[ErrorDominio])
    }
  }
}

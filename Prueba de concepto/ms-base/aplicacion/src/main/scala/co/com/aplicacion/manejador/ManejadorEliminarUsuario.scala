package co.com.aplicacion.manejador

import co.com.aplicacion.ComandoRespuesta
import co.com.dominio.modelo.Usuario.IdUsuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario
import co.com.dominio.servicio.ServicioEliminarUsuario
import javax.inject.Inject

class ManejadorEliminarUsuario @Inject() (
  repositorioUsuario: RepositorioUsuario,
  servicioEliminarUsuario: ServicioEliminarUsuario) extends ManejadorComandoRespuestas[IdUsuario, IdUsuario] {

  override def ejecutar(idUsuario: IdUsuario): RespuestaComando[Long] = {
    servicioEliminarUsuario.eliminar(idUsuario)
      .run(repositorioUsuario)
      .map(respuesta => ComandoRespuesta(respuesta))
  }

}

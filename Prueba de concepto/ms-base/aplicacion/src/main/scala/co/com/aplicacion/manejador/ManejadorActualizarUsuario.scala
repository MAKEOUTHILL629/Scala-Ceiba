package co.com.aplicacion.manejador

import cats.data.EitherT
import co.com.aplicacion.ComandoRespuesta
import co.com.aplicacion.comando.ComandoUsuario
import co.com.aplicacion.fabrica.FabricaUsuario
import co.com.dominio.modelo.Usuario.IdUsuario
import co.com.dominio.persistencia.repositorio.RepositorioUsuario
import co.com.dominio.servicio.ServicioActualizarUsuario
import javax.inject.Inject

class ManejadorActualizarUsuario @Inject() (
  repositorioUsuario: RepositorioUsuario,
  servicioActualizarUsuario: ServicioActualizarUsuario) extends ManejadorComandoRespuestas[ComandoUsuario, IdUsuario] {

  override def ejecutar(comandoUsuario: ComandoUsuario): RespuestaComando[IdUsuario] = {
    for {
      usuario <- EitherT(FabricaUsuario.crear(comandoUsuario))
      respuesta <- servicioActualizarUsuario.actualizar(usuario).run(repositorioUsuario)
    } yield ComandoRespuesta(respuesta)
  }

}

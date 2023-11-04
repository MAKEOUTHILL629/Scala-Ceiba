package co.com.aplicacion.manejador

import co.com.dominio.modelo.Resultado.Respuesta

trait ManejadorConsulta[R] {
  def ejecutar(): Respuesta[R]
}

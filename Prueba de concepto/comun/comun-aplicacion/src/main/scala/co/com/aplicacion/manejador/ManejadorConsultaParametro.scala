package co.com.aplicacion.manejador

import co.com.dominio.modelo.Resultado.Respuesta

trait ManejadorConsultaParametro[P, R] {
  def ejecutar(comando: P): Respuesta[R]
}


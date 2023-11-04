package co.com.aplicacion.manejador

trait ManejadorComandoRespuestas[P, R] {
  def ejecutar(comando: P): RespuestaComando[R]
}

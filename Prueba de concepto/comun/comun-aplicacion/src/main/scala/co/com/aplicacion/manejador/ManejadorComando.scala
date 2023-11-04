package co.com.aplicacion.manejador

trait ManejadorComando[P] {
  def ejecutar(comando: P): Unit
}

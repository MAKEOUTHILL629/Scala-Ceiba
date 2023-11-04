package co.com.dominio.modelo

case class ExcepcionValorInvalido(value: String = "Valor invalido", throwable: Throwable) extends Exception(value, throwable)

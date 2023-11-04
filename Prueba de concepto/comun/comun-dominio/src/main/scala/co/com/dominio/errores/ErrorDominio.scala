package co.com.dominio.errores

import cats.data.NonEmptyList

sealed trait ErrorDominio

final case class ErroresDominio(errores: NonEmptyList[DetalleErrorDominio]) extends ErrorDominio

sealed trait DetalleErrorDominio extends ErrorDominio {
  val mensaje: String
}

object ErrorDominio {

  final case class ElementoDuplicado(mensaje: String) extends DetalleErrorDominio
  final case class ElementoNoExiste(mensaje: String) extends DetalleErrorDominio
  final case class LongitudInvalida(mensaje: String) extends DetalleErrorDominio
  final case class ValorInvalido(mensaje: String) extends DetalleErrorDominio
  final case class ValorObligatorio(mensaje: String) extends DetalleErrorDominio

  def noExiste(mensaje: String = "Elemento no existe"): DetalleErrorDominio = ElementoNoExiste(mensaje)
  def existe(mensaje: String = "Elemento duplicado"): DetalleErrorDominio = ElementoDuplicado(mensaje)
  def longitudInvalida(mensaje: String = "Longitud invalida"): DetalleErrorDominio = LongitudInvalida(mensaje)
  def valorInvalido(mensaje: String = "Valor invalido"): DetalleErrorDominio = ValorInvalido(mensaje)
  def valorObligatorio(mensaje: String = "Valor obligatorio"): DetalleErrorDominio = ValorObligatorio(mensaje)

}
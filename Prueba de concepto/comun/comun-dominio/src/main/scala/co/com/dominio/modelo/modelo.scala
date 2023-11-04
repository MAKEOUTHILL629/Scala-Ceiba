package co.com.dominio

import java.time.LocalDateTime

import cats.data._
import cats.implicits._
import co.com.dominio.errores.{ DetalleErrorDominio, ErrorDominio, ErroresDominio }

import scala.util.{ Failure, Success, Try }

package object modelo {
  type ValidationResult[A] = ValidatedNel[DetalleErrorDominio, A]

  implicit def toEitherDominio[T](e: ValidationResult[T]): Either[ErrorDominio, T] = {
    e.toEither match {
      case Left(value) => Left(ErroresDominio(value))
      case Right(usuario) => Right(usuario)
    }
  }

  def validarObligatorio[T](valor: Option[T], mensaje: String): ValidationResult[T] = {
    Either.cond(
      valor.isDefined,
      valor.get,
      ErrorDominio.valorObligatorio(mensaje)).toValidatedNel
  }

  def validarLongitudObl(valor: Option[String], longitud: Int, mensaje: String): ValidationResult[Option[String]] = {
    valor match {
      case None => ErrorDominio.valorObligatorio().invalidNel
      case Some(valorOpt) if (valorOpt.length <= longitud) => valor.validNel
      case Some(valorOpt) if (valorOpt.length > longitud) => ErrorDominio.longitudInvalida(mensaje).invalidNel
    }
  }

  def validarLongitud(valor: String, longitud: Int, mensaje: String): ValidationResult[String] = {
    Either.cond(valor.length < longitud, valor, ErrorDominio.longitudInvalida(mensaje)).toValidatedNel
  }

  def validarNoVacio[T](lista: List[T], mensaje: String): ValidationResult[List[T]] = {
    Either.cond(!lista.isEmpty, lista, ErrorDominio.valorObligatorio(mensaje)).toValidatedNel
  }

  def validarPositivo(valor: Double, mensaje: String): ValidationResult[Double] = {
    Either.cond(valor > 0, valor, ErrorDominio.valorInvalido(mensaje)).toValidatedNel
  }

  def validarIgual(valor: Double, valorEsperado: Double, mensaje: String): ValidationResult[Double] = {
    Either.cond(valor == valorEsperado, valor, ErrorDominio.valorInvalido(mensaje)).toValidatedNel
  }

  def validarLongitudMinima(valor: Any, longitudMinima: Int, mensaje: String): ValidationResult[Any] = {
    Either.cond(valor.toString.length > longitudMinima, valor, ErrorDominio.longitudInvalida(mensaje)).toValidatedNel
  }

  def validarMenor(fechaInicial: LocalDateTime, fechaFinal: LocalDateTime, mensaje: String): ValidationResult[LocalDateTime] = {
    Either.cond(fechaInicial.toLocalDate.isBefore(fechaFinal.toLocalDate), fechaInicial, ErrorDominio.valorInvalido(mensaje)).toValidatedNel
  }

  def validarMenor(numeroInicial: Long, numeroFinal: Long, mensaje: String): ValidationResult[Long] = {
    Either.cond(numeroInicial > numeroFinal, numeroInicial, ErrorDominio.valorInvalido(mensaje)).toValidatedNel
  }

  def validarRegex(valor: String, regex: String, mensaje: String): ValidationResult[String] = {
    Either.cond(valor.matches(regex), valor, ErrorDominio.valorInvalido(mensaje)).toValidatedNel
  }

  def validarValido[E <: Enum[E]](valor: String, enumAObtener: Class[E], mensaje: String): ValidationResult[E] = {
    val resultadoOpcional = enumAObtener.getEnumConstants.filter((resultado: E) => resultado.toString == valor).headOption
    Either.cond(resultadoOpcional.isDefined, resultadoOpcional.get, ErrorDominio.valorInvalido(mensaje)).toValidatedNel

  }

  def validarNumerico(valor: String, mensaje: String): ValidationResult[Long] = {
    val respuesta = Try(valor.toLong) match {
      case Success(value) => Right(value)
      case Failure(exception) => Left(ErrorDominio.valorInvalido(mensaje))
    }
    respuesta.toValidatedNel
  }
  def toValidatedNel[T](valor: T): ValidationResult[T] = {
    valor.validNel[DetalleErrorDominio]
  }

  implicit def optToValue[T](valueOpT: Option[T]): T = {
    try
      valueOpT.get
    catch {
      case exception: Exception =>
        throw ExcepcionValorInvalido("Valor invalido", exception)
    }
  }

}

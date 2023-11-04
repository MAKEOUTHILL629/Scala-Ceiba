package co.com.aplicacion

import cats.data.EitherT
import co.com.dominio.errores.ErrorDominio
import co.com.dominio.modelo.Resultado.Respuesta
import monix.eval.Task

package object manejador {
  type RespuestaComando[T] = EitherT[Task, ErrorDominio, ComandoRespuesta[T]]

  implicit def tranformar[T](respuestaDominio: Respuesta[T]): RespuestaComando[T] = {
    respuestaDominio.map(respuesta => ComandoRespuesta[T](respuesta))
  }

}

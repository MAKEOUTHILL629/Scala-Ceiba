package co.com.dominio.modelo

import cats.data.EitherT
import co.com.dominio.errores.ErrorDominio
import monix.eval.Task

object Resultado {

  type Respuesta[T] = EitherT[Task, ErrorDominio, T]

}

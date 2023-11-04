package co.com.infraestructura

import monix.eval.Task
import monix.execution.Scheduler

import scala.concurrent.Future

object FutureTransformador {

  implicit class FutureExtension[T](val future: Future[T]) extends AnyVal {
    def deferFuture(): Task[T] = {
      Task.deferFutureAction { implicit scheduler: Scheduler => future }
    }
  }

}

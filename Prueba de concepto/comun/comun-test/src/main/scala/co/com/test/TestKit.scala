package co.com.test

import scala.concurrent.duration.{Duration, _}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.reflect.ClassTag
import org.scalatestplus.play.PlaySpec

abstract class TestKit extends PlaySpec {
  implicit val ec = ExecutionContext.global
}



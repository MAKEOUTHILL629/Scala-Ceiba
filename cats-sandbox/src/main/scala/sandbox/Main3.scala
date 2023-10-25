package sandbox

import cats._
import cats.implicits._
object Main3 extends App{
 val hilosDisponibles = Runtime.getRuntime.availableProcessors()
 println(hilosDisponibles)

 val value1= Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy"))
  val value2= Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4))

  println(value1)
  println(value2)

 val lazyResult =
  Foldable[List].foldRight(List(1, 2, 3), Now(0))((x, rest) => Later(x + rest.value))

 val foldk = Foldable[List].foldK(List(None, Option("two"), Option("three")))
 println(foldk)



 println(lazyResult.value)

 def parseInt(s: String): Option[Int] =
  Either.catchOnly[NumberFormatException](s.toInt).toOption

 val value11= Foldable[List].traverse_(List("1", "2", "3"))(parseInt)
 val value22= Foldable[List].traverse_(List("a", "b", "c"))(parseInt)

 println(value11)
  println(value22)
}
package sandbox

import cats.{ Functor, Semigroup}
import cats.implicits._


object Main extends App {
  println(Semigroup[Int].combine(1, 2))
  println(Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)))
  println(Semigroup[Option[Int]].combine(Option(1), Option(2)))
  println(Semigroup[Option[Int]].combine(Option(1), None))
  println(
    Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6))



  val aMap = Map("foo" -> Map("bar" -> 5))
  val anotherMap = Map("foo" -> Map("bar" -> 6))
  val combinedMap = Semigroup[Map[String, Map[String, Int]]].combine(aMap, anotherMap)

  println(combinedMap)



  val listOpt = Functor[List] compose Functor[Option];
println(listOpt.map(List(Some(1), None, Some(3)))(_ + 1))
}

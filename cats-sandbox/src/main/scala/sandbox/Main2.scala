package sandbox
import cats.implicits._
object Main2 extends App{
  val option2 = (Option(1), Option(2))
  val option3 = (option2._1, option2._2, Option.empty[Int])
  val addArity2 = (a: Int, b: Int) => a + b
  val addArity3 = (a: Int, b: Int, c: Int) => a + b + c

  println(option2 mapN addArity2 )
  println(option3 mapN addArity3  )

  println(option2 apWith Some(addArity2) )
  println(option3 apWith Some(addArity3) )
  println(option2.tupled)
  println(option3.tupled )
}

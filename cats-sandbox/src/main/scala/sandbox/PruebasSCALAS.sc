trait Printable[A] {
  def format(value: A): String
}

object Printable{
  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)
  def print[A](value: A)(implicit p: Printable[A]): Unit = println(format(value))
}

object PrintableInstances {
  implicit val stringPrintable = new Printable[String] {
    def format(input: String) = input
  }

  implicit val intPrintable = new Printable[Int] {
    def format(input: Int) = input.toString
  }
}


import PrintableInstances._



final case class Cat(name: String, age: Int, color: String)

implicit val catPrintable: Printable[Cat] = new Printable[Cat] {
    def format(cat: Cat) = {
      val name = Printable.format(cat.name)
      val age = Printable.format(cat.age)
      val color = Printable.format(cat.color)
      s"${name} is a ${age} year-old ${color} cat."
    }
}


val caramelo = Cat("Caramelo", 2, "Orange")

Printable.print(caramelo)
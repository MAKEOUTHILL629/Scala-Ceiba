# Buenas practicas

## Algebraic Data Types

Los Tipos Algebraicos de Datos (abreviado ADTs) son una forma de estructurar datos. Son ampliamente utilizados en Scala debido, sobre todo, a lo bien que funcionan con la concordancia de patrones y lo fácil que es utilizarlos para hacer que los estados ilegales sean imposibles de representar.

There are two basic categories of ADTs:

- product types
- sum types

## Product Types

Un tipo de producto es esencialmente una forma de meter múltiples valores dentro de uno - una Tuple, o algo muy similar a una. Las clases Case son el tipo de producto prototípico:

```scala
final case class Foo(b1: Boolean, b2: Boolean)
```

Se llama tipo producto porque podemos calcular su cardinalidad (el número de valores que puede tener) calculando el producto de los tipos que lo componen.

Aquí, Boolean tiene una cardinalidad de 2 (sólo puede contener verdadero o falso), y por lo tanto Foo debe tener una cardinalidad de 4. Efectivamente:

- Foo(true, true)
- Foo(true, false)
- Foo(false, true)
- Foo(false, false)

## Sum Types

Un tipo de suma es un tipo que se compone de diferentes valores posibles y formas de valor. El ejemplo más simple posible es una enumeración - Bool, por ejemplo:

```scala
sealed abstract class Bool extends Product with Serializable

object Bool {
  final case object True extends Bool
  final case object False extends Bool
}
```

Se llama tipo suma porque su cardinalidad es igual a la suma de las aridades de los tipos que lo componen. Aquí, tanto True como False son tipos singleton, y Bool sólo puede tener 2 valores posibles.

Los tipos suma se vuelven mucho más interesantes cuando empiezas a utilizar tipos de datos más complejos para las alternativas.

## Reunirlos

Imaginemos un lenguaje muy sencillo en el que sólo se pueden dar las siguientes instrucciones:

- avanzar X metros
- girar Y grados

Una aplicación ingenua podría ser:

```scala
final case class Command(label: String, meters: Option[Int], degrees: Option[Int])
```

Sin embargo, esto es problemático, ya que permite representar muchos estados ilegales. Por ejemplo:

```scala
Command("foo", None, None)
Command("bar", Some(1), Some(2))
```

Modificando nuestro tipo a un ADT un poco más complicado, nos deshacemos de ellos:

```scala
sealed abstract class Command extends Product with Serializable

object Command {
  final case class Move(meters: Int) extends Command
  final case class Rotate(degrees: Int) extends Command
}
```

Ahora es imposible crear un valor que no tenga sentido: o avanzas X metros o giras Y grados, nada más.

Este tipo también tiene la ventaja de que se adapta muy bien a los patrones:

```scala
def print(cmd: Command) = cmd match {
  case Command.Move(dist)    => println(s"Moving by ${dist}m")
  case Command.Rotate(angle) => println(s"Rotating by ${angle}°")
}
```
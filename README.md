# Scala-Ceiba

## Functional Programming Principles in Javascript

### Que es la programacion funcional?

La programación funcional es un paradigma de programación declarativa que se basa en construir software utilizando funciones puras y evitando el cambio de estado y la mutación de datos. En lugar de especificar cómo hacer algo (como en la programación imperativa), la programación funcional se centra en especificar qué se quiere hacer.

### Funciones puras

¿Cómo sabemos si una función es pura o no? He aquí una definición muy estricta de pureza:

- Son aquellas que, dada la misma entrada, siempre producirán la misma salida y no tendrán ningún efecto secundario.
- No provoca efectos secundarios observables.

#### Ejemplo de funcion pura

```scala
def suma(a: Int, b: Int): Int = {
  return a + b
}
```

Es pura porque siempre que se llame con los mismos argumentos, devolverá el mismo resultado y no tiene efectos secundarios.

#### Ejemplo de funcion impura

```scala
def suma(a: Int, b: Int): Int = {
  println("La suma de " + a + " + " + b + " es: " + (a + b))
  return a + b
}
```

Es impura porque imprime en la consola, lo que es un efecto secundario.

### Funcion impura lectura de archivos

```scala
def charactersCounter(text: String): Int = text.length



def analyzeFile(filename: String): String = {
  val file = new File(filename)
  val contents = file.read()
  return charactersCounter(contents)
}

```

La función analyzeFile(filename) no es pura porque su salida depende del contenido del archivo especificado por filename, que puede cambiar con el tiempo. Esto significa que la misma entrada (es decir, el mismo nombre de archivo) puede producir diferentes salidas en diferentes momentos, violando la primera condición.

charactersCounter(text) sí es una función pura porque para un text dado, siempre producirá la misma salida y no tiene efectos secundarios. No modifica ni interactúa con ningún estado fuera de la función.

### Funciones impuras numeros aleatorios

Cualquier función que dependa de un generador de números aleatorios no puede ser pura.

```scala
def random(): Int = {
  return System.nanoTime()
}

def randomBetween(min: Int, max: Int): Int = {
  return min + random() % (max - min)
}
```

Las funciones puras son estables, coherentes y predecibles. Dados los mismos parámetros, las funciones puras siempre devolverán el mismo resultado. No necesitamos pensar en situaciones en las que el mismo parámetro tenga resultados diferentes, porque nunca ocurrirá.

### Ventajas de las funciones puras

El código es definitivamente más fácil de probar. No necesitamos simular nada. Así que podemos probar unitariamente funciones puras con diferentes contextos:

- Dado un parámetro A ➡️ esperar que la función devuelva el valor B
- Dado un parámetro C ➡️ esperar que la función devuelva el valor D

Ejemplo de funcion pura 

```scala
val lista = List(1, 2, 3, 4, 5)

def incrementarNumeros(lista: List[Int]): List[Int] = lista.map(numero => numero + 1)

incrementarNumeros(lista) // List(2, 3, 4, 5, 6)
```

Para la entrada de [1,2,3,4,5] siempre obtendremos la salida [2,3,4,5,6].

## Inmutabilidad

Inmutable en el tiempo o incapaz de modificarse. En programación, la inmutabilidad es un concepto que se refiere a la incapacidad de un objeto de cambiar su valor de estado después de su creación.

### Ejemplo de inmutabilidad

```javascript
var values = [1, 2, 3, 4, 5];
var sumOfValues = 0;

for (var i = 0; i < values.length; i++) {
  sumOfValues += values[i];
}

sumOfValues // 15
```

Para cada iteración, estamos cambiando el i y el estado sumOfValue. Pero, ¿cómo manejamos la mutabilidad en la iteración? Recursión.

```javascript
var values = [1, 2, 3, 4, 5];
let accumulator = 0;

function sum(list, accumulator) {
  if (list.length == 0) {
    return accumulator;
  }

  return sum(list.slice(1), accumulator + list[0]);
}

sum(list, accumulator); // 15
list; // [1, 2, 3, 4, 5]
accumulator; // 0
```

Así que aquí tenemos la función suma que recibe un vector de valores numéricos. La función se llama a sí misma hasta que obtenemos la lista vacía (nuestro caso base de recursividad). En cada "iteración" añadiremos el valor al acumulador total.

### Ejemplo de inmutabilidad URL

En programacion orientada a objetos se tiene un metodo para convertir un string a url slug es decir que se reemplazan los espacios por guiones y se convierte a minusculas.

```ruby
class UrlSlugify
  attr_reader :text
  
  def initialize(text)
    @text = text
  end

  def slugify!
    text.downcase!
    text.strip!
    text.gsub!(' ', '-')
  end
end

UrlSlugify.new(' I will be a url slug   ').slugify! # "i-will-be-a-url-slug"
```

En este ejemplo se puede ver que el metodo slugify! Que hace los cambios en el texto para convertirlo en un slug, sin embargo el metodo esta modificando el texto original, lo cual es un problema porque si se quiere usar el texto original en otro lugar ya no se podra.

Como alternativa se puede realizar una funcion que sea una composicion de funciones sin tener que modificar que string original

```javascript
const string = " I will be a url slug   ";

const slugify = string =>
  string
    .toLowerCase()
    .trim()
    .split(" ")
    .join("-");

slugify(string); // i-will-be-a-url-slug
string; // " I will be a url slug   "
```

- toLowerCase() convierte el string a minusculas
- trim() elimina los espacios en blanco al inicio y al final del string
- split(" ") divide el string en un array de strings
- join("-") une el array de strings en un string usando guiones

## Transparencia referencial

La transparencia referencial es un concepto que nos permite reemplazar una expresión por su valor sin cambiar el comportamiento del programa.

funciones puras + datos inmutables = transparencia referencial

Con la transparencia referencial, podemos memoizar la funcion y guardar el resultado en cache para que no se tenga que volver a ejecutar la funcion con los mismos parametros.

```javascript
const sum = (a, b) => a + b
```

Se llama con los siguientes parametros

```javascript
sum(3, sum(5, 8));
```

La suma de 5 y 8 es igual a 13, por lo que la expresion se puede reemplazar por

```javascript
sum(3, 13);
```

Y esta expresión siempre dará como resultado 16. Podemos sustituir toda la expresión por una constante numérica y memorizarla.

## Funciones como entidades de primera clase

En programación funcional, las funciones son ciudadanos de primera clase, lo que significa que son tratadas como cualquier otra variable, tienen las siguientes características:

- hacer referencia a él desde constantes y variables
- pasarlo como parámetro a otras funciones
- devolverlo como resultado de otras funciones

La principal idea es tratar a las funciones como valores y pasarlas a otras funciones para que las ejecuten y crear nuevas funciones a partir de las existentes.

Ejemplo de funciones como entidades de primera clase

```javascript
const doubleSum = (a, b) => (a + b) * 2;
const doubleSubtraction = (a, b) => (a - b) * 2;


// Se puede hacer esto con funciones de primera clase
const sum = (a, b) => a + b;
const subtraction = (a, b) => a - b;
const doubleOperator = (f, a, b) => f(a, b) * 2;

doubleOperator(sum, 3, 1); // 8
doubleOperator(subtraction, 3, 1); // 4
```

Ahora tenemos un argumento f, y lo usamos para procesar a y b. Pasamos las funciones suma y resta para componer con la función doubleOperator y crear un nuevo comportamiento.

## Funciones de orden superior

Las funciones de orden superior son funciones que pueden tomar otras funciones como argumentos o devolverlas como resultado.

Un buen ejemplo de son las funciones filter, map y reduce de los arrays.

### Filter

Dada una colección, devuelve una colección filtrada por una condición dada como funcion, es decir que se le pasa una funcion que devuelve un booleano y se filtra la coleccion con los elementos que devuelven true.

Ejemplo de filtrar numeros par con un enfoque imperativo

```javascript

const numbers = [1, 2, 3, 4, 5, 6];
var evenNumbers = [];

for (var i = 0; i < numbers.length; i++) {
  if (numbers[i] % 2 == 0) {
    evenNumbers.push(numbers[i]);
  }
}

console.log(evenNumbers); // (6) [0, 2, 4, 6, 8, 10]
```

Sin embargo se puede usar la funcion de orden superior filter

```javascript
const numbers = [1, 2, 3, 4, 5, 6];
const even = n => n % 2 == 0;

const evenNumbers = numbers.filter(even);

console.log(evenNumbers); // (6) [0, 2, 4, 6, 8, 10]
```

También podemos hacerlo con mapas. Imagina que tenemos un mapa de personas con su nombre y edad. Queremos filtrar sólo las personas que superen un valor determinado de edad, en este ejemplo las personas que tengan más de 21 años.

```javascript
let people = [
  { name: "TK", age: 26 },
  { name: "Kaio", age: 10 },
  { name: "Kazumi", age: 30 }
];

const olderThan21 = person => person.age > 21;
const overAge = people => people.filter(olderThan21);
overAge(people); // [{ name: 'TK', age: 26 }, { name: 'Kazumi', age: 30 }]
```

### Map

Dada una colección, devuelve una colección con cada elemento transformado por una función dada.

Aprovechando el ejemplo anterior, podemos usar map para transformar cada elemento de la colección en un string con el nombre de la persona y edad.

```javascript
const makeSentence = (person) => `${person.name} is ${person.age} years old`;

const peopleSentences = (people) => people.map(makeSentence);
  
peopleSentences(people);
// ['TK is 26 years old', 'Kaio is 10 years old', 'Kazumi is 30 years old']
```

La idea es transformar una array dado en una nuevo array.

### Reduce

Dada una colección y una función, devuelve un valor que es la reducción de esa colección. La función reduce recibe dos parámetros, el primero es la función que se va a ejecutar y el segundo es el valor inicial.

Un ejemplo habitual del que habla la gente es obtener el importe total de un pedido. Imagina que estás en un sitio web de compras. Has añadido el Producto 1, el Producto 2, el Producto 3 y el Producto 4 a tu cesta de la compra (pedido). Ahora queremos calcular el importe total del carrito de la compra.

```javascript
let shoppingCart = [
  { productTitle: "Product 1", amount: 10 },
  { productTitle: "Product 2", amount: 30 },
  { productTitle: "Product 3", amount: 20 },
  { productTitle: "Product 4", amount: 60 }
];

const sumAmount = (currentTotalAmount, order) => currentTotalAmount + order.amount;

const getTotalAmount = (shoppingCart) => shoppingCart.reduce(sumAmount, 0);

getTotalAmount(shoppingCart); // 120
```

La función reduce recibe dos parámetros, el primero es la función que se va a ejecutar y el segundo es el valor inicial. En este caso, el valor inicial es 0 porque queremos que el importe total comience en 0 y luego se sume a medida que se procesan los pedidos.

## Ejemplo de funciones de orden superior

Hablando del carrito de la compra, imaginemos que tenemos esta lista de productos en nuestro pedido:

Queremos el importe total de todos los libros de nuestra cesta de la compra. Así de sencillo. ¿El algoritmo?

- filtrar por tipo de libro
- transformar la cesta de la compra en una colección de importes usando map
- combinar todos los artículos sumándolos con reduce

```javascript
let shoppingCart = [
  { productTitle: "Functional Programming", type: "books", amount: 10 },
  { productTitle: "Kindle", type: "eletronics", amount: 30 },
  { productTitle: "Shoes", type: "fashion", amount: 20 },
  { productTitle: "Clean Code", type: "books", amount: 60 }
];

let shoppingCart = [
  { productTitle: "Functional Programming", type: "books", amount: 10 },
  { productTitle: "Kindle", type: "eletronics", amount: 30 },
  { productTitle: "Shoes", type: "fashion", amount: 20 },
  { productTitle: "Clean Code", type: "books", amount: 60 }
]

const byBooks = (order) => order.type == "books";
const getAmount = (order) => order.amount;
const sumAmount = (acc, amount) => acc + amount;

function getTotalAmount(shoppingCart) {
  return shoppingCart
    .filter(byBooks)
    .map(getAmount)
    .reduce(sumAmount, 0);
}

getTotalAmount(shoppingCart); // 70

```

### Case Classes

Las case classes son clases que tienen un constructor por defecto, getters y setters para los atributos y métodos equals, hashCode y toString implementados.

```scala
case class Person(firstName: String, lastName: String) {
  def name = firstName + " " + lastName
}
```

Las case clase automaticamente son generadas por Scala con objeto de compañia.

- ToString para mostrar el objeto en forma de string con el nombre de la clase y los valores de los atributos, como si fuera el constructor, de esa manera se evita el @ y el hashcode.

```scala
val person = Person("Juan", 30)

person.toString // Person(Juan,30)
```

- Equals sensible, y hashCode métodos que operan sobre los valores de campo en el objeto.

```scala
new Person("Noel", "Welsh").equals(new Person("Noel", "Welsh"))
// res3: Boolean = true

new Person("Noel", "Welsh") == new Person("Noel", "Welsh")
// res4: Boolean = true
```

- Un método de copy que crea un nuevo objeto con los mismos valores de campo que el actual:

```scala
val person = Person("Juan", 30)
person.copy(age = 22) // Person = Person(Juan,22)
```

- Las clases Case implementan dos rasgos: java.io.Serializable y scala.Product. Ninguno de los dos se utiliza directamente. Este último proporciona métodos para inspeccionar el número de campos y el nombre de la clase case.

- El objeto compañero contiene un método apply con los mismos argumentos que el constructor de la clase. Los programadores de Scala tienden a preferir el método apply sobre el constructor por la brevedad de omitir new
  
### Case Objects

- Los objetos case son como las clases case, excepto que no tienen constructores de parámetros y no se puede instanciar con new. Son útiles para representar valores atómicos, Un objeto case se define igual que un objeto singleton normal, pero tiene un método toString más significativo y extiende los rasgos Product y Serializable

```scala
case object Citizen {
  def firstName = "John"
  def lastName  = "Doe"
  def name = firstName + " " + lastName
}

Citizen.toString // Citizen
```

### Pattern Matching

Las clases case permiten una nueva forma de interacción, llamada concordancia de patrones. La concordancia de patrones nos permite separar una clase case y evaluar diferentes expresiones dependiendo de lo que contenga la clase case.

```scala
expr0 match {
  case pattern1 => expr1
  case pattern2 => expr2
  ...
}
```

- un nombre, vinculando cualquier valor a ese nombre;
- un guión bajo, que coincide con cualquier valor y lo ignora
- un literal, que coincide con el valor que el literal denota; o
- un patrón estilo constructor para una clase case.

```scala
object ChipShop {
  def willServe(cat: Cat): Boolean =
    cat match {
      case Cat(_, _, "Chips") => true
      case Cat(_, _, _) => false
      case _ => false
    }
}
```

### Traits

Los traits son como interfaces en Java, pero con implementaciones. Los rasgos pueden tener métodos y campos, y pueden proporcionar implementaciones predeterminadas para los métodos. Los traits pueden extenderse por clases y otros rasgos, pero no pueden tomar parámetros de tipo de clase. Es una especide mezcla entre las interfaces de Java y las clases abstractas.

```scala
import java.util.Date

trait Visitor {
  def id: String      // Unique id assigned to each user
  def createdAt: Date // Date this user first visited the site

  // How long has this visitor been around?
  def age: Long = new Date().getTime - createdAt.getTime
}

case class Anonymous(
  id: String,
  createdAt: Date = new Date()
) extends Visitor

case class User(
  id: String,
  email: String,
  createdAt: Date = new Date()
) extends Visitor

```

Sintaxis usada para un trait

```scala
//Declaracion
trait TraitName {
  declarationOrExpression ...
}
//Declarar que una clase es un subtipo de un trait
class Name(...) extends TraitName {
  ...
}
//Declarar que un case class es un subtipo de un trait
case class Name(...) extends TraitName {
 ...
}

### Traits vs Classes

- Un trait no puede tener un constructor: no podemos crear objetos directamente a partir de un trait. En su lugar, podemos utilizar un trait para crear una clase, y luego crear objetos a partir de esa clase. Podemos basar tantas clases como queramos en un trait.
- Los traits pueden definir métodos abstractos que tienen nombres y firmas de tipo pero no implementación. Hemos visto esto en el trait Visitante. Debemos especificar la implementación cuando creamos una clase que extiende el trait, pero hasta ese momento somos libres de dejar las definiciones abstractas.

```scala
trait Visitor {
  def id: String      // Unique id assigned to each user
  def createdAt: Date // Date this user first visited the site
}
```

id y createdAt son abstractos, por lo que deben definirse en las clases que los extienden. Nuestras clases los implementan como vals en lugar de defs. Esto es legal en Scala, que ve def como una versión más general de val. Es una buena práctica no definir nunca vals en un trait, sino usar def. Una implementación concreta puede entonces implementarlo usando def o val según sea apropiado.

### Sealed Traits

Un trait sellado es un trait que no puede tener implementaciones de clases fuera del archivo en el que está definido. Esto permite que el compilador verifique que la concordancia de patrones es exhaustiva.

Aún podemos extender los subtipos de un rasgo sellado fuera del fichero donde están definidos. Por ejemplo, podríamos extender Usuario o Anónimo en otro lugar. Si queremos evitar esta posibilidad debemos declararlos como sellados (si queremos permitir extensiones dentro del fichero) o final si queremos desautorizar todas las extensiones. Para el ejemplo de los visitantes probablemente no tenga sentido permitir ninguna extensión a Usuario o Anónimo, así que el código simplificado debería tener este aspecto:

```scala
//definicion de un trait sellado
sealed trait TraitName {
  ...
}

//definicion de una clase final para no permitir extensiones
final case class Name(...) extends TraitName {
  ...
}

sealed trait Visitor { /* ... */ }
final case class User(/* ... */) extends Visitor
final case class Anonymous(/* ... */) extends Visitor

// Ejemplo de division de un numero
sealed trait DivisionResult
final case class Finite(value: Int) extends DivisionResult
case object Infinite extends DivisionResult

object divide {
  def apply(num: Int, den: Int): DivisionResult =
    if(den == 0) Infinite else Finite(num / den)
}

divide(1, 0) match {
  case Finite(value) => s"It's finite: ${value}"
  case Infinite      => s"It's infinite"
}
// res34: String = It's infinite

```

## Modelando Datos con Traits

### Algebraic Data Types

#### Product Types

Los tipos de producto son tipos que tienen más de un valor. Por ejemplo, un tipo de producto que representa un usuario puede tener un valor para un usuario anónimo y un valor para un usuario registrado. Un Visitor tiene un id y una fecha de creacion.

```scala
case class A(b:B, c:C)

//Alternativa
trait A{
  def b: B
  def c: C
}
```

#### Sum Types

Los tipos de suma son tipos que tienen más de un tipo de valor. Por ejemplo, UUn felino es un gato, leon o tigre.

```scala
sealed trait A
case class B() extends A
case class C() extends A
```

#### Is-a-and

```scala
trait B
trait C
trait A extends B with C
```

A es b y c

#### has-a-or

A es b o c

```scala
trait A {
  def d: D
}
sealed trait D
final case class B() extends D
final case class C() extends D

//Alternativa
sealed trait A
final case class D(b: B) extends A
final case class E(c: C) extends A
```

### Structural Recursion

#### El product type Polimorfismo Pattern

A tiene un b y un c, se quiere escribir un metodo f que retorne un f

```scala
case class A(b: B, c: C) {
  def f: F = ???
}
```

#### La Sum Type Polimorfismo Pattern

A es b o c, se quiere escribir un metodo f que retorne un f

```scala
sealed trait A {
  def f: F 
}
final case class B() extends A {
  def f: F = ???
}
final case class C() extends A {
  def f: F = ???
}
```

#### Product Type Pattern Matching Pattern

A tiene un b y un c, se quiere escribir un metodo f que retorne un f

```scala
def f(a: A): F =
  a match {
    case A(b, c) => ???
  }
```

#### Sum Type Pattern Matching Pattern

A es b o c, se quiere escribir un metodo f que retorne un f

```scala
def f(a: A): F =
  a match {
    case B() => ???
    case C() => ???
  }
```

### Recursion Alegbraica Data Types Pattern

Cuando se definen tipos de datos algebraicos recursivos, debe haber al menos dos casos: uno que sea recursivo y otro que no lo sea. Los casos que no son recursivos se conocen como casos base. En código, el esqueleto general es

```scala
sealed trait RecursiveExample
final case class RecursiveCase(recursion: RecursiveExample) extends RecursiveExample
case object BaseCase extends RecursiveExample
```

Al escribir código estructuralmente recursivo en un tipo de datos algebraico recursivo:

- siempre que encontremos un elemento recursivo en los datos hacemos una llamada recursiva a nuestro método; y
- siempre que encontremos un caso base en los datos devolvemos la identidad para la operación que estamos realizando

#### Comprender el caso base y el caso recursivo

- Para el caso base, generalmente deberíamos devolver la identidad de la función que estamos intentando calcular. La identidad es un elemento que no cambia el resultado. Por ejemplo, 0 es la identidad de la suma, porque a + 0 == a para cualquier a. Si estuviéramos calculando el producto de elementos, la identidad sería 1, ya que a * 1 == a para todo a.
- Para el caso recursivo, asume que la recursión devolverá el resultado correcto y calcula lo que necesitas sumar para obtener la respuesta correcta. Hemos visto esto para la suma, donde asumimos que la llamada recursiva nos dará el resultado correcto para la cola de la lista y entonces simplemente sumamos en la cabeza.
  
#### Ejemplo de recursion

```scala
sealed trait IntList
case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

def sum(list: IntList): Int =
  list match {
    case End => 0 // Caso base
    case Pair(hd, tl) => hd + sum(tl) // Llamada recursiva, asumimos que sum(tl) es correcto
  }


val example = Pair(1, Pair(2, Pair(3, End))) // 1, 2, 3
assert(sum(example) == 6)
assert(sum(example.tail) == 5)
assert(sum(End) == 0)

```

### Tail Recursion

La recursión de cola es una técnica de optimización que permite que algunas funciones recursivas se ejecuten en un espacio constante de memoria. La idea es que la llamada recursiva sea la última operación que se realiza en la función. Cuando esto sucede, la función no necesita mantener el estado de la llamada actual en la pila. En su lugar, puede reemplazar la llamada actual por la llamada recursiva.

```scala
// Ejemplo de Tail Recursion
def method1: Int =
  1

def tailCall: Int =
  method1 // tailCall no necesita mantener el estado de la llamada actual en la pila, porque tailCall devuelve inmediatamente el resultado de llamar al método1

// Ejemplo de No Tail Recursion
def notATailCall: Int =
  method1 + 2 // notATailCall necesita mantener el estado de la llamada actual en la pila, porque notATailCall necesita sumar el resultado de llamar al método1 con 2

```

Existe una anotación @tailrec que se puede usar para verificar que una función se está optimizando correctamente. Si la función no se optimiza correctamente, el compilador nos avisará.

```scala

@tailrec
def sum(list: IntList): Int =
  list match {
    case End => 0
    case Pair(hd, tl) => hd + sum(tl)
  }
// Error: could not optimize @tailrec annotated method sum: it contains a recursive call not in tail position


// Convirtiendo la funcion a tail recursion
@tailrec
def sum(list: IntList, total: Int = 0): Int =
  list match {
    case End => total
    case Pair(hd, tl) => sum(tl, total + hd)
  }


```

## Ejericios interesantes

### Option

Asignar valores por defecte utilizando pattern matching

```scala
val someValue: Option[Double] = Some(20.0)
val value = someValue match {
  case Some(v) => v
  case None => 0.0
}
```

### Object Singleton

Uso de atributos privados desde el object compani

```scala
class Person(
  val name: String,
  private val superheroName: String) //The superhero name is private!

object Person {
  def showMeInnerSecret(x: Person) = x.superheroName
}

val clark = new Person("Clark Kent", "Superman")
val peter = new Person("Peter Parker", "Spider-Man")

Person.showMeInnerSecret(clark) should be(
"Superman"
)
Person.showMeInnerSecret(peter) should be(
"Spider-Man"
)
```

### Functions Types

```scala
//declarar una funcion type
(A, B, ...) => C
```

- Donde A, B, ... son los tipos de los parametros de la funcion
- C es el tipo de retorno de la funcion

```scala
//declarar una funcion type con un solo parametro
A => B
```

### Fold Pattern

Para un tipo de datos algebraico A, fold lo convierte en un tipo genérico B. Fold es una recursión estructural con:

- un parámetro de función para cada caso de A;
- cada función toma como parámetros los campos de su clase asociada;
- si A es recursivo, cualquier parámetro de función que se refiera a un campo recursivo toma un parámetro de tipo B.

### Funciones extreme shorthand

```scala
((_: Int) * 2) // expandiendo la funcion (a: Int) => a * 2
_ + _     // expandiendo la funcion`(a, b) => a + b`
foo(_)    // expandiendo la funcion `(a) => foo(a)`
foo(_, b) // expandiendo la funcion`(a) => foo(a, b)`
_(foo)    // expandiendo la funcion`(a) => a(foo)`
```

## Cats

### Type Class

Una type class es una interfaz que especifica algún comportamiento que queremos que un tipo de datos en particular tenga. El tipo de datos no es parte de la type class, pero en su lugar se define en otro lugar. Esto es diferente de una clase en un lenguaje orientado a objetos, donde la clase y la interfaz son lo mismo. En Cats, una clase de tipo está representada por un rasgo con al menos un parámetro de tipo

```scala
trait JsonWriter[A] {
  def write(value: A): Json
}
```

JsonWriter es nuestra clase de tipo en este ejemplo, con Json y sus subtipos proporcionando código de soporte.

### Type Class Instances

Una instancia de clase de tipo es una implementación de los métodos de una clase de tipo para un tipo de datos específico. En Cats, las instancias de clase de tipo se representan con objetos singleton.En Scala definimos instancias creando implementaciones concretas de la clase de tipo y etiquetándolas con la palabra clave implícita, Por ejemplo, podríamos definir una instancia de clase de tipo para nuestro tipo de datos Person como:

```scala
final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json =
        JsString(value)
    }

  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
    }

  // etc...
}
```

### Type Class Interfaces

Una interfaz de clase de tipo es cualquier funcionalidad que expone los métodos de una clase de tipo al usuario. En Cats, las interfaces de clase de tipo se representan con objetos singleton o clases estáticas que aceptan instancias de clase de tipo en sus métodos. Una interfaz de clase de tipo es cualquier funcionalidad que exponemos a los usuarios. Las interfaces son métodos genéricos que aceptan instancias de la clase de tipo como parámetros implícitos.

#### Interface Objects

La forma más sencilla de crear una interfaz es colocar métodos en un objeto singleton:

```scala
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}
```

Para utilizar este objeto, importamos cualquier instancia de clase de tipo que nos interese y llamamos al método correspondiente:

```scala
import JsonWriterInstances._

Json.toJson(Person("Dave", "dave@example.com"))
// res4: Json = JsObject(Map(name -> JsString(Dave), email -> JsString(dave@example.com)))
```

#### Interface Syntax

También podemos utilizar métodos de extensión para ampliar los tipos existentes con métodos de interfaz. Cats denomina a esto "sintaxis" de la clase de tipos:

```scala
object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json =
      w.write(value)
  }
}
```

Utilizamos la sintaxis de interfaz importándola junto con las instancias de los tipos que necesitamos:

```scala
import JsonWriterInstances._
import JsonSyntax._

Person("Dave", "dave@example.com").toJson
// res6: Json = JsObject(Map(name -> JsString(Dave), email -> JsString(dave@example.com)))
```

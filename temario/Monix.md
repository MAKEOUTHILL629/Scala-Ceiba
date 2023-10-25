# Monix

## Introduccion

Task es un tipo de datos para controlar cálculos posiblemente perezosos y asíncronos, útil para controlar efectos secundarios, evitar el no determinismo y el callback-hell.

Para quitar las importaciones de en medio:

```scala
// Para evaluar las tareas, necesitaremos un Programador
import monix.execution.Scheduler.Implicits.global

//Un tipo de Futuro que también es Cancelable
import monix.execution.CancelableFuture

// La tarea está en monix.eval
import monix.eval.Task
import scala.util.{Success, Failure}
```

```scala
// Ejecutar una suma, que (debido a la semántica de apply)
// ocurrirá en otro hilo. No pasa nada al construir
// esta instancia, esta expresión es pura, siendo
// ¡sólo una especificación! La tarea por defecto tiene un comportamiento perezoso ;-)

val task = Task { 1 + 1 }

// ¡Las tareas se evalúan sólo en runToFuture!
// Estilo Callback:
val cancelable = task.runAsync { result =>
  result match {
    case Right(value) =>
      println(value)
    case Left(ex) =>
      System.out.println(s"ERROR: ${ex.getMessage}")
  }
}

//=> 2

// O puedes convertirlo en un Futuro

val future: CancelableFuture[Int] =
  task.runToFuture

// Imprimir el resultado de forma asíncrona
future.foreach(println)
//=> 2
```

## Comparacion con Future Scala

Task suena parecido con Future de Scala, pero tiene un carácter diferente y los dos tipos como verás son en realidad complementarios. Un hombre sabio dijo una vez:

`"Un Futuro representa un valor, desvinculado del tiempo" - Viktor Klang`

Es ciertamente una noción poética, que hace pensar en qué son los valores y cómo incorporan el tiempo. Pero lo que es más importante, aunque no podemos decir que un Futuro es un valor, sí podemos decir que es un aspirante a valor, lo que significa que cuando los usuarios reciben una referencia a un Futuro, saben que cualquier proceso que vaya a evaluarlo probablemente ya ha comenzado e incluso puede que ya haya terminado. Esto hace que el comportamiento del Futuro de Scala sea sobre evaluación ansiosa y ciertamente su diseño ayuda con eso, si piensas en cómo toma ese contexto de ejecución implícito cada vez que llamas a sus operadores, como map y flatMap.

Pero Task es diferente. Task se basa en la evaluación perezosa. Bueno, no siempre perezosa, de hecho Task permite afinar el modelo de ejecución, como verás, pero esa es la principal distinción entre ellas. Si Future es como un valor, Task es como una función. Y de hecho Task puede funcionar como una "fábrica" de instancias de Future.

Otra distinción es que Future es "memoized" por defecto, lo que significa que su resultado va a ser compartido entre múltiples consumidores si es necesario. Pero la evaluación de una Task no está memoizada por defecto. No, tienes que querer que la memoización ocurra, tienes que especificarlo explícitamente, como verás.

En términos de eficiencia, Future teniendo un comportamiento eager, resulta ser menos eficiente porque cualquier operación que estés haciendo en él, la implementación terminará enviando instancias Runnable en el thread-pool y porque el resultado siempre está memoizado en cada paso, invocando esa maquinaria (por ejemplo entrando en bucles compare-and-set) hagas lo que hagas. Por otro lado Task puede hacer la ejecución en lotes síncronos.

## Comparación con la Task de Scalaz

No es ningún secreto que Monix Task se inspiró en Scalaz Task, una implementación por lo demás sólida. Toda la librería Monix se apoya en los hombros de gigantes. Pero donde la implementación Monix Task no está de acuerdo:

- La Scalaz Task está filtrando detalles de implementación. Esto se debe a que la Scalaz Task es, ante todo, sobre la ejecución trampolinada, pero la ejecución asíncrona se trata de saltar por encima de los límites asíncronos y por lo tanto trampolín. Así que la API está limitada por lo que el trampolín puede hacer y, por ejemplo, para no bloquear el hilo actual en un gran bucle, tienes que insertar manualmente los límites asíncronos tú mismo mediante Task.executeAsync. La tarea Monix, por otro lado, se las arregla para hacerlo automáticamente por defecto, lo cual es muy útil cuando se ejecuta sobre Javascript, donde la multitarea cooperativa no es sólo agradable de tener, sino necesaria.
- La tarea Scalaz tiene una doble personalidad síncrona / asíncrona. Eso está bien para fines de optimización en lo que respecta al productor (es decir, ¿por qué bifurcar un hilo cuando no es necesario), pero desde el punto de vista del consumidor tener una ejecución def: A significa que la API no puede ser soportada completamente sobre Javascript y sobre la JVM significa que la Task acaba fingiendo una evaluación síncrona y bloqueando hilos. Y bloquear hilos es muy inseguro.
- La tarea Scalaz no puede cancelar la ejecución de los cálculos. Esto es importante para las operaciones no deterministas. Por ejemplo, cuando se crea una condición de carrera con una carrera, es posible que desee cancelar la tarea más lenta que no terminó a tiempo, porque por desgracia, si no liberamos los recursos lo suficientemente pronto, podemos terminar con una fuga grave que puede bloquear nuestro proceso.
- La tarea Scalaz se apoya en la biblioteca estándar de Java para hacer frente a la ejecución asíncrona. Esto es malo por razones de portabilidad, ya que esta API no está soportada en Scala.js.

## Ejecucion (runToFuture & ForEach)

Las instancias de tarea no harán nada hasta que se ejecuten mediante runToFuture. Y existen múltiples sobrecargas de la misma.

Task.runToFuture también quiere un Programador implícito en el ámbito, que puede suplantar tu ExecutionContext (ya que hereda de él). Pero aquí es donde el diseño de Task diverge del propio Future de Scala. La Tarea es perezosa, sólo quiere este Programador en la ejecución con runToFuture, en lugar de quererlo en cada operación (como map o flatMap), como hace el Futuro de Scala.

Así que lo primero es lo primero, necesitamos un Programador en el ámbito. El global es piggybacking en el propio global de Scala, así que ahora usted puede hacer esto:

```scala
import monix.execution.Scheduler.Implicits.global
```

NOTA: El Programador puede inyectar un modelo de ejecución configurable que determina cómo se fuerzan (o no) los límites asíncronos. Infórmate sobre ello.

La forma más directa e idiomática sería ejecutar tareas y obtener a cambio un CancelableFuture, que es un Future estándar emparejado con un Cancelable:

```scala
import monix.eval.Task
import monix.execution.CancelableFuture
import concurrent.duration._

val task = Task(1 + 1).delayExecution(1.second)

val result: CancelableFuture[Int] =
  task.runToFuture

// Si cambiamos de opinión
result.cancel()
```

Devolver un Future puede ser demasiado pesado para tus necesidades, puede que quieras proporcionar un simple callback. También podemos ejecutarAsync con un callback Either[Throwable, A] => Unit, similar al Future.onComplete estándar.

```scala
val task = Task(1 + 1).delayExecution(1.second)

val cancelable = task.runAsync { result =>
  result match {
    case Right(value) =>
      println(value)
    case Left(ex) =>
      System.err.println(s"ERROR: ${ex.getMessage}")
  }
}

// Si cambiamos de opinión
cancelable.cancel()
```

También podemos runAsync con una instancia Callback. Esto es como una API tipo Java, útil en caso de que, por cualquier razón, quieras mantener el estado. Callback también se utiliza internamente, porque nos permite protegernos contra violaciones de contrato y evitar el boxeo específico de Try[T] o Either[E, A]. Ejemplo:

```scala
import monix.execution.Callback

val task = Task(1 + 1).delayExecution(1.second)

val cancelable = task.runAsync(
  new Callback[Throwable, Int] {
    def onSuccess(value: Int): Unit =
      println(value)
    def onError(ex: Throwable): Unit =
      System.err.println(s"ERROR: ${ex.getMessage}")
  })

// Si cambiamos de opinión
cancelable.cancel()
```
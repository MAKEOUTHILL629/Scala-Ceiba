# Cats Effect

## Conceptos

### Asincronismo

En concreto, "asíncrono" es lo contrario de "síncrono", y se refiere a la forma en que un efecto determinado produce un valor. Los efectos síncronos se definen mediante apply (también delay, blocking, interruptible o interruptibleMany) y producen sus resultados mediante return, o bien provocan errores mediante throw. Se trata de los conocidos efectos de tipo "secuencial":

```scala
IO(Thread.sleep(500))   // => IO[Unit]
```

Los efectos asíncronos se definen usando async (o async_) y producen sus resultados usando un callback, donde un resultado exitoso se envuelve en Right mientras que un error se envuelve en Left:

```scala
import java.util.concurrent.{Executors, TimeUnit}

val scheduler = Executors.newScheduledThreadPool(1)

IO.async_[Unit] { cb =>
  scheduler.schedule(new Runnable {
    def run = cb(Right(()))
  }, 500, TimeUnit.MILLISECONDS)

  ()
}
// => IO[Unit]
```

Tanto el efecto Thread.sleep como el efecto schedule mostrados aquí tienen la misma semántica: retrasan 500 milisegundos antes de permitir que tenga lugar el siguiente paso en la fibra. En lo que difieren es en la forma en que fueron definidos: Thread.sleep es síncrono mientras que schedule es asíncrono. Las implicaciones de esto son sorprendentemente profundas. Dado que Thread.sleep no devuelve el flujo de control a nivel de la JVM hasta que expira su retardo, desperdicia un recurso escaso (el Thread del núcleo subyacente) durante toda su duración, impidiendo que otras acciones utilicen ese recurso de forma más eficiente mientras tanto. Por el contrario, schedule vuelve inmediatamente cuando se ejecuta y simplemente invoca la llamada de retorno en el futuro una vez que el tiempo dado ha transcurrido. Esto significa que el subproceso del núcleo subyacente no se desperdicia y puede reutilizarse para evaluar otras tareas mientras tanto.

Los efectos asíncronos son considerablemente más eficientes que los síncronos (siempre que sean aplicables, como para E/S de red o temporizadores), pero generalmente se considera que son más difíciles de trabajar en aplicaciones reales debido a la necesidad de gestionar manualmente las retrollamadas y los escuchadores de eventos. Las fibras eliminan completamente esta desventaja debido a su soporte integrado para efectos asíncronos. En los dos ejemplos anteriores, el efecto en cuestión es simplemente un valor de tipo IO[Unit], y desde fuera, ambos efectos se comportan de forma idéntica. Así, la diferencia entre return/throw y un callback se encapsula completamente en el sitio de definición, mientras que el resto del flujo de control de tu aplicación permanece completamente ajeno. Esta es una gran parte del poder de las fibras.

Es fundamental señalar que nada en la definición de "asíncrono" implica "paralelo" o "simultáneo", ni niega el significado de "secuencial" (recuerde: todas las fibras son secuencias de efectos). "Asíncrono" significa simplemente "produce valores o errores utilizando una llamada de retorno en lugar de return/throw". Se trata de un detalle de implementación de un efecto, gestionado por una fibra, en lugar de un patrón fundamental más amplio en torno al cual diseñar.

### Concurencia

En el lenguaje de Cats Effect, "concurrente" se refiere generalmente a dos o más acciones que se definen como independientes en su flujo de control. Es lo contrario de "secuencial", o mejor dicho, "secuencial" implica que algo no puede ser "concurrente". Críticamente, es posible que las cosas que son "concurrentes" se evalúen secuencialmente si el tiempo de ejecución subyacente decide que es lo óptimo, mientras que las acciones que son secuenciales siempre se evaluarán una tras otra.

La concurrencia se confunde a menudo con la ejecución asíncrona debido al hecho de que, en la práctica, la implementación de la concurrencia a menudo se basa en algún mecanismo para la evaluación asíncrona. Pero, como se ha señalado anteriormente, la asincronía es sólo eso: un detalle de implementación, y que no dice nada sobre la semántica concurrente frente a la secuencial.

Cats Effect dispone de numerosos mecanismos para definir efectos concurrentes. Uno de los más sencillos es parTupled, que evalúa un par de efectos independientes y produce una tupla con sus resultados:

```scala
(callServiceA(params1), callServiceB(params2)).parTupled   // => IO[(Response, Response)]
```

Como con todo el soporte de concurrencia, parTupled es una forma de declarar al tiempo de ejecución subyacente que dos efectos (callServiceA(params1) y callServiceB(params2)) son independientes y pueden ser evaluados en paralelo. Cats Effect nunca asumirá que dos efectos pueden ser evaluados en paralelo.

Toda la concurrencia en Cats Effect se implementa en términos de primitivas subyacentes que crean y manipulan fibras: start y join. Estas primitivas de concurrencia son muy similares a las operaciones de nombre equivalente en Thread, pero como con la mayoría de las cosas en Cats Effect, son considerablemente más rápidas y seguras.

#### Concurrencia Estructurada

Formalmente hablando, la concurrencia estructurada es una forma de flujo de control en la que todas las operaciones concurrentes deben formar una jerarquía cerrada. Conceptualmente, significa que cualquier operación que bifurque algunas acciones para ejecutarse de forma concurrente debe asegurarse forzosamente de que esas acciones se completan antes de seguir adelante. Además, los resultados de una operación concurrente sólo deben estar disponibles una vez completada, y sólo para su padre en la jerarquía. parTupled anterior es un ejemplo sencillo de esto: el IO[(Response, Response)] no está disponible como resultado hasta que ambas llamadas de servicio se han completado, y esas respuestas sólo son accesibles dentro de la tupla resultante.

Cats Effect dispone de un gran número de herramientas de concurrencia estructurada, entre las que destacan parTupled, parMapN y parTraverse. Además, ofrece una serie de operadores de concurrencia estructurada más robustos, como background, Supervisor y Dispatcher. También ha fomentado un ecosistema en el que la concurrencia estructurada es la norma y no la excepción, especialmente con la ayuda de marcos de trabajo de nivel superior como Fs2. Sin embargo, la concurrencia estructurada puede ser muy limitante, y Cats Effect no impide la concurrencia no estructurada cuando es necesaria.

En particular, las fibras pueden iniciarse sin que el invocador se vea obligado a esperar a que se completen. Esta flexibilidad de bajo nivel es necesaria en algunos casos, pero también es algo peligrosa, ya que puede dar lugar a "fugas" de fibras (en las que se inicia una fibra y se abandonan todas las referencias a ella fuera del tiempo de ejecución). En general, es mejor confiar en herramientas estructuradas (pero muy flexibles) como background o Supervisor.

Además, Cats Effect proporciona un par de herramientas generales para modelar el estado concurrente compartido, Ref y Deferred. Estos mecanismos son fundamentalmente no estructurados por naturaleza, y pueden resultar en lógica de negocio innecesariamente difícil de seguir. Sin embargo, como ocurre con muchas herramientas potentes, tienen su momento y su lugar. Estas herramientas pueden ser utilizadas para crear poderosas abstracciones de alto nivel como Queue, Semaphore, y similares.

Todo ello viene a decir que Cats Effect fomenta la concurrencia estructurada y proporciona a los usuarios un gran número de herramientas flexibles para conseguirla, pero no impide las composiciones concurrentes no estructuradas como start o Ref.

### Paralelismo

Al igual que la ejecución asíncrona, el paralelismo es un detalle de implementación del tiempo de ejecución. Cuando dos cosas se evalúan en paralelo, significa que el tiempo de ejecución subyacente y el hardware son libres de programar los cálculos asociados simultáneamente en los procesadores subyacentes. Este concepto está muy relacionado con el de concurrencia en el sentido de que la concurrencia es la forma en que los usuarios de Cats Effect declaran al tiempo de ejecución que las cosas pueden ejecutarse en paralelo.

Generalmente es más fácil entender la distinción entre concurrencia y paralelismo examinando escenarios en los que los efectos concurrentes no se evaluarían en paralelo. Un escenario obvio es cuando la aplicación se ejecuta en JavaScript en lugar de en la JVM. Dado que JavaScript es un lenguaje de un solo hilo, es imposible que algo se evalúe en paralelo, incluso cuando se define como concurrente. Ahora bien, esto no significa que la concurrencia sea inútil en JavaScript, ya que sigue siendo útil para que el tiempo de ejecución entienda que no necesita esperar a que A termine antes de ejecutar B, pero sí significa que todo, en el hardware subyacente, se evaluará secuencialmente.

Un ejemplo más complejo de evaluación no paralela de efectos concurrentes puede ocurrir cuando el número de fibras excede el número de hilos subyacentes dentro del tiempo de ejecución. En general, el tiempo de ejecución de Cats Effect intenta mantener el número de hilos subyacentes ajustado al número de hilos físicos proporcionados por el hardware, mientras que el número de fibras puede crecer hasta las decenas de millones (o incluso más en sistemas con una gran cantidad de memoria disponible). Dado que sólo hay un pequeño número de hilos portadores reales, el tiempo de ejecución programará algunas de las fibras concurrentes en el mismo hilo portador subyacente, lo que significa que esas fibras se ejecutarán en serie en lugar de en paralelo.

Como otro detalle de implementación, vale la pena señalar que se impide que las fibras "acaparen" su hilo portador, incluso cuando el tiempo de ejecución subyacente sólo tiene un único hilo de ejecución (como JavaScript). Cada vez que una fibra secuencia un efecto asíncrono, cede su hilo a la siguiente fibra de la cola. Además, si una fibra ha tenido una larga serie de efectos secuenciales sin ceder, el tiempo de ejecución detectará la situación e insertará un rendimiento artificial para asegurar que otras fibras pendientes tengan la oportunidad de progresar. Este es un elemento importante de equidad.

### Effects

Un efecto es una descripción de una acción (o acciones) que se llevará a cabo cuando ocurra la evaluación. Un tipo de efecto muy común es IO:

```scala
val printer: IO[Unit] = IO.println("Hello, World")
val printAndRead: IO[String] = IO.print("Enter your name: ") >> IO.readLine

def foo(str: String): IO[String] = ???
```

En el fragmento anterior, tanto printer como printAndRead son efectos: describen una acción (o, en el caso de printAndRead, acciones en plural) que se llevará a cabo cuando se evalúen. foo es un ejemplo de función que devuelve un efecto. Para abreviar, este tipo de funciones suelen denominarse "con efecto": foo es una función con efecto.

Esto es muy distinto de decir que foo es una función que realiza efectos, del mismo modo que el efecto impresora es muy distinto de imprimir realmente. Esto se ilustra claramente si escribimos algo como lo siguiente:

```scala
printer
printer
printer
```

Cuando se evalúa este código, el texto "Hola, Mundo" se imprimirá exactamente cero veces, ya que printer es sólo un valor descriptivo; no hace nada por sí mismo. Cats Effect hace posible expresar efectos como valores.

Esto es algo que Future no puede hacer:

```scala
val printer: Future[Unit] = Future(println("Hello, World"))

printer
printer
printer
```

Esto imprimirá "Hola, Mundo" exactamente una vez, lo que significa que printer no representa una descripción de una acción, sino más bien los resultados de esa acción (sobre la que ya se ha actuado fuera de nuestro control). Críticamente, cambiar val por def en lo anterior resultaría en imprimir tres veces, lo cual es una fuente de bugs y comportamientos inesperados más a menudo de lo que podrías esperar cuando trabajas con Future.

En el uso avanzado de Cats Effect, también es común utilizar tipos de efectos que no son simplemente IO:

```scala
import cats.Monad
import cats.effect.std.Console
import cats.syntax.all._

def example[F[_]: Monad: Console](str: String): F[String] = {
  val printer: F[Unit] = Console[F].println(str)
  (printer >> printer).as(str)
}
```

En lo anterior, ejemplo es una función con efecto, e impresora es un efecto (como lo son impresora >> impresora y (impresora >> impresora).as(str)). El tipo de efecto aquí es F, que podría ser IO, ¡pero también podría ser algo más interesante! El invocador de ejemplo es libre de elegir el efecto en el lugar de la llamada, por ejemplo escribiendo algo como ejemplo[IO]("Hola, Mundo"), que a su vez devolvería un IO[String]. Gran parte de la composibilidad del ecosistema Cats Effect se consigue utilizando esta técnica bajo la superficie

### Side-Effects

Cuando la ejecución de un fragmento de código provoca cambios aparte de devolver un valor, solemos decir que el código "tiene efectos secundarios". Más intuitivamente, el código en el que te importa si se ejecuta más de una vez, y/o cuándo se ejecuta, casi siempre tiene efectos secundarios. El ejemplo clásico de esto es System.out.println:

```scala
def double(x: Int): Int = {
  System.out.println("Hello, World")
  x + x
}
```

La función double toma un Int y devuelve ese mismo Int sumado a sí mismo... e imprime "Hello, World" en la salida estándar. Esto es lo que se entiende por "side" en "side-effect": algo más se está haciendo "on the side". Lo mismo podría decirse de un registro, cambiar el valor de una var, hacer una llamada de red, etc etc.

Desde un punto de vista crítico, un efecto secundario no es lo mismo que un efecto. Un efecto es una descripción de alguna acción, donde la acción puede realizar efectos secundarios cuando se ejecuta. El hecho de que los efectos sean sólo descripciones de acciones es lo que los hace mucho más seguros y controlables. Cuando un trozo de código contiene un efecto secundario, esa acción simplemente ocurre. No puedes hacer que se evalúe en paralelo, o en un grupo de hilos diferente, o en un horario, o hacer que se reintente si falla. Dado que un efecto es sólo una descripción de las acciones a tomar, puede cambiar libremente la semántica de cómo se ejecuta finalmente para satisfacer las necesidades de su caso de uso específico.

En Cats Effect, el código que contiene efectos secundarios siempre debe envolverse en uno de los constructores "especiales". En particular:

- Sincronico (Return S o Throw S)
  - IO(...) o IO.delay(...)
  - IO.blocking(...)
  - IO.interruptible(...)
  - IO.interruptibleMany(...)
- Asincronico (invoca una devolución de llamada)
  - IO.async(...) o IO.async_[...]

Cuando el código con efectos secundarios se envuelve en uno de estos constructores, el código en sí todavía contiene efectos secundarios, pero fuera del ámbito léxico del constructor podemos razonar sobre todo el asunto (por ejemplo, incluyendo el IO(...)) como un efecto, en lugar de como un efecto secundario.

Por ejemplo, podemos envolver el código de efecto secundario System.out.println de antes para convertirlo en un valor de efecto:

```scala
val wrapped: IO[Unit] = IO(System.out.println("Hello, World"))
```

Siendo estricto con esta regla general y siempre envolviendo tu lógica de efectos secundarios en constructores de efectos desbloquea todo el poder y composibilidad de la programación funcional. Esto también hace posible que Cats Effect haga un trabajo más eficaz de programación y optimización de tu aplicación, ya que puede hacer suposiciones más agresivas sobre cuándo evaluar piezas de código de manera que utilicen mejor los recursos de CPU y caché.

<!-- La jerarquía de clases dentro de Cats Effect define lo que significa, fundamentalmente, ser un efecto funcional. Esto es muy similar a cómo tenemos un conjunto de reglas que nos dicen lo que significa ser un número, o lo que significa tener un método flatMap. A grandes rasgos, las reglas de los efectos funcionales pueden dividirse en las siguientes categorías:

- Seguridad y anulación de recursos
- Evaluación paralela
- Compartición de estados entre procesos paralelos
- Interacciones con el tiempo, incluido el tiempo actual y la suspensión
- Captura segura de efectos secundarios que devuelven valores
- Captura segura de efectos secundarios que invocan una devolución de llamada -->
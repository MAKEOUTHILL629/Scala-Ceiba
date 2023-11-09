# Spray / Akka HTTP

## Qué es spray?

spray es un conjunto de librerías Scala ligeras que proporcionan soporte REST/HTTP del lado del cliente y del servidor sobre Akka.

Creemos que, después de haber elegido Scala (y posiblemente Akka) como herramientas primarias para la construcción de software, usted querrá confiar en su poder no sólo en su capa de aplicación, sino en toda la pila de red completa (a nivel de JVM). spray proporciona precisamente eso: un conjunto de componentes integrados para todas sus necesidades REST/HTTP que le permiten trabajar con APIs idiomáticas de Scala (y Akka) en el nivel de pila de su elección, todo implementado sin ningún tipo de capas envolventes alrededor de bibliotecas Java "heredadas".

### Principios

El desarrollo de los sprays se rige por los siguientes principios:

- **Totalmente asíncrono, no bloqueante**: Todas las API son totalmente asíncronas y se evita el bloqueo de código siempre que sea posible.
- **Actor y futuro**: spray adopta plenamente el modelo de programación de la plataforma sobre la que está construido. Akka Actors y Futures son elementos clave de sus API.
- **Alto rendimiento**: Especialmente los componentes de bajo nivel de los sprays están cuidadosamente elaborados para ofrecer un excelente rendimiento en entornos de alta carga.
- **Ligero**: Todas las dependencias se gestionan con sumo cuidado y el código base de los sprays se mantiene lo más reducido posible.
- **Modular**: Al estar estructurada en un conjunto de componentes integrados pero poco acoplados, su aplicación sólo necesita depender de las partes que realmente se utilizan.
- **Testable**: Todos los componentes de la pulverización están estructurados de tal forma que permiten realizar pruebas de forma fácil y cómoda.

### Modules

Actualmente, el paquete de pulverización consta de estos módulos:

- **spray-caching**: Un módulo de caché de alto rendimiento basado en Akka, Caché en memoria rápida y ligera basada en concurrentlinkedhashmap y Akka Futures.
- **spray-can**: Servidor y cliente HTTP de bajo nivel, bajo coste y alto rendimiento basado en spray-io.
- **spray-client**: Proporciona soporte HTTP del lado del cliente a un nivel superior que las API de cliente HTTP de bajo nivel, en las que se basa.
- **spray-http**:  Un modelo inmutable de peticiones HTTP, respuestas y cabeceras comunes. Este módulo es completamente independiente, no depende de Akka ni de ninguna otra parte de spray.
- **spray-httpx**: Herramientas de alto nivel para trabajar con mensajes HTTP (principalmente marshalling, unmarshalling y (de)compresión) que son utilizadas tanto por spray-client como por spray-routing.
- **spray-io**: Una capa de IO de red de bajo nivel para conectar directamente actores Akka a sockets Java NIO asíncronos. Nos gusta pensar en ella como una versión básica de Netty para Scala. A partir de 1.0-M8/1.1-M8 contiene un backport de la nueva capa Akka IO que viene con Akka 2.2. En 1.2-M8 simplemente contiene algunos "restos" específicos de Scala que probablemente desaparecerán por completo en el futuro.
- **spray-servlet**: Una capa adaptadora que proporciona (un subconjunto de) la interfaz de servidor HTTP spray-can sobre la API Servlet. Permite el uso de spray-routing en un contenedor servlet.
- **spray-routing**: Un DSL de enrutamiento de alto nivel para definir con elegancia servicios web RESTful.
- **spray-testkit**: Un DSL para probar fácilmente servicios de enrutamiento spray. Soporta tanto ScalaTest como Specs2.
- **spray-util**: Pequeño módulo utilitario utilizado por todos los demás módulos excepto spray-http.
- **spray-json**: Una implementación JSON ligera, limpia y sencilla en Scala. Debido a que no depende de ninguna otra parte de spray ni de Akka y es sólo una dependencia opcional de spray-client y spray-httpx no vive en el repositorio principal de spray, sino más bien en su propio repositorio github Tenga en cuenta que puede utilizar fácilmente spray con cualquier biblioteca JSON que más le guste, spray-json es sólo una de varias alternativas.

#### Filosofia

Desde su creación a principios de 2011, el desarrollo de sprays se ha centrado claramente en proporcionar herramientas para crear capas de integración en lugar de núcleos de aplicaciones. Como tal, se considera más un conjunto de bibliotecas que un framework.

Un marco, como nos gustaría pensar en el término, le da un "marco", en el que construir su aplicación. Viene con muchas decisiones ya tomadas y proporciona una base que incluye estructuras de soporte que te permiten empezar y obtener resultados rápidamente. En cierto modo, un framework es como un esqueleto sobre el que se pone la "carne" de la aplicación para que cobre vida. Como tales, los marcos de trabajo funcionan mejor si los eliges antes de empezar a desarrollar la aplicación e intentas ceñirte a su "forma de hacer las cosas" a medida que avanzas.

Por ejemplo, si estás construyendo una aplicación web orientada al navegador, tiene sentido elegir un framework web y construir tu aplicación sobre él, porque el "núcleo" de la aplicación es la interacción de un navegador con tu código en el servidor web. Los creadores del framework han elegido una forma "probada" de diseñar este tipo de aplicaciones y te permiten "rellenar los espacios en blanco" de una "plantilla de aplicación" más o menos flexible. Poder confiar en una arquitectura de buenas prácticas como ésta puede ser una gran ventaja para hacer las cosas con rapidez.

Sin embargo, si tu aplicación no es principalmente una aplicación web porque su núcleo no es la interacción con el navegador, sino algún servicio de negocio especializado o complejo, y simplemente estás tratando de conectarlo al mundo a través de una interfaz REST/HTTP, un marco web podría no ser lo que necesitas. En este caso, la arquitectura de la aplicación debe ser dictada por lo que tiene sentido para el núcleo, no la capa de interfaz. Además, es probable que no se beneficie de los componentes del marco de trabajo específicos del navegador, como plantillas de vista, gestión de activos, generación/manipulación/minificación de JavaScript y CSS, soporte de localización, soporte de AJAX, etc.

spray fue diseñado específicamente como "no-un-framework", no porque no nos gusten los frameworks, sino para casos de uso en los que un framework no es la elección correcta. spray está hecho para construir capas de integración basadas en HTTP y como tal intenta "mantenerse al margen". Por lo tanto, normalmente no construyes tu aplicación "sobre" spray, sino que construyes tu aplicación sobre lo que tenga sentido y utilizas spray simplemente para las necesidades de integración HTTP.

## Primeros pasos

Para ayudarte a ponerte en marcha hemos creado el proyecto spray-template en GitHub. Esto proporciona todo lo necesario para obtener una aplicación de servidor HTTP spray en menos de 5 minutos y ver el resultado en su navegador.

Prueba a ejecutar uno de estos ejemplos:

- [spray as a standalone service](https://github.com/spray/spray-template/tree/on_spray-can_1.3) utiliza los módulos de spray-routing y spray-can
- [spray inside a servlet container](https://github.com/spray/spray-template/tree/on_jetty_1.3) utiliza spray-routing y spray-servlet dentro de un servidor Jetty

Nota: cada ejemplo vive en su propia rama Git del proyecto spray-template.

## Big Picture

Tanto el servidor HTTP spray-can como el servlet conector spray-servlet proporcionan una interfaz a nivel de actor que permite a su aplicación responder a peticiones HTTP entrantes simplemente respondiendo con un HttpResponse:

```scala
import spray.http._
import HttpMethods._

class MyHttpService extends Actor {
  def receive = {
    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
      sender ! HttpResponse(entity = "PONG")
  }
}
```

Aunque sería perfectamente posible definir un servicio completo de API REST simplemente pattern-matching con la solicitud Http entrante (tal vez con la ayuda de algunos extractores del tipo de Unfiltered), este enfoque se vuelve un poco difícil de manejar para servicios más grandes debido a la cantidad de "ceremonias" sintácticas necesarias. Además, no ayuda a mantener la definición del servicio tan DRY como te gustaría.

Como alternativa, spray-routing proporciona un DSL flexible para expresar el comportamiento de tu servicio como una estructura de elementos componibles (llamados Directivas) de forma concisa y legible. En el nivel superior, como resultado de la envoltura runRoute, la "estructura de ruta" produce una función parcial `Actor.Receive` que puede suministrarse directamente a su actor de servicio. La definición de servicio de arriba, por ejemplo, escrita utilizando el DSL de enrutamiento, tendría este aspecto:

```scala
import spray.routing._

class MyHttpService extends HttpServiceActor {
  def receive = runRoute {
    path("ping") {
      get {
        complete("PONG")
      }
    }
  }
}
```

Este ejemplo tan breve no es, desde luego, el mejor para ilustrar el ahorro en "ceremonias" y las mejoras en concisión y legibilidad que promete el enrutamiento por pulverización. El ejemplo más largo podría hacer un mejor trabajo en este sentido.

Para aprender a trabajar con el DSL de spray-routing deberías entender primero el concepto de Rutas.

### The HttpService

spray-routing hace que todas las partes relevantes del DSL de enrutamiento estén disponibles a través del rasgo HttpService, que puedes mezclar en tu actor de servicio o prueba de ruta. El rasgo HttpService define un único miembro abstracto:

```scala
def actorRefFactory: ActorRefFactory
```

que conecta el DSL de enrutamiento a tu jerarquía de actores. Para tener acceso a todos los miembros de HttpService en tu actor de servicio puedes mezclar el rasgo HttpService y añadir esta línea a tu clase de actor:

```scala
def actorRefFactory = context
```

o, alternativamente, derivar su actor de servicio de la clase HttpServiceActor, que ya define la conexión def actorRefFactory = contexto para usted.

### The runRoute Wrapper

Aparte de todas las directivas predefinidas, el HttpService proporciona una cosa importante, la envoltura runRoute. Este método conecta tu estructura de ruta con el actor que la encierra construyendo una función parcial Actor.Receive que puedes utilizar directamente como función de "comportamiento" de tu actor:

```scala
import spray.routing._

class MyHttpService extends HttpServiceActor {
  def receive = runRoute {
    path("ping") {
      get {
        complete("PONG")
      }
    }
  }
}
```

### Routes

"Rutas" son un concepto central en spray-routing ya que todas las estructuras que construyes con el DSL de enrutamiento son subtipos de tipo Ruta. En spray-routing una ruta se define así:

```scala
type Route = RequestContext => Unit
```

Es un simple alias para una función que toma un `RequestContext` como parámetro.

Contrariamente a lo que cabría esperar en un principio, una ruta no devuelve nada. Más bien, todo el procesamiento de la respuesta (es decir, todo lo que hay que hacer después de que la propia ruta haya gestionado una solicitud) se realiza en "estilo de continuación" a través del `responder` del `RequestContext`. Si no sabes lo que esto significa, no te preocupes. Pronto quedará claro. El punto clave es que este diseño tiene la ventaja de ser completamente no-bloqueante así como amigable con el actor ya que, de esta manera, es posible simplemente enviar un RequestContext a otro actor de una manera "dispara y olvida", sin tener que preocuparse por el manejo de los resultados.

Generalmente cuando una ruta recibe una petición (o más bien un `RequestContext` para ella) puede hacer una de tres cosas:

- Completar la solicitud llamando a requestContext.complete(...)
- Rechazar la solicitud llamando a requestContext.reject(...)
- Ignorar la solicitud (es decir, no completarla ni rechazarla)

El primer caso es bastante claro, al llamar complete se envía una respuesta determinada al cliente como reacción a la petición. En el segundo caso "reject" significa que la ruta no quiere gestionar la petición. Verás más adelante en la sección sobre composición de rutas para qué sirve esto. El tercer caso suele ser un error. Si una ruta no hace nada con la petición simplemente no se actuará sobre ella. Esto significa que el cliente no recibirá una respuesta hasta que la petición se agote, momento en el que se generará una respuesta 500 Internal Server Error. Por lo tanto, sus rutas normalmente deberían terminar completando o rechazando la petición.

### Constructing Routes

Dado que las rutas son funciones ordinarias RequestContext => Unit, la ruta más sencilla es:

```scala
ctx => ctx.complete("Response")
```

o menos:

```scala
_.complete("Response")
```

o incluso más corta (utilizando la directiva completa):

```scala
complete("Response")
```

Todas estas son formas diferentes de definir lo mismo, es decir, una Ruta que simplemente completa todas las peticiones con una respuesta estática.

Aunque podrías escribir toda la lógica de tu aplicación como una función monolítica que inspecciona el RequestContext y lo completa dependiendo de sus propiedades, este tipo de diseño sería difícil de leer, mantener y reutilizar. Por lo tanto spray-routing te permite construir rutas más complejas a partir de otras más simples a través de la composición.

### Composing Routes

Hay tres operaciones básicas que necesitamos para construir rutas más complejas a partir de otras más sencillas:

- Transformación de rutas, que delega el procesamiento a otra ruta "interna", pero en el proceso cambia algunas propiedades de la solicitud entrante, de la respuesta saliente o de ambas.
- Filtrado de rutas, que sólo deja pasar las solicitudes que satisfacen una determinada condición de filtrado y rechaza todas las demás.
- Encadenamiento de rutas, que intenta una segunda ruta si la primera ha sido rechazada.

El último punto se consigue con el sencillo operador ~, que está disponible para todas las rutas a través de un "chulo", es decir, una extensión implícita. Los dos primeros puntos son proporcionados por las llamadas Directivas, de las cuales un gran número ya están predefinidas por spray-routing y que también puede crear usted mismo fácilmente. Las directivas proporcionan la mayor parte de la potencia y flexibilidad de spray-routings.

### The Routing Tree

Esencialmente, cuando se combinan directivas y rutas personalizadas mediante anidamiento y el operador ~, se construye una estructura de enrutamiento que forma un árbol. Cuando llega una petición, se inyecta en este árbol en la raíz y fluye hacia abajo a través de todas las ramas de una manera profundidad-primero hasta que algún nodo la completa o es totalmente rechazada.

Considere este ejemplo esquemático:

```scala
val route =
  a {
    b {
      c {
        ... // route 1
      } ~
      d {
        ... // route 2
      } ~
      ... // route 3
    } ~
    e {
      ... // route 4
    }
  }
```

Aquí cinco directivas forman un árbol de enrutamiento.

- La ruta 1 sólo se ejecutará si las directivas a, b y c dejan pasar la petición.
- La ruta 2 se ejecutará si a y b pasan, c rechaza y d pasa.
- La ruta 3 se ejecutará si a y b pasan, pero c y d rechazan.

Por tanto, la ruta 3 puede considerarse como una ruta "comodín" que sólo entra en acción si las rutas encadenadas en las posiciones precedentes la rechazan. Este mecanismo puede facilitar la aplicación de una lógica de filtrado compleja: basta con colocar los casos más específicos al principio y los más generales al final.

### Directives

Las "directivas" son pequeños bloques con los que se pueden construir estructuras de rutas arbitrariamente complejas. He aquí un ejemplo sencillo de una ruta construida a partir de directivas:

```scala
import spray.routing._
import Directives._

val route: Route =
  path("order" / IntNumber) { id =>
    get {
      complete {
        "Received GET request for order " + id
      }
    } ~
    put {
      complete {
        "Received PUT request for order " + id
      }
    }
  }
```

La anatomía general de una directiva es la siguiente:

```scala
name(arguments) { extractions =>
  ... // inner Route
}
```

Tiene un nombre, cero o más argumentos y, opcionalmente, una ruta interna. Además, las directivas pueden "extraer" una serie de valores y ponerlos a disposición de sus rutas internas como argumentos de función. Vista "desde fuera", una directiva y su ruta interna forman una expresión de tipo Ruta (para más detalles, véase el capítulo Rutas).

## Para qué sirven las Directivas

Una directiva realiza una o varias de las siguientes acciones:

- Transformar el RequestContext entrante antes de pasarlo a su ruta interna.
- Filtrar el RequestContext de acuerdo con cierta lógica, es decir, pasar sólo ciertas peticiones y rechazar todas las demás.
- Extraer valores del RequestContext y ponerlos a disposición de su ruta interna como "extracciones".
- Completar la solicitud

El primer punto merece algo más de discusión. Un `RequestContext` es el objeto central que se transmite a través de una estructura de rutas y, potencialmente, entre actores. Es inmutable pero ligero y, por tanto, puede copiarse rápidamente. Cuando una directiva recibe una instancia de `RequestContext` desde el exterior puede decidir pasar esta instancia sin cambios a su ruta interna o puede crear una copia de la instancia de `RequestContext`, con uno o más cambios, y pasar esta copia a su ruta interna. Típicamente esto es bueno para dos cosas:

- Transformación de la instancia HttpRequest
- "Hooking in" otra función de transformación de respuesta en la cadena de respuesta.

### The Responder Chain

Para entender la "cadena de respuesta" es útil observar lo que ocurre cuando se llama al método `complete` de una instancia `RequestContext` en la ruta más interna de una estructura de rutas.

Considere la siguiente estructura de ruta hipotética de tres directivas anidadas alrededor de una ruta simple:

```scala
foo {
  bar {
    baz {
      ctx => ctx.complete("Hello")
    }
  }
}
```

Supongamos que foo y baz "hook in" la lógica de transformación de la respuesta mientras que bar deja inalterado el `responder` del `RequestContext` que recibe antes de pasarlo a su Route interna. Esto es lo que ocurre cuando se llama a `complete("Hola")`:

1. El método `complete` crea un `HttpResponse` y lo envía al respondedor del `RequestContext`.
2. La lógica de transformación de la respuesta proporcionada por la directiva baz se ejecuta y envía su resultado al respondedor del `RequestContext` que recibió la directiva baz.
3. La lógica de transformación de respuesta proporcionada por la directiva foo se ejecuta y envía su resultado al respondedor del `RequestContext` que recibió la directiva foo.
4. El respondedor del `RequestContext` original, que es el `ActorRef` remitente de la `HttpRequest`, recibe la respuesta y la envía al cliente.

Como puede ver, toda la lógica de gestión de respuestas forma una cadena lógica a la que las directivas pueden "engancharse".

### Composing Directives

Como ha visto en los ejemplos presentados hasta ahora, la forma "normal" de componer directivas es el anidamiento. Veamos de nuevo el ejemplo anterior:

```scala
val route: Route =
  path("order" / IntNumber) { id =>
    get {
      complete {
        "Received GET request for order " + id
      }
    } ~
    put {
      complete {
        "Received PUT request for order " + id
      }
    }
  }
```

Aquí las directivas `get` y `put` se encadenan con el operador ~ para formar una ruta de nivel superior que sirve como ruta interna de la directiva path. Para hacer esta estructura más explícita también se podría escribir todo de esta manera:

```scala
def innerRoute(id: Int): Route =
  get {
    complete {
      "Received GET request for order " + id
    }
  } ~
  put {
    complete {
      "Received PUT request for order " + id
    }
  }

val route: Route = path("order" / IntNumber) { id => innerRoute(id) }
```

Lo que no se ve en este fragmento es que las directivas no se implementan como simples métodos, sino como objetos independientes de tipo Directiva. Esto le da más flexibilidad a la hora de componer directivas. Por ejemplo, también puede utilizar el operador `|` en las directivas. He aquí otra forma de escribir el ejemplo:

```scala
val route =
  path("order" / IntNumber) { id =>
    (get | put) { ctx =>
      ctx.complete("Received " + ctx.request.method + " request for order " + id)
    }
  }
```

Si tienes una estructura de rutas más grande en la que el fragmento `(get | put)` aparece varias veces también podrías factorizarlo así:

```scala
val getOrPut = get | put
val route =
  path("order" / IntNumber) { id =>
    getOrPut { ctx =>
      ctx.complete("Received " + ctx.request.method + " request for order " + id)
    }
  }
```

Como alternativa a la anidación, también puede utilizar el operador &:

```scala
val getOrPut = get | put
val route =
  (path("order" / IntNumber) & getOrPut) { id => ctx =>
    ctx.complete("Received " + ctx.request.method + " request for order " + id)
  }
```

Y una vez más, puedes factorizar las cosas si quieres:

```scala
val orderGetOrPut = path("order" / IntNumber) & (get | put)
val route =
  orderGetOrPut { id => ctx =>
    ctx.complete("Received " + ctx.request.method + " request for order " + id)
  }
```

Este tipo de combinación de directivas con los operadores `|` y `&`, así como el "guardado" de configuraciones de directivas más complejas como un `val` funciona de forma generalizada, con todas las directivas tomando rutas internas.

Queda una cosa más "fea" en nuestro fragmento: tenemos que volver a la definición de ruta de nivel más bajo, manipulando directamente el `RequestContext`, para llegar al método de petición. Sería mejor si pudiéramos "extraer" de algún modo el nombre del método en una directiva especial, de modo que pudiéramos expresar nuestra ruta más interna con un simple `complete`. Resulta que esto es fácil con la directiva `extract`:

```scala
val orderGetOrPut = path("order" / IntNumber) & (get | put)
val requestMethod = extract(_.request.method)
val route =
  orderGetOrPut { id =>
    requestMethod { m =>
      complete("Received " + m + " request for order " + id)
    }
  }
```

O de otra manera:

```scala
val orderGetOrPut = path("order" / IntNumber) & (get | put)
val requestMethod = extract(_.request.method)
val route =
  (orderGetOrPut & requestMethod) { (id, m) =>
    complete("Received " + m + " request for order " + id)
  }
```

Llevando al extremo la "factorización" de las configuraciones directivas, llegamos a esto:

```scala
val orderGetOrPutMethod =
  path("order" / IntNumber) & (get | put) & extract(_.request.method)
val route =
  orderGetOrPutMethod { (id, m) =>
    complete("Received " + m + " request for order " + id)
  }
```

Tenga en cuenta que ir tan lejos con la "compresión" de varias directivas en una sola probablemente no resulte en el código de enrutamiento más legible y por lo tanto mantenible. Puede que incluso el primero de esta serie de ejemplos sea el más legible.

Aún así, el propósito del ejercicio presentado aquí es mostrarte lo flexibles que pueden ser las directivas y cómo puedes usar su poder para definir el comportamiento de tu servicio web al nivel de abstracción que sea adecuado para tu aplicación.

### Type Safety

Cuando se combinan directivas con los operadores `|` y `&`, el enrutamiento spray garantiza que todas las extracciones funcionen como se espera y que se apliquen las restricciones lógicas en tiempo de compilación.

Por ejemplo, no se puede combinar una directiva que produzca una extracción con otra que no la produzca:

```scala
val route = path("order" / IntNumber) | get // doesn't compile
```

También tienen que coincidir el número de extracciones y sus tipos:

```scala
val route = path("order" / IntNumber) | path("order" / DoubleNumber)   // doesn't compile
val route = path("order" / IntNumber) | parameter('order.as[Int])      // ok
```

Si combina directivas que producen extracciones con el operador `&`, todas las extracciones se recogerán correctamente:

```scala
val order = path("order" / IntNumber) & parameters('oem, 'expired ?)
val route =
  order { (orderId, oem, expired) =>
    ...
  }
```

Las directivas ofrecen una excelente manera de construir la lógica de su servicio web a partir de pequeños bloques de construcción de una manera plug and play, manteniendo la DRYness y la seguridad de tipos completa. Si la amplia gama de directivas predefinidas (alfabéticamente) no satisface plenamente sus necesidades, también puede crear fácilmente directivas personalizadas.

### Rejections

En el capítulo sobre construcción de Rutas se introdujo el operador `~`, que conecta dos rutas de forma que permite a una segunda ruta intentar una petición si la primera ruta la "rechazó". El concepto de "rechazos" es utilizado por spray-routing para mantener una arquitectura general más funcional y para poder manejar adecuadamente todo tipo de escenarios de error.

Cuando una directiva de filtrado, como la directiva `get`, no puede dejar pasar la petición a su ruta interna porque no se cumple la condición de filtrado (por ejemplo, porque la petición entrante no es una petición GET), la directiva no completa inmediatamente la petición con una respuesta de error. Esto haría imposible que otras rutas encadenadas después del filtro que falla tuvieran la oportunidad de gestionar la petición. En su lugar, los filtros que fallan "rechazan" la petición de la misma forma que si se llamara explícitamente a `requestContext.reject(...).`

Después de haber sido rechazada por una ruta, la solicitud continuará fluyendo a través de la estructura de enrutamiento y posiblemente encontrará otra ruta que pueda completarla. Si hay más rechazos, todos ellos serán recogidos.

Si la solicitud no puede ser completada por (una rama de) la estructura de ruta, se puede utilizar una directiva handleRejections adjunta para convertir un conjunto de rechazos en una HttpResponse (que, en la mayoría de los casos, será una respuesta de error). La envoltura runRoute definida por el rasgo The HttpService envuelve internamente su argumento route con la directiva handleRejections para "atrapar" y manejar cualquier rechazo.

### Predefined Rejections

Un rechazo encapsula una razón específica por la que una ruta no ha podido gestionar una petición. Se modela como un objeto de tipo `Rejection`. spray-routing viene con un conjunto de rechazos predefinidos, que son utilizados por varias directivas predefinidas.

Los rechazos se acumulan a lo largo de la evaluación de una Ruta y finalmente se convierten en respuestas `HttpResponse` mediante la directiva handleRejections si no hubo forma de completar la petición.

### RejectionHandler

La directiva handleRejections delega el trabajo real de convertir una lista de rechazos a su argumento, un RejectionHandler, que se define así:

```scala
trait RejectionHandler extends PartialFunction[List[Rejection], Route]
```

Dado que un `RejectionHandler` es una función parcial, puede elegir qué rechazos quiere gestionar y cuáles no. Los rechazos no gestionados simplemente continuarán fluyendo a través de la estructura de la ruta. El `RejectionHandler` superior aplicado por The runRoute Wrapper gestionará todos los rechazos que le lleguen.

Por lo tanto, si desea personalizar la forma en que se gestionan determinados rechazos, simplemente introduzca un `RejectionHandler` personalizado en el ámbito implícito de The runRoute Wrapper o páselo a una directiva handleRejections explícita que haya colocado en algún lugar de la estructura de su ruta.

He aquí un ejemplo:

```scala
import spray.routing._
import spray.http._
import StatusCodes._
import Directives._

implicit val myRejectionHandler = RejectionHandler {
  case MissingCookieRejection(cookieName) :: _ =>
    complete(BadRequest, "No cookies, no service!!!")
}

class MyService extends HttpServiceActor {
  def receive = runRoute {
    `<my-route-definition>`
  }
}
```

### Rejection Cancellation

Como puede ver en la definición anterior, el `RejectionHandler` no gestiona rechazos individuales, sino una lista completa de ellos. Esto se debe a que algunas estructuras de ruta producen varias "razones" por las que una solicitud no puede ser gestionada.

Tome esta estructura de ruta como ejemplo:

```scala
import spray.httpx.encoding._

val route =
  path("order") {
    get {
      complete("Received GET")
    } ~
    post {
      decodeRequest(Gzip) {
        complete("Received POST")
      }
    }
  }
```

Para solicitudes POST sin comprimir, esta estructura de ruta podría producir dos rechazos:

- un `MethodRejection` producido por la directiva get (que rechaza porque la petición no es una petición GET)
- un `UnsupportedRequestEncodingRejection` producido por la directiva decodeRequest (que sólo acepta peticiones comprimidas con gzip)

En realidad, la ruta genera incluso un rechazo más, un `TransformationRejection` producido por la directiva post. Este "cancela" todos los demás MethodRejections potencialmente existentes, ya que son inválidos después de que la directiva post permitiera pasar la petición (después de todo, la estructura de la ruta puede tratar peticiones POST). Este tipo de cancelaciones de rechazo se resuelven antes de que un `RejectionHandler` vea la lista de rechazos. Así, en el ejemplo anterior, el `RejectionHandler` se encontrará con una lista de rechazos de un solo elemento, que no contiene nada más que el `UnsupportedRequestEncodingRejection`.

### Empty Rejections

Dado que los rechazos se transmiten en listas, cabe preguntarse cuál es la semántica de una lista de rechazos vacía. De hecho, las listas de rechazo vacías tienen una semántica bien definida. Señalan que una solicitud no se ha gestionado porque no se ha podido encontrar el recurso correspondiente. spray-routing reserva el estado especial de "rechazo vacío" a este fallo, el más común que puede producir un servicio.

Así, por ejemplo, si la directiva path rechaza una petición, lo hace con una lista de rechazos vacía. La directiva host se comporta de la misma manera.

## Exception Handling

Las excepciones lanzadas durante la ejecución de la ruta burbujean a través de la estructura de la ruta hasta la siguiente directiva handleExceptions adjunta, la envoltura runRoute o la llamada de retorno `onFailure` de un futuro creado por `detach`.

De forma similar a la forma en que se gestionan los rechazos, la directiva handleExceptions delega el trabajo real de convertir una lista de rechazos a su argumento, un ExceptionHandler, que se define así:

```scala
trait ExceptionHandler extends PartialFunction[Throwable, Route]
```

El Wrapper runRoute definido en The HttpService hace lo mismo pero obtiene su instancia `ExceptionHandler` implícitamente.

Dado que un `ExceptionHandler` es una función parcial, puede elegir qué excepciones quiere manejar y cuáles no. Las excepciones no manejadas simplemente continuarán burbujeando en la estructura de la ruta. El `ExceptionHandler` superior aplicado por The runRoute Wrapper manejará todas las excepciones que le lleguen.

Por lo tanto, si desea personalizar la forma en que se manejan ciertas excepciones, simplemente introduzca un `ExceptionHandler` personalizado en el ámbito implícito de The runRoute Wrapper o páselo a una directiva handleExceptions explícita que haya colocado en algún lugar de la estructura de su ruta.

He aquí un ejemplo:

```scala
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.routing._

implicit def myExceptionHandler(implicit log: LoggingContext) =
  ExceptionHandler {
    case e: ArithmeticException =>
      requestUri { uri =>
        log.warning("Request to {} could not be handled normally", uri)
        complete(InternalServerError, "Bad numbers, bad result!!!")
      }
  }

class MyService extends HttpServiceActor {
  def receive = runRoute {
    `<my-route-definition>`
  }
}
```

## Case Class Extraction

La extracción de valores realizada por las directivas es una buena forma de proporcionar a tu lógica de ruta propiedades de petición interesantes, todo ello con la seguridad de tipos y el tratamiento de errores adecuados. Sin embargo, en algunos casos puedes querer incluso más. Considere este ejemplo:

```scala
case class Color(red: Int, green: Int, blue: Int)

val route =
  path("color") {
    parameters('red.as[Int], 'green.as[Int], 'blue.as[Int]) { (red, green, blue) =>
      val color = Color(red, green, blue)
      doSomethingWith(color) // route working with the Color instance
    }
  }
```

Aquí se emplea una directiva parameters para extraer tres valores `Int`, que luego se utilizan para construir una instancia de la clase `Color` case. Hasta aquí todo correcto. Sin embargo, si las clases modelo con las que queremos trabajar tienen más de unos pocos parámetros, la sobrecarga introducida por la captura de los argumentos como extracciones sólo para introducirlos en el constructor de la clase modelo directamente después puede desordenar un poco las definiciones de ruta.

Si tus clases modelo son clases case, como en nuestro ejemplo, spray-routing soporta una sintaxis aún más corta y concisa. También puedes escribir el ejemplo anterior así:

```scala
case class Color(red: Int, green: Int, blue: Int)

val route =
  path("color") {
    parameters('red.as[Int], 'green.as[Int], 'blue.as[Int]).as(Color) { color =>
      doSomethingWith(color) // route working with the Color instance
    }
  }
```

Puede posfijar cualquier directiva con extracciones con una llamada a `as(...)`. Simplemente pasando el objeto compañero de tu clase de caso modelo al método modificador `as`, la directiva subyacente se transforma en una equivalente, que extrae sólo un valor del tipo de tu clase modelo. Tenga en cuenta que no hay reflexión involucrada y su clase case no tiene que implementar ninguna interfaz especial. El único requisito es que la directiva a la que adjuntes la llamada as produzca el número correcto de extracciones, con los tipos correctos y en el orden correcto.

Si quieres construir una instancia de la clase case a partir de extracciones producidas por varias directivas, puedes primero unir las directivas con el operador `&` antes de usar la llamada `as`:

```scala
case class Color(name: String, red: Int, green: Int, blue: Int)

val route =
  (path("color" / Segment) &
    parameters('r.as[Int], 'g.as[Int], 'b.as[Int])).as(Color) { color =>
      doSomethingWith(color) // route working with the Color instance
    }
```

Aquí la clase `Color` tiene otro miembro, `name`, que no se suministra como parámetro sino como elemento path. Uniendo las directivas path y parameters con & se crea una directiva que extrae 4 valores, que se ajustan directamente a la lista de miembros de la clase `Color` case. Por lo tanto, puede utilizar el modificador `as` para convertir la directiva en una que extraiga sólo una instancia de `Color`.

Generalmente, cuando tienes rutas que trabajan con, digamos, más de 3 extracciones es una buena idea introducir una clase case para estas y recurrir a la extracción de clases case. Sobre todo porque soporta otra característica agradable: la validación.

-----
**Precaución**
Hay una peculiaridad a tener en cuenta cuando se utiliza la extracción de clases case: Si creas un objeto compañero explícito para tu clase case, no importa si realmente le añades algún miembro o no, la sintaxis presentada arriba ya no funcionará (del todo). En lugar de as(Color) tendrá que decir as(Color.apply). Este comportamiento parece que no está realmente previsto, intentaremos trabajar con el equipo de Typesafe para solucionarlo.

-----

### Case Class Validation

En muchos casos, su servicio web necesita verificar los parámetros de entrada de acuerdo con cierta lógica antes de trabajar realmente con ellos. Por ejemplo, en el ejemplo anterior, la restricción podría ser que todos los valores de los componentes de color deben estar entre 0 y 255. Podría conseguirlo con unas cuantas directivas de validación, pero esto se convertiría rápidamente en algo engorroso y difícil de leer. Podrías hacer esto con unas cuantas directivas de validación, pero esto se convertiría rápidamente en algo engorroso y difícil de leer.

Si utiliza la extracción de clases case puede poner la lógica de verificación en el constructor de su clase case, donde debería estar:

```scala
case class Color(name: String, red: Int, green: Int, blue: Int) {
  require(!name.isEmpty, "color name must not be empty")
  require(0 <= red && red <= 255, "red color component must be between 0 and 255")
  require(0 <= green && green <= 255, "green color component must be between 0 and 255")
  require(0 <= blue && blue <= 255, "blue color component must be between 0 and 255")
}
```

Si escribes tus validaciones como esta clase de caso spray-routings la lógica de extracción recogerá correctamente todos los mensajes de error y generará un `ValidationRejection` si algo va mal. Por defecto, los `ValidationRejections` son convertidos en una respuesta de error `400 Bad Request` por el RejectionHandler por defecto, si ninguna ruta posterior gestiona correctamente la petición.

## Custom Directives

Parte de la potencia de las spray-routings se debe a la facilidad con la que es posible definir directivas personalizadas a distintos niveles de abstracción. Existen básicamente tres formas de crear directivas personalizadas:

1. Introduciendo nuevas "etiquetas" para configuraciones de directivas existentes.
2. Transformando directivas existentes
3. Escribiendo una directiva "desde cero".

### Configuration Labelling

La forma más sencilla de crear una directiva personalizada es simplemente asignar un nuevo nombre a una determinada configuración de una o más directivas existentes. De hecho, la mayoría de las directivas predefinidas de spray-routings pueden considerarse configuraciones con nombre de directivas de más bajo nivel.

La técnica básica se explica en el capítulo sobre Composición de directivas, donde, por ejemplo, una nueva directiva `getOrPut` se define así:

```scala
val getOrPut = get | put
```

Otro ejemplo son las MethodDirectives, que son simplemente instancias de una directiva de método preconfigurada, como:

```scala
val delete = method(DELETE)
val get = method(GET)
val head = method(HEAD)
val options = method(OPTIONS)
val patch = method(PATCH)
val post = method(POST)
val put = method(PUT)
```

Las directivas de bajo nivel que más a menudo forman la base de las directivas de "configuración con nombre" de nivel superior se agrupan en el rasgo BasicDirectives.

### Transforming Directives

La segunda opción para crear nuevas directivas es transformar una existente utilizando uno de los "métodos de transformación", que se definen en la clase Directiva, la clase base de todas las directivas "normales".

Aparte de los operadores combinatorios (`|` y `&`) y el extractor de clases de casos (`as[T]`) existen estas transformaciones definidas en todas las instancias de `Directive[L <: HList]`:

- map / hmap
- flatMap / hflatMap
- require / hrequire
- recover / recoverPF

#### map / hmap

El modificador hmap tiene esta firma (algo simplificada):

```scala
def hmap[R](f: L => R): Directive[R :: HNil]
```

Puede utilizarse para transformar la `HList` de extracciones en otra `HList`. El número y/o los tipos de las extracciones pueden cambiarse arbitrariamente. Si `R <: HList` entonces el resultado es `Directiva[R]`. He aquí un ejemplo algo artificioso:

```scala
import shapeless._
import spray.routing._
import Directives._

val twoIntParameters: Directive[Int :: Int :: HNil] =
  parameters('a.as[Int], 'b.as[Int])

val myDirective: Directive1[String] =
  twoIntParameters.hmap {
    case a :: b :: HNil => (a + b).toString
  }

// test `myDirective` using the testkit DSL
Get("/?a=2&b=5") ~> myDirective(x => complete(x)) ~> check {
  responseAs[String] === "7"
}
```

Si la directiva es de valor único, es decir, que extrae exactamente un valor, también puede utilizar el modificador `map` simple, que no toma como parámetro la HList de la directiva, sino el valor único en sí.

Un ejemplo de directiva predefinida basada en `map` es la directiva optionalHeaderValue.

#### flatMap / hflatMap

Con `hmap` o `map` puede transformar los valores que extrae una directiva, pero no puede cambiar la naturaleza "extractora" de la directiva. Por ejemplo, si tiene una directiva que extrae un `Int` puede usar `map` para convertirla en una directiva que extraiga ese `Int` y lo duplique, pero no puede transformarla en una directiva que duplique todos los valores `Int` positivos y rechace todos los demás.

Para hacer esto último necesitas `hflatMap` o `flatMap`. El modificador `hflatMap` tiene esta firma:

```scala
def hflatMap[R <: HList](f: L => Directive[R]): Directive[R]
```

La función dada produce una nueva directiva en función de la `HList` de extracciones de la subyacente. Como en el caso de map / hmap también existe una variante de un solo valor llamada `flatMap`, que simplifica la operación para Directivas que sólo extraen un único valor.

He aquí el ejemplo (artificial) anterior, que duplica los valores `Int` positivos y rechaza todos los demás:

```scala
import shapeless._
import spray.routing._
import Directives._

val intParameter: Directive1[Int] = parameter('a.as[Int])

val myDirective: Directive1[Int] =
  intParameter.flatMap {
    case a if a > 0 => provide(2 * a)
    case _ => reject
  }

// test `myDirective` using the testkit DSL
Get("/?a=21") ~> myDirective(i => complete(i.toString)) ~> check {
  responseAs[String] === "42"
}
Get("/?a=-18") ~> myDirective(i => complete(i.toString)) ~> check {
  handled must beFalse
}
```

Un patrón común que se basa en `flatMap` es extraer primero un valor del `RequestContext` con la directiva extract y luego `flatMap` con algún tipo de lógica de filtrado. Por ejemplo, esta es la implementación de la directiva method:

```scala
/**
 * Rechaza todas las peticiones cuyo método HTTP no coincida con el dado.
 */
def method(httpMethod: HttpMethod): Directive0 =
  extract(_.request.method).flatMap[HNil] {
    case `httpMethod` ⇒ pass
    case _            ⇒ reject(MethodRejection(httpMethod))
  } & cancelAllRejections(ofType[MethodRejection])
```

El parámetro de tipo explícito `[HNil]` en el `flatMap` es necesario en este caso porque el resultado del `flatMap` se concatena directamente con la directiva cancelAllRejections, evitando así la inferencia "outside-in" del valor del parámetro de tipo.

#### require / hrequire

El modificador `require` transforma una directiva de una sola extracción en una directiva sin extracciones, que filtra las peticiones según una función de predicado. Todas las peticiones para las que el predicado es `false` son rechazadas, todas las demás pasan sin cambios.

La firma de `require` es esta (ligeramente simplificada):

```scala
def require[T](predicate: T => Boolean): Directive[HNil]
```

Un ejemplo de directiva predefinida que depende de `require` es la primera sobrecarga de la directiva host.

Sólo puede invocar `require` en directivas de extracción única. El modificador `hrequire` es la variante más general, que toma un predicado de tipo `HList => Boolean`. Por tanto, también puede utilizarse en directivas con varias extracciones.

#### recover / recoverPF

El modificador `recover` permite "atrapar" los rechazos producidos por la directiva subyacente y, en lugar de rechazar, producir una directiva alternativa con el mismo tipo o tipos de extracciones.

La firma de `recover` es la siguiente:

```scala
def recover(recovery: List[Rejection] => Directive[L]): Directive[L]
```

En muchos casos, el modificador `recoverPF`, muy similar, podría ser un poco más fácil de usar, ya que no requiere la gestión de todos los rechazos:

```scala
def recoverPF(recovery: PartialFunction[List[Rejection], Directive[L]]): Directive[L]
```

Un ejemplo de directiva predefinida que depende de `recoverPF` es la directiva optionalHeaderValue.

### Directives from Scratch

La tercera opción para crear directivas personalizadas es hacerlo "desde cero", subclasificando directamente la clase `Directive`. La `Directive` se define así (sin operadores ni modificadores):

```scala
abstract class Directive[L <: HList] {
  def happly(f: L => Route): Route
}
```

Sólo tiene un miembro abstracto que necesitas implementar, el método `happly`, que crea la `Route` que las directivas presentan al exterior desde su función interna de construcción de la Ruta (tomando las extracciones como parámetro).

Las extracciones se guardan como una `HList` sin forma. He aquí algunos ejemplos:

- Una `Directive[HNil]` no extrae nada (como la directiva `get`). Dado que este tipo se utiliza con bastante frecuencia, spray-routing define un alias de tipo para él:

```scala
type Directive0 = Directive[HNil]
```

- Una `Directive[String :: HNil]` extrae un valor de `String` (como la directiva hostName). El alias de tipo es:

```scala
type Directive1[T] = Directive[T :: HNil]
```

- Una `Directive[Int :: String :: HNil]` extrae un valor Int y un valor String (como una directiva `parameters('a.as[Int], 'b.as[String]`).

Mantener las extracciones como HLists tiene muchas ventajas, principalmente una gran flexibilidad mientras se mantiene la seguridad de tipos y la "inferibilidad". Sin embargo, el número de veces que tendrás que volver a definir una directiva desde cero debería ser muy pequeño. De hecho, si te encuentras en una situación en la que una directiva "desde cero" es tu única opción, nos gustaría saberlo, para que podamos proporcionar un "algo" de nivel superior para otros usuarios.
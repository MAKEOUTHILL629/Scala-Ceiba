# Play Framework
Play es un framework de aplicaciones web Java y Scala de alta productividad que integra componentes y APIs para el desarrollo de aplicaciones web modernas. Play fue desarrollado por desarrolladores web para el desarrollo de aplicaciones web.

La arquitectura Modelo-Vista-Controlador (MVC) de Play le resultará familiar y fácil de aprender. Play proporciona patrones de programación concisos y funcionales. Y, la gran comunidad que desarrolla aplicaciones Play proporciona un excelente recurso para obtener respuesta a sus preguntas.

Como marco de trabajo completo, Play incluye todos los componentes necesarios para crear aplicaciones web y servicios REST, como un servidor HTTP integrado, gestión de formularios, protección contra la falsificación de solicitudes en sitios cruzados (CSRF), un potente mecanismo de enrutamiento, compatibilidad con I18n y mucho más. Play ahorra un valioso tiempo de desarrollo al soportar directamente las tareas cotidianas y la recarga en caliente para que pueda ver inmediatamente los resultados de su trabajo.

La arquitectura de Play, ligera, sin estado y apta para la web, utiliza Akka y Akka Streams de forma encubierta para proporcionar un consumo de recursos predecible y mínimo (CPU, memoria, hilos). Gracias a su modelo reactivo, las aplicaciones escalan de forma natural, tanto horizontal como verticalmente. Consulte Elasticidad y uso eficiente de recursos para obtener más información.

Play no opina sobre el acceso a bases de datos y se integra con muchas capas de mapeo relacional de objetos (ORM). Es compatible con Anorm, Slick y JPA, pero muchos clientes utilizan NoSQL u otros ORM.

## Introducción a play

Como se ilustra a continuación, Play es un framework completo con todos los componentes que necesitas para construir una aplicación web o un servicio REST, incluyendo: un servidor HTTP integrado, gestión de formularios, protección contra falsificación de petición en sitios cruzados (CSRF), un potente mecanismo de enrutamiento, soporte I18n y mucho más. Play se integra con muchas capas de mapeo relacional de objetos (ORM). Es compatible con Anorm, Ebean, Slick y JPA, pero muchos clientes utilizan NoSQL, otros ORM o incluso acceden a los datos desde un servicio REST.

![Stack de Play](/assets/play-stack.png)

Las API de Play están disponibles tanto en Java como en Scala. El marco de trabajo utiliza Akka y Akka HTTP. Esto dota a las aplicaciones Play de una arquitectura sin estado, no bloqueante y basada en eventos que proporciona escalabilidad horizontal y vertical y utiliza los recursos de forma más eficiente. Los proyectos Play contienen componentes Scala, pero como Play tiene una API Java, los desarrolladores Java no necesitan aprender Scala para utilizar Play con éxito.

### ¿ Porque se dice que play está basado en akka?

Se dice que Play está basado en Akka porque Play utiliza el modelo de actores de Akka para manejar las solicitudes de los clientes de manera concurrente y asíncrona. Akka es un framework de programación concurrente y distribuida que se basa en el modelo de actores, donde cada actor es una unidad de procesamiento independiente que puede enviar y recibir mensajes entre sí.

Play utiliza este modelo de actores para manejar las solicitudes de los clientes de manera eficiente y escalable. Cada solicitud entrante se asigna a un actor, que se encarga de procesarla de forma asincrónica. Esto permite que Play maneje múltiples solicitudes de manera concurrente sin bloquear el hilo principal de ejecución, lo que mejora el rendimiento y la capacidad de respuesta del sistema.

Además, Play también aprovecha otras características de Akka, como la tolerancia a fallos y la capacidad de distribución, lo que permite construir aplicaciones resilientes y escalables. En resumen, Play se basa en Akka para aprovechar su modelo de actores y construir aplicaciones web reactivas y escalables.

## Modelo por actores de Akka

El Modelo Actor proporciona un mayor nivel de abstracción para escribir sistemas concurrentes y distribuidos. Alivia al desarrollador de tener que lidiar con el bloqueo explícito y la gestión de hilos, haciendo más fácil escribir sistemas concurrentes y paralelos correctos. Los actores se definieron en el artículo de Carl Hewitt de 1973, pero se han popularizado con el lenguaje Erlang y se han utilizado, por ejemplo, en Ericsson con gran éxito para crear sistemas de telecomunicaciones altamente concurrentes y fiables.

El actor puede realizar las tres acciones fundamentales siguientes:

- enviar un número finito de mensajes a los Actores que conoce
- crear un número finito de nuevos Actores
- designar el comportamiento que se aplicará al siguiente mensaje

Un actor es un contenedor de Estado, Comportamiento, un Buzón, Actores Hijo y una Estrategia Supervisora. Todo esto se encapsula detrás de una Referencia de Actor. Un aspecto digno de mención es que los actores tienen un ciclo de vida explícito, no se destruyen automáticamente cuando ya no se hace referencia a ellos; después de haber creado uno, es su responsabilidad asegurarse de que eventualmente también se terminará, lo que también le da control sobre cómo se liberan los recursos Cuando un Actor Termina.

### Sistema de actores

Los actores son objetos que encapsulan estado y comportamiento, se comunican exclusivamente intercambiando mensajes que se colocan en el buzón del destinatario. En cierto sentido, los actores son la forma más estricta de programación orientada a objetos, pero es mejor considerarlos personas: al modelar una solución con actores, imaginemos un grupo de personas y asignémosles subtareas, dispongamos sus funciones en una estructura organizativa y pensemos en cómo escalar los fallos (todo ello con la ventaja de no tratar realmente con personas, lo que significa que no tenemos que preocuparnos por su estado emocional ni por cuestiones morales). El resultado puede servir de andamiaje mental para construir la aplicación informática.

#### Estructura jerárquica

Como en una organización económica, los actores forman jerarquías de forma natural. Un actor, que debe supervisar una determinada función del programa, puede querer dividir su tarea en partes más pequeñas y manejables. Para ello, crea actores secundarios.

La característica por excelencia de los sistemas de actores es que las tareas se dividen y delegan hasta que se vuelven lo suficientemente pequeñas como para ser manejadas en una sola pieza. De este modo, no sólo se estructura claramente la tarea en sí, sino que se puede razonar sobre los actores resultantes en términos de qué mensajes deben procesar, cómo deben reaccionar normalmente y cómo deben gestionarse los fallos.

Compárese esto con el diseño de software por capas, que fácilmente se convierte en programación defensiva con el objetivo de no filtrar ningún fallo: si el problema se comunica a la persona adecuada, se puede encontrar una solución mejor que si se intenta mantener todo "bajo la alfombra".

Ahora bien, la dificultad de diseñar un sistema así estriba en decidir cómo estructurar el trabajo. No existe una solución única, pero hay algunas pautas que pueden ser útiles:

- Si un actor transporta datos muy importantes (es decir, su estado no debe perderse si puede evitarse), este actor debe derivar las subtareas posiblemente peligrosas a los hijos y gestionar los fallos de estos hijos como corresponda. Dependiendo de la naturaleza de las peticiones, puede ser mejor crear un nuevo hijo para cada petición, lo que simplifica la gestión del estado para recoger las respuestas. Esto se conoce como el "Error Kernel Pattern" de Erlang.
- Si un actor depende de otro para llevar a cabo su tarea, debe vigilar la actividad de ese otro actor y actuar cuando reciba un aviso de finalización.
- Si un actor tiene múltiples responsabilidades, cada una de ellas se puede transferir a un hijo separado para simplificar la lógica y el estado.

### Definición de una clase Actor

Los Actores se implementan extendiendo el trait base Actor e implementando el método receive. El método receive debe definir una serie de sentencias case (que tienen el tipo PartialFunction[Any, Unit]) que definen qué mensajes puede manejar su Actor, utilizando la concordancia de patrones estándar de Scala, junto con la implementación de cómo deben procesarse los mensajes.

```scala
import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

class MyActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "test" => log.info("received test")
    case _      => log.info("received unknown message")
  }
}
```

## No Blocking IO

La E/S asíncrona o no bloqueante en aplicaciones web significa simplemente que el cliente no necesita esperar a que el servidor envíe una respuesta a una petición antes de ejecutar otras tareas. Cuando digo "cliente", no me refiero necesariamente al navegador: puede ser el código de la aplicación (que podría ser un servicio/controlador, por ejemplo) y el servidor podría ser la base de datos.

### Procesamiento síncrono (la forma tradicional)

Normalmente hay muchas capas de código que se ejecutan como parte del ciclo de petición/respuesta HTTP. Ejecutar esas capas de código lleva tiempo. Incluso antes de que el código de la aplicación reciba la petición, ésta ya ha sido procesada y empaquetada de forma que pueda ser manipulada, y esto ya ha consumido algo de tiempo. A medida que la aplicación crece en complejidad, se ejecutan más rutas de código para procesar una petición. En algún momento, puede que incluso tengamos que interactuar con servicios externos como la base de datos, el servidor de correo electrónico o el sistema de archivos. La aplicación tendrá que esperar en esos servicios (bloqueo) aumentando el tiempo necesario para atender una petición drásticamente.

En el diagrama siguiente, la petición del cliente se procesa de forma sincrónica, de modo que mientras el servidor está picando bits, el cliente simplemente espera. Esta no es la situación ideal, ¿verdad? Las peticiones no deberían tardar más de unos cientos de milisegundos en procesarse. Unos segundos es demasiado tiempo. Eso se convertirá en un cuello de botella más adelante, reduciendo el rendimiento y la capacidad de escalar (¿optimización prematura? - meh)

![Procesamiento síncrono](/assets/sincrona-peticiones.webp)

### Procesamiento asíncrono

En el diagrama siguiente han cambiado bastantes cosas. La cola de trabajos es la parte pertinente para el procesamiento asíncrono. Cuando una solicitud requiere el procesamiento de tareas que consumen mucho tiempo, como la E/S de archivos, esas tareas se aplazan para más tarde (sin bloqueo). De este modo, las tareas que consumen mucho tiempo quedan fuera del ciclo de solicitud-respuesta, lo que permite al servidor responder rápidamente a la solicitud. La cola de trabajos es sólo una forma de almacenar esas tareas aplazadas para su futura ejecución por el procesador correspondiente (los "consumidores").

![Procesamiento asíncrono](/assets/asincrona-peticion.webp)

Ahora nos encontramos con una serie de enigmas: ¿cómo nos aseguramos de que el trabajo (tarea) que aplazamos se ejecutará realmente? ¿Y cómo sabremos si se ha ejecutado correctamente? ¿Y si el servicio falla? ¿Y si se calienta demasiado? ¡Caramba!

Ahí es donde los frameworks de aplicaciones web asíncronas nos echan una mano. Frameworks como Play!, Tornado, etc. proporcionan los mecanismos para desarrollar un sistema de este tipo y gestionarlo bien. Por supuesto, puedes construir todos los componentes tú mismo y lanzar tu propia aplicación web asíncrona orgánica... ¡eso sí que sería un Marathon des Sables!

En una aplicación web tradicional, cuando una solicitud llega al servidor, el hilo principal de ejecución se bloquea hasta que se completa el procesamiento de la solicitud. Durante este tiempo, el hilo no puede atender otras solicitudes, lo que puede llevar a una baja capacidad de respuesta y un rendimiento deficiente, especialmente cuando se enfrenta a una carga alta de solicitudes.

En cambio, una aplicación web IO asíncrona o no bloqueante utiliza técnicas como la programación asíncrona y el uso de llamadas de E/S no bloqueantes para evitar el bloqueo del hilo principal. En lugar de esperar a que una operación de E/S se complete, se delega la operación a otro hilo o proceso y se continúa con la ejecución de otras tareas. Una vez que la operación de E/S se completa, se notifica al hilo principal y se procesa el resultado.

Esto permite que la aplicación web maneje múltiples solicitudes concurrentemente sin bloquear el hilo principal de ejecución, lo que mejora el rendimiento y la capacidad de respuesta. Además, también permite aprovechar eficientemente los recursos del sistema, ya que los hilos no se desperdician esperando operaciones de E/S.

En resumen, una aplicación web IO asíncrona o no bloqueante utiliza técnicas de programación que permiten manejar múltiples solicitudes de manera eficiente y sin bloquear el hilo principal de ejecución, lo que mejora el rendimiento y la capacidad de respuesta del sistema.

## Java NIO vs IO

### principales diferencias entre java nio e io

| io | nio |
| --- | --- |
| Orientado a flujo | Orientado a buffer |
| Bloqueante | No bloqueante |
|  | selectores |

### orientado al flujo frente a orientado al buffer

que java io esté orientado a flujos significa que lees uno o más bytes a la vez, de un flujo. lo que hagas con los bytes leídos depende de ti. no se almacenan en caché en ningún sitio. además, no puedes avanzar y retroceder en los datos de un flujo. si necesitas avanzar y retroceder en los datos leídos de un flujo, tendrás que almacenarlos primero en caché en un búfer.

el enfoque orientado al búfer de java nio es ligeramente diferente. los datos se leen en un búfer desde el que se procesan posteriormente. puedes avanzar y retroceder en el búfer según necesites. esto te da un poco más de flexibilidad durante el procesamiento. sin embargo, también tienes que comprobar si el búfer contiene todos los datos que necesitas para procesarlos completamente. y, tienes que asegurarte de que al leer más datos en el búfer, no sobrescribes datos en el búfer que aún no has procesado.

### io bloqueante frente a io no bloqueante

los diversos flujos de java io son bloqueantes. eso significa que cuando un hilo invoca una función read() o write(), ese hilo se bloquea hasta que hay algún dato que leer, o los datos se escriben completamente. el hilo no puede hacer nada más mientras tanto.

el modo no-bloqueo de java nio permite a un hilo solicitar la lectura de datos de un canal, y sólo obtener lo que está disponible en ese momento, o nada en absoluto, si no hay datos disponibles en ese momento. en lugar de permanecer bloqueado hasta que los datos estén disponibles para la lectura, el hilo puede seguir con otra cosa.

lo mismo ocurre con la escritura no bloqueante. un hilo puede solicitar que se escriban algunos datos en un canal, pero no esperar a que se escriban por completo. el hilo puede entonces continuar y hacer otra cosa mientras tanto.

cuando no están bloqueados en llamadas io, los hilos pasan su tiempo de inactividad realizando io en otros canales mientras tanto. es decir, un solo hilo puede gestionar múltiples canales de entrada y salida.

### selectores

los selectores de java nio permiten que un único subproceso supervise varios canales de entrada. puede registrar varios canales con un selector y, a continuación, utilizar un único subproceso para "seleccionar" los canales que tienen entrada disponible para su procesamiento o seleccionar los canales que están listos para escribir. este mecanismo de selector facilita que un único subproceso gestione varios canales.

### cómo influyen nio e io en el diseño de aplicaciones

la elección de nio o io como conjunto de herramientas io puede afectar a los siguientes aspectos del diseño de su aplicación:

- las llamadas api a las clases nio o io.
- el procesamiento de los datos.
- el número de hilos utilizados para procesar los datos.

### las llamadas api

por supuesto, las llamadas a la api cuando se utiliza nio tienen un aspecto diferente que cuando se utiliza io. esto no es ninguna sorpresa. en lugar de limitarse a leer los datos byte a byte desde, por ejemplo, un flujo de entrada, los datos deben leerse primero en un búfer, y luego procesarse desde allí.

### el tratamiento de datos

el procesamiento de los datos también se ve afectado cuando se utiliza un diseño nio puro, frente a un diseño io.

en un diseño io lees los datos byte a byte desde un flujo de entrada o un lector. imagina que estuvieras procesando un flujo de datos textuales basados en líneas. por ejemplo:

```text
name: anna
age: 25
email: anna@mailserver.com
phone: 1234567890
```

este flujo de líneas de texto podría procesarse así:

```java
inputstream input = ... ; // obtener el flujo de entrada del socket cliente

bufferedreader reader = new bufferedreader(new inputstreamreader(input));

string nameline   = reader.readline();
string ageline    = reader.readline();
string emailline  = reader.readline();
string phoneline  = reader.readline();
```

observe cómo el estado de procesamiento viene determinado por lo lejos que se ha ejecutado el programa. en otras palabras, una vez que retorna el primer método reader.readline(), sabe con seguridad que se ha leído una línea completa de texto. readline() se bloquea hasta que se lee una línea completa, por eso. también sabe que esta línea contiene el nombre. de forma similar, cuando retorna la segunda llamada readline(), sabe que esta línea contiene la edad, etc.

como se puede ver, el programa avanza sólo cuando hay nuevos datos que leer, y para cada paso se sabe cuáles son esos datos. una vez que el hilo de ejecución ha avanzado más allá de la lectura de un determinado dato en el código, el hilo no retrocede en los datos (la mayoría de las veces no). este principio también se ilustra en este diagrama:

![Java IO](/assets/java-io-reading-data.png)

una implementación nio tendría un aspecto diferente. he aquí un ejemplo simplificado:

```java
bytebuffer buffer = bytebuffer.allocate(48);

int bytesread = inchannel.read(buffer);
```

fíjate en la segunda línea, que lee bytes del canal en el bytebuffer. cuando vuelve esa llamada al método no sabes si todos los datos que necesitas están dentro del buffer. todo lo que sabes es que el buffer contiene algunos bytes. esto dificulta un poco el procesamiento.

Imagina que, después de la primera llamada a read(buffer), todo lo que se leyó en el buffer fue media línea. por ejemplo, "name: an". ¿puedes procesar esos datos? en realidad no. necesitas esperar hasta que al menos una línea completa de datos haya estado en el buffer, antes de que tenga sentido procesar alguno de los datos.

Entonces, ¿cómo sabe si el búfer contiene suficientes datos para que tenga sentido procesarlos? Bueno, no lo sabe. la única forma de saberlo es mirar los datos del búfer. el resultado es que puede que tenga que inspeccionar los datos del búfer varias veces antes de saber si todos los datos están ahí. esto es ineficaz y puede convertirse en un lío en términos de diseño del programa. por ejemplo:

```java
bytebuffer buffer = bytebuffer.allocate(48);

int bytesread = inchannel.read(buffer);

while(! bufferfull(bytesread) ) {
    bytesread = inchannel.read(buffer);
}
```

el método bufferfull() tiene que hacer un seguimiento de cuántos datos se leen en el búfer, y devolver true o false, dependiendo de si el búfer está lleno. en otras palabras, si el búfer está listo para ser procesado, se considera lleno.

el método bufferfull() recorre el búfer, pero debe dejar el búfer en el mismo estado que antes de llamar al método bufferfull(). de lo contrario, los siguientes datos leídos en el búfer podrían no ser leídos en el lugar correcto. esto no es imposible, pero es otro problema a tener en cuenta.

si el búfer está lleno, puede ser procesado. si no está lleno, es posible que pueda procesar parcialmente los datos que haya, si eso tiene sentido en su caso particular. en muchos casos no lo tiene.

el bucle is-data-in-buffer-ready se ilustra en este diagrama:

![Java NIO](/assets/Java-nio-reading.png)

### Resumen 

nio permite gestionar varios canales (conexiones de red o archivos) utilizando un único (o unos pocos) subprocesos, pero el coste es que el análisis de los datos puede ser algo más complicado que cuando se leen datos de un flujo de bloqueo.

si necesitas gestionar miles de conexiones abiertas simultáneamente, cada una de las cuales sólo envía unos pocos datos, por ejemplo un servidor de chat, implementar el servidor en nio probablemente sea una ventaja. del mismo modo, si necesitas mantener muchas conexiones abiertas a otros ordenadores, por ejemplo en una red p2p, utilizar un único hilo para gestionar todas las conexiones salientes podría ser una ventaja. este diseño de un hilo, múltiples conexiones se ilustra en este diagrama:

![Java NIO](/assets/java-nio-single-thread.png)

si tiene menos conexiones con un ancho de banda muy elevado, que envían muchos datos a la vez, quizá la implementación de un servidor io clásico sea la más adecuada. este diagrama ilustra un diseño de servidor io clásico:

![Java IO](/assets/java-io-server-desing.png)

## Play Framework

### Introduccion a Play 2

Desde 2007, trabajamos para facilitar el desarrollo de aplicaciones web Java. Play comenzó como un proyecto interno en Zenexity (ahora Zengularity) y fue fuertemente influenciado por nuestra forma de hacer proyectos web: centrándose en la productividad del desarrollador, respetando la arquitectura web, y utilizando un enfoque fresco a las convenciones de empaquetado desde el principio - rompiendo las llamadas mejores prácticas JEE donde tenía sentido.

En 2009, decidimos compartir estas ideas con la comunidad como un proyecto de código abierto. La respuesta inmediata fue extremadamente positiva y el proyecto ganó mucha tracción. Hoy, tras años de desarrollo público activo, Play cuenta con varias versiones, una comunidad activa de más de 10.000 personas y un número creciente de aplicaciones en producción en todo el mundo.

Abrir un proyecto al mundo significa sin duda recibir más comentarios, pero también descubrir y aprender sobre nuevos casos de uso, funciones necesarias y desenterrar errores que no se tuvieron en cuenta específicamente en el diseño original y sus supuestos. Durante estos años de trabajo en Play como proyecto de código abierto hemos trabajado para solucionar este tipo de problemas, así como para integrar nuevas funciones que den soporte a una gama más amplia de escenarios. A medida que el proyecto ha ido creciendo, hemos aprendido mucho de nuestra comunidad y de nuestra propia experiencia, utilizando Play en proyectos cada vez más complejos y variados.

Mientras tanto, la tecnología y la web han seguido evolucionando. La web se ha convertido en el punto central de todas las aplicaciones. Las tecnologías HTML, CSS y JavaScript han evolucionado rápidamente, haciendo casi imposible que un framework del lado del servidor pueda seguirles el ritmo. Toda la arquitectura web avanza rápidamente hacia el procesamiento en tiempo real, y los requisitos emergentes de los perfiles de proyecto actuales implican que SQL ya no funciona como tecnología exclusiva de almacenamiento de datos. En el ámbito de los lenguajes de programación, hemos asistido a cambios monumentales con la popularización de varios lenguajes JVM, entre ellos Scala.

### Diseñado para la programación asíncrona

Las aplicaciones web actuales integran más datos concurrentes en tiempo real, por lo que los frameworks web necesitan soportar un modelo de programación HTTP asíncrono completo. Play se diseñó inicialmente para gestionar aplicaciones web clásicas con muchas peticiones de corta duración. Pero ahora, el modelo de eventos es el camino a seguir para conexiones persistentes - a través de Comet, long-polling y WebSockets.

Play 2 está diseñado desde el principio bajo el supuesto de que cada solicitud es potencialmente de larga duración. Pero eso no es todo: también necesitamos una forma potente de programar y ejecutar tareas de larga duración. El modelo basado en Actor es sin duda el mejor modelo actual para manejar sistemas altamente concurrentes, y la mejor implementación de ese modelo disponible tanto para Java como para Scala es Akka, por lo que va a entrar. Play 2 proporciona soporte nativo de Akka para aplicaciones Play, haciendo posible escribir sistemas altamente distribuidos.

### Centrados en la seguridad tipográfica

Una de las ventajas de utilizar un lenguaje de programación de tipado estático para escribir aplicaciones Play es que el compilador puede comprobar partes de tu código. Esto no sólo es útil para detectar errores en las primeras fases del proceso de desarrollo, sino que también facilita mucho el trabajo en proyectos grandes con muchos desarrolladores implicados.

Añadiendo Scala a la mezcla para Play 2, nos beneficiamos claramente de garantías de compilador aún más fuertes - pero eso no es suficiente. En Play 1.x, el sistema de plantillas era dinámico, basado en el lenguaje Groovy, y el compilador no podía hacer mucho por ti. Como resultado, los errores en las plantillas sólo podían detectarse en tiempo de ejecución. Lo mismo ocurre con la verificación del código glue con los controladores.

En la versión 2.0, realmente queríamos empujar esta idea de tener Play comprobar la mayor parte de su código en tiempo de compilación. Esta es la razón por la que decidimos utilizar el motor de plantillas basado en Scala como predeterminado para las aplicaciones Play, incluso para los desarrolladores que utilizan Java como lenguaje de programación principal. Esto no significa que tengas que convertirte en un experto en Scala para escribir plantillas en Play 2, del mismo modo que no era necesario conocer Groovy para escribir plantillas en Play 1.x.

En las plantillas, Scala se utiliza principalmente para navegar por tu grafo de objetos con el fin de mostrar información relevante, con una sintaxis muy cercana a la de Java. Sin embargo, si quieres dar rienda suelta a la potencia de Scala para escribir abstracciones de plantillas avanzadas, descubrirás rápidamente cómo Scala, al estar orientado a expresiones y ser funcional, encaja a la perfección en un motor de plantillas.

Y eso no sólo es cierto para el motor de plantillas: el sistema de rutas también está totalmente verificado por tipos. Play 2 comprueba las descripciones de tus rutas, y verifica que todo es consistente, incluyendo la parte de enrutamiento inverso.

Un buen efecto secundario de estar totalmente compilado es que las plantillas y los archivos de ruta serán más fáciles de empaquetar y reutilizar. También se obtiene una ganancia significativa en el rendimiento de estas partes en tiempo de ejecución.

### Compatibilidad nativa con Java y Scala

Al principio de la historia del proyecto Play, empezamos a explorar la posibilidad de utilizar el lenguaje de programación Scala para escribir aplicaciones Play. Inicialmente introdujimos este trabajo como un módulo externo, para poder experimentar libremente sin afectar al propio framework.

Integrar correctamente Scala en un framework basado en Java no es trivial. Teniendo en cuenta la compatibilidad de Scala con Java, se puede conseguir rápidamente una primera integración ingenua que simplemente utilice la sintaxis de Scala en lugar de la de Java. Esto, sin embargo, no es ciertamente la forma óptima de utilizar el lenguaje. Scala es una mezcla de verdadera orientación a objetos con programación funcional. Aprovechar toda la potencia de Scala requiere repensar la mayoría de las API del framework.

Rápidamente llegamos a los límites de lo que podemos hacer con el soporte de Scala como módulo independiente. Las decisiones iniciales de diseño que tomamos en Play 1.x, basadas en gran medida en la API de reflexión de Java y la manipulación de código de bytes, han hecho que sea más difícil progresar sin replantear por completo algunas partes esenciales del funcionamiento interno de Play. Mientras tanto, hemos creado varios componentes impresionantes para el módulo Scala, como el nuevo motor de plantillas de tipo seguro y el nuevo componente de acceso SQL Anorm. Esta es la razón por la que decidimos que, para dar rienda suelta a la potencia de Scala con Play, trasladaríamos el soporte de Scala de un módulo separado al núcleo de Play 2, que está diseñado desde el principio para soportar de forma nativa Scala como lenguaje de programación.

Java, por otro lado, no está recibiendo menos soporte de Play 2, sino todo lo contrario. La versión Play 2 nos brinda la oportunidad de mejorar la experiencia de desarrollo de los desarrolladores Java. Los desarrolladores Java obtienen una API Java real escrita teniendo en cuenta todas las especificidades de Java.

### Potente sistema de construcción

Desde el principio del proyecto Play, hemos optado por una forma novedosa de ejecutar, compilar y desplegar las aplicaciones Play. Puede que al principio pareciera un diseño esotérico, pero era crucial para proporcionar una API HTTP asíncrona en lugar de la API Servlet estándar, ciclos de respuesta cortos a través de la compilación en vivo y la recarga del código fuente durante el desarrollo, y promover un enfoque de empaquetado fresco. En consecuencia, era difícil hacer que Play siguiera las convenciones JEE estándar.

Hoy en día, esta idea de despliegue sin contenedor está cada vez más aceptada en el mundo Java. Es una elección de diseño que ha permitido que Play Framework funcione de forma nativa en plataformas como Heroku, que introdujo un modelo que consideramos el futuro del despliegue de aplicaciones Java en plataformas PaaS elásticas.

Sin embargo, los sistemas de compilación Java existentes no eran lo suficientemente flexibles como para soportar este nuevo enfoque. Como queríamos proporcionar herramientas sencillas para ejecutar y desplegar aplicaciones Play, en Play 1.x creamos una colección de scripts Python para gestionar las tareas de compilación y despliegue.

Mientras tanto, los desarrolladores que utilizan Play para proyectos de mayor escala empresarial, que requieren la personalización del proceso de compilación y la integración con los sistemas de compilación existentes en la empresa, estaban un poco perdidos. Los scripts de Python que proporcionamos con Play 1.x no son en modo alguno un sistema de compilación completo y no son fácilmente personalizables. Por eso hemos decidido apostar por un sistema de compilación más potente para Play 2.

Dado que necesitamos una herramienta de construcción moderna, lo suficientemente flexible como para soportar las convenciones originales de Play y capaz de construir proyectos Java y Scala, hemos optado por integrar sbt en Play 2. sbt es la herramienta de construcción de facto para Scala y es cada vez más aceptada en la comunidad Java también.

Esto también significa una mejor integración con los proyectos de Maven fuera de la caja, la capacidad de empaquetar y publicar su proyecto como un simple conjunto de archivos JAR a cualquier repositorio, y sobre todo la compilación en vivo y la recarga en tiempo de desarrollo de cualquier proyecto dependiente, incluso para Java estándar o proyectos de bibliotecas Scala.

### Almacén de datos e integración de modelos

Almacén de datos" ya no es sinónimo de "base de datos SQL", y probablemente nunca lo haya sido. Se están popularizando muchos modelos interesantes de almacenamiento de datos, que ofrecen diferentes propiedades para diferentes escenarios. Por esta razón, se ha vuelto difícil para un framework web como Play hacer suposiciones atrevidas sobre el tipo de almacén de datos que utilizarán los desarrolladores. Un concepto de modelo genérico en Play ya no tiene sentido, ya que es casi imposible abstraer todo este tipo de tecnologías con una única API.

En Play 2, queríamos que fuera realmente fácil utilizar cualquier controlador de almacén de datos, ORM o cualquier otra biblioteca de acceso a bases de datos sin ninguna integración especial con el marco web. Simplemente queremos ofrecer un conjunto mínimo de ayudantes para manejar problemas técnicos comunes, como la gestión de los límites de conexión. También queremos, sin embargo, mantener el aspecto de pila completa de Play Framework mediante la agrupación de herramientas por defecto para acceder a bases de datos clásicas para los usuarios que no tienen necesidades especializadas, y es por eso que Play 2 viene con bibliotecas de acceso a bases de datos relacionales integradas como Ebean, JPA y Anorm.

## Sbt

### Uso de la consola sbt

Puedes gestionar el ciclo completo de desarrollo de una aplicación Play con sbt. La herramienta sbt tiene un modo interactivo o puedes introducir comandos de uno en uno. El modo interactivo puede ser más rápido en el tiempo porque sbt sólo necesita iniciarse una vez. Cuando se introducen comandos de uno en uno, sbt se reinicia cada vez que se ejecuta.

### Comandos individuales

Puedes ejecutar comandos sbt individuales directamente. Por ejemplo, para compilar y ejecutar Play, cambie al directorio de su proyecto y ejecute:

```bash
sbt run
```

Verás algo como:

```bash
[info] Loading project definition from /Users/play-developer/my-first-app/project
[info] Set current project to my-first-app (in build file:/Users/play-developer/my-first-app/)

--- (Running the application from sbt, auto-reloading is enabled) ---

[info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Enter to stop and go back to the console...)
The application starts directly. When you quit the server using Ctrl+D or Enter, the command prompt returns.
```

### Modo interactivo

Para lanzar sbt en modo interactivo, cambie al nivel superior de su proyecto e introduzca sbt sin argumentos:

```bash
cd my-first-app
my-first-app $  sbt
```

Y verás algo como:

```bash
[info] Loading global plugins from /Users/play-developer/.sbt/0.13/plugins
[info] Loading project definition from /Users/play-developer/my-first-app/project
[info] Updating {file:/Users/play-developer/my-first-app/project/}my-first-app-build...
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[info] Set current project to my-first-app (in build file:/Users/play-developer/my-first-app/)
[my-first-app] $
```

### Modo de desarrollo

En este modo, sbt lanza Play con la función de auto-recarga activada. Cuando hagas una petición, Play recompilará automáticamente y reiniciará tu servidor si algún archivo ha cambiado. Si es necesario, la aplicación se reiniciará automáticamente.

Con sbt en modo interactivo, ejecuta la aplicación actual en modo desarrollo, usa el comando run:

```bash
[my-first-app] $ run
```

Y verás algo como:

```bash
$ sbt
[info] Loading global plugins from /Users/play-developer/.sbt/0.13/plugins
[info] Loading project definition from /Users/play-developer/my-first-app/project
[info] Set current project to my-first-app (in build file:/Users/play-developer/my-first-app/)
[my-first-app] $ run

--- (Running the application, auto-reloading is enabled) ---

[info] p.c.s.AkkaHttpServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Ctrl+D to stop and go back to the console...)
```

### Sólo compilación

También puede compilar su aplicación sin ejecutar el servidor HTTP. El comando compilar muestra cualquier error de la aplicación en la ventana de comandos. Por ejemplo, en modo interactivo, introduzca:

```bash
[my-first-app] $ compile
```

Y verás algo como:

```bash
[my-first-app] $ compile
[info] Compiling 1 Scala source to /Users/play-developer/my-first-app/target/scala-2.11/classes...
[error] /Users/play-developer/my-first-app/app/controllers/HomeController.scala:21: not found: value Actionx
[error]   def index = Actionx { implicit request =>
[error]               ^
[error] one error found
[error] (compile:compileIncremental) Compilation failed
[error] Total time: 1 s, completed Feb 6, 2017 2:00:07 PM
[my-first-app] $
```

Si no hay errores con tu código, lo verás:

```bash
[my-first-app] $ compile
[info] Updating {file:/Users/play-developer/my-first-app/}root...
[info] Resolving jline#jline;2.12.2 ...
[info] Done updating.
[info] Compiling 8 Scala sources and 1 Java source to /Users/play-developer/my-first-app/target/scala-2.11/classes...
[success] Total time: 3 s, completed Feb 6, 2017 2:01:31 PM
[my-first-app] $
```

### Opciones de prueba

Puede realizar pruebas sin ejecutar el servidor. Por ejemplo, en modo interactivo, utilice el comando de prueba

```bash
[my-first-app] $ test
```

Los comandos de prueba ejecutarán todas las pruebas de su proyecto. También puede utilizar testOnly para seleccionar pruebas específicas:

```bash
[my-first-app] $ testOnly com.acme.SomeClassTest
```

### Iniciar la consola interactiva

Escribe console para entrar en la consola interactiva de Scala, que te permite probar tu código de forma interactiva:

```bash
[my-first-app] $ console
```

Para iniciar la aplicación dentro de la consola de scala (por ejemplo, para acceder a la base de datos):

```bash
import play.api._
val env     = Environment(new java.io.File("."), this.getClass.getClassLoader, Mode.Dev)
val context = ApplicationLoader.createContext(env)
val loader  = ApplicationLoader(context)
val app     = loader.load(context)
Play.start(app)
```

### Debugging

Puedes pedir a Play que inicie un puerto de depuración JPDA al iniciar la consola. Entonces puedes conectarte usando el depurador de Java. Usa el comando sbt -jvm-debug <port> para hacerlo:

```bash
sbt -jvm-debug 9999
```

Cuando un puerto JPDA está disponible, la JVM registrará esta línea durante el arranque:

```bash
Listening for transport dt_socket at address: 9999
```

### Uso de las funciones de sbt

Puede utilizar funciones de sbt como la ejecución activada.

Por ejemplo, usando ~ compile:

```bash
[my-first-app] $ ~ compile
```

La compilación se activará cada vez que modifique un archivo fuente.

Si está utilizando ~ run:

```bash
[my-first-app] $ ~ run
```

La compilación activada se activará mientras se esté ejecutando un servidor de desarrollo.

También puede hacer lo mismo para ~ test, para probar continuamente su proyecto cada vez que modifique un archivo fuente:

```bash
[my-first-app] $ ~ test
```

Esto podría ser especialmente útil si desea ejecutar sólo un pequeño conjunto de sus pruebas utilizando el comando testOnly. Por ejemplo:

```bash
[my-first-app] $ ~ testOnly com.acme.SomeClassTest 
```

Activará la ejecución de la prueba com.acme.SomeClassTest cada vez que modifique un archivo fuente.

### Utilizar directamente los comandos de reproducción

También puedes ejecutar comandos directamente sin entrar en la consola Play. Por ejemplo, introduzca sbt run:

```bash
$ sbt run
[info] Loading project definition from /Users/play-developer/my-first-app/project
[info] Set current project to my-first-app (in build file:/Users/play-developer/my-first-app/)

--- (Running the application from sbt, auto-reloading is enabled) ---

[info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Enter to stop and go back to the console...)
```

La aplicación se inicia directamente. Cuando salga del servidor usando Ctrl+D o Enter, volverá al prompt de su sistema operativo.

Por defecto, el servidor está enlazado al puerto 9000. Se puede especificar un puerto personalizado (por ejemplo, 8080): sbt 'run 8080'

Por supuesto, la ejecución disparada también está disponible aquí:

```bash
sbt ~run
```

### Donde quedan los binarios y compilados

Cuando ejecutas el comando sbt compile, los archivos compilados y binarios se generan en el directorio `target/scala-<versión>/classes`. 

El directorio `target` es creado en la raíz del proyecto cuando se ejecuta el comando sbt compile por primera vez. Dentro de `target`, se crea un subdirectorio llamado `scala-<versión>`, donde `<versión>` representa la versión de Scala que estás utilizando en tu proyecto.

Dentro de `scala-<versión>`, se encuentra el directorio classes, que es donde se generan los archivos compilados y binarios de tu proyecto. Estos archivos son generados a partir del código fuente de tu proyecto y se organizan en la estructura de paquetes definida en tu proyecto.

Por ejemplo, si tienes un archivo fuente MiClase.scala ubicado en el paquete com.example, después de compilar tu proyecto, el archivo compilado MiClase.class se ubicará en `target/scala-<versión>/classes/com/example`.

Es importante tener en cuenta que el directorio target y su contenido son generados por SBT y se pueden regenerar cada vez que ejecutes comandos de compilación o construcción, como sbt compile o sbt package. Por lo tanto, no se recomienda realizar cambios directamente en este directorio, ya que se pueden sobrescribir en futuras compilaciones.

## Anatomía de una aplicación Play

### El diseño de la aplicación Play

El diseño de una aplicación Play está estandarizado para mantener las cosas lo más simples posible. Tras la primera compilación correcta, la estructura del proyecto tiene este aspecto:

```text
app                      → Fuentes de aplicación
 └ assets                → Fuentes de activos recopiladas
    └ stylesheets        → Fuentes CSS típicas de LESS
    └ javascripts        → Fuentes típicas de CoffeeScript
 └ controllers           → Controladores de aplicación
 └ models                → Capa de negocio de la aplicación
 └ views                 → Templates
build.sbt                → Script de creación de aplicaciones
conf                     → Archivos de configuración y otros recursos no compilados (en classpath)
 └ application.conf      → Fichero de configuración principal
 └ routes                → Definición de rutas
dist                     → Archivos arbitrarios para incluir en la distribución de sus proyectos
public                   → Patrimonio público
 └ stylesheets           → CSS files
 └ javascripts           → Javascript files
 └ images                → Image files
project                  → Archivos de configuración de sbt
 └ build.properties      → Marcador para el proyecto sbt
 └ plugins.sbt           → sbt plugins, incluida la declaración para el propio Play
lib                      → Dependencias de bibliotecas no gestionadas
logs                     → Logs folder
 └ application.log       → Default log file
target                   → Cosas generadas
 └ resolution-cache      → Información sobre dependencias
 └ scala-2.11
    └ api                → Generated API docs
    └ classes            → Compiled class files
    └ routes             → Sources generated from routes
    └ twirl              → Sources generated from templates
 └ universal             → Application packaging
 └ web                   → Compiled web assets
test                     → carpeta fuente para pruebas unitarias o funcionales
```

### El directorio app

El directorio app contiene todos los artefactos ejecutables: código fuente Java y Scala, plantillas y fuentes de activos compilados.

Hay tres paquetes en el directorio app, uno para cada componente del patrón arquitectónico MVC:

- app/controllers
- app/models
- app/views

Puedes añadir tus propios paquetes, por ejemplo, un paquete app/utils.

### El directorio public/

Los recursos almacenados en el directorio público son activos estáticos que el servidor Web sirve directamente.

Este directorio se divide en tres subdirectorios para imágenes, hojas de estilo CSS y archivos JavaScript. Debe organizar sus activos estáticos de esta forma para que todas las aplicaciones Play sean coherentes.

### El directorio conf/

El directorio conf contiene los archivos de configuración de la aplicación. Hay dos archivos de configuración principales:

- application.conf, el archivo de configuración principal de la aplicación, que contiene parámetros de configuración
- routes, el archivo de definición de rutas.

Si necesitas añadir opciones de configuración específicas para tu aplicación, es una buena idea añadir más opciones al archivo application.conf.

Si una biblioteca necesita un archivo de configuración específico, es bueno proporcionarlo en el directorio conf.

### El directorio lib/

El directorio lib es opcional y contiene dependencias de librerías no gestionadas, es decir, todos los archivos JAR que quieras gestionar manualmente fuera del sistema de compilación. Sólo tienes que soltar cualquier archivo JAR aquí y se añadirán al classpath de tu aplicación.

### El archivo build.sbt

Las principales declaraciones de compilación de su proyecto se encuentran generalmente en build.sbt en la raíz del proyecto. Los archivos .scala en el directorio project/ también pueden ser usados para declarar la construcción de tu proyecto.

### El directorio project/

El directorio del proyecto contiene las definiciones de compilación de sbt:

- plugins.sbt define los plugins sbt utilizados por este proyecto.
- build.properties contiene la versión de sbt que se utilizará para compilar la aplicación.

### El directorio target/

El directorio de destino contiene todo lo generado por el sistema de compilación. Puede ser útil saber lo que se genera aquí:

- classes/ contiene todas las clases compiladas (tanto de fuentes Java como Scala).
- classes_managed/ contiene sólo las clases que son gestionadas por el framework (como las clases generadas por el router o el sistema de plantillas). Puede ser útil añadir esta carpeta de clases como una carpeta de clases externa en tu proyecto IDE.
- resource_managed/ contiene recursos generados, típicamente activos compilados como CSS LESS y resultados de compilación CoffeeScript.
- src_managed/ contiene fuentes generadas, como las fuentes Scala generadas por el sistema de plantillas.
- web/ contiene activos procesados por sbt-web, como los de las carpetas app/assets y public.

### Archivo .gitignore típico

Las carpetas generadas deben ser ignoradas por tu sistema de control de versiones. Este es el típico archivo .gitignore para una aplicación Play:

```text
logs
project/project
project/target
target
tmp
dist
.cache
```

## Sintaxis y características del archivo de configuración

El archivo de configuración de una aplicación Play debe definirse en conf/aplication.conf. Utiliza el formato HOCON.

Además del archivo application.conf, la configuración proviene de un par de lugares más.

- La configuración por defecto se carga desde cualquier archivo reference.conf que se encuentre en el classpath. La mayoría de los JAR de Play incluyen un archivo reference.conf con la configuración predeterminada. Los ajustes en application.conf anularán los ajustes en los archivos reference.conf.
- También es posible establecer la configuración mediante las propiedades del sistema. Las propiedades del sistema anulan la configuración de application.conf.

La forma idiomática de utilizar Config es tener todas las claves de configuración definidas en algún lugar, ya sea en reference.conf o application.conf. Si la clave no tiene un valor predeterminado razonable, normalmente se establece en null para significar "sin valor".

### Especificación de un archivo de configuración alternativo

En tiempo de ejecución, el application.conf por defecto se carga desde el classpath. Las propiedades del sistema se pueden utilizar para forzar una fuente de configuración diferente:

- config.resource especifica un nombre de recurso que incluye la extensión, es decir, application.conf y no sólo application
- config.file especifica una ruta de sistema de archivos, de nuevo debe incluir la extensión, no ser un nombre base

Estas propiedades del sistema especifican un reemplazo para application.conf, no una adición. Si aún desea utilizar algunos valores del archivo application.conf entonces puede incluir el application.conf en su otro archivo .conf escribiendo include "application" en la parte superior de ese archivo. Después de incluir la configuración de application.conf en su nuevo archivo .conf puede especificar cualquier configuración que desee anular.

### Utilización desde el controlador

La configuración puede estar disponible en tu controlador (o en tu componente), para utilizar la configuración por defecto o la personalizada, gracias a la Inyección de Dependencias (en Scala o en Java).

```scala
import javax.inject._
import play.api.Configuration

class MyController @Inject()(config: Configuration) {
  // ...
}
```

### Uso con Akka

Akka utilizará el mismo archivo de configuración que el definido para tu aplicación Play. Esto significa que puedes configurar cualquier cosa en Akka en el archivo application.conf. En Play, Akka lee su configuración desde dentro de la configuración play.akka, no desde la configuración akka.

### Utilización con la orden de ejecución

Hay un par de cosas especiales que debes saber sobre la configuración cuando ejecutes tu aplicación con el comando run.

#### Extra devSettings

Puedes configurar opciones adicionales para el comando run en tu build.sbt. Estos ajustes no se utilizarán cuando despliegues tu aplicación.

```bash
PlayKeys.devSettings += "play.server.http.port" -> "8080"
```

#### Configuración del servidor HTTP en application.conf

En modo ejecución, la parte del servidor HTTP de Play se inicia antes de que la aplicación haya sido compilada. Esto significa que el servidor HTTP no puede acceder al archivo application.conf cuando se inicia. Si desea anular la configuración del servidor HTTP mientras utiliza el comando de ejecución, no puede utilizar el archivo application.conf. En su lugar, debe utilizar las propiedades del sistema o la configuración devSettings mostrada anteriormente. Un ejemplo de configuración del servidor es el puerto HTTP. Otros ajustes del servidor se pueden ver aquí.

```bash
run -Dhttp.port=1234
```

También existe un espacio de nombres específico si necesitas personalizar la configuración de Akka para el modo de desarrollo (el modo utilizado con el comando run). Necesitas anteponer a tu configuración en PlayKeys.devSettings play.akka.dev-mode, por ejemplo:

```Scala
PlayKeys.devSettings += "play.akka.dev-mode.akka.cluster.log-info" -> "off"
```

Esto es especialmente útil si existe algún conflicto entre el Akka ActorSystem utilizado para ejecutar el servidor de desarrollo y el ActorSystem utilizado por la propia aplicación.

## La API de configuración de Scala

Play usa la librería Typesafe config, pero Play también proporciona un bonito envoltorio Scala llamado Configuration con características Scala más avanzadas. Si no estás familiarizado con Typesafe config, puede que también quieras leer la documentación sobre sintaxis y características del archivo de configuración.

### Acceso a la configuración

Normalmente, obtendrás un objeto Configuration a través de la inyección de dependencias, o simplemente pasando una instancia de Configuration a tu componente:

```scala
class MyController @Inject()(config: Configuration, c: ControllerComponents) extends AbstractController(c) {
  def getFoo = Action {
    Ok(config.get[String]("foo"))
  }
}
```

El método get es el más común que utilizarás. Se utiliza para obtener un único valor en una ruta del fichero de configuración.

```scala
// foo = bar
config.get[String]("foo")

// bar = 8
config.get[Int]("bar")

// baz = true
config.get[Boolean]("baz")

// listOfFoos = ["bar", "baz"]
config.get[Seq[String]]("listOfFoos")
```

Acepta un ConfigLoader implícito, pero para los tipos más comunes como String, Int, e incluso Seq[String], ya existen cargadores definidos que hacen lo que se espera.

La configuración también soporta la validación contra un conjunto de valores válidos:

```scala
config.getAndValidate[String]("foo", Set("bar", "baz"))
```

### ConfigLoader

Definiendo tu propio ConfigLoader, puedes convertir fácilmente la configuración en un tipo personalizado. Esto se utiliza ampliamente en Play internamente, y es una gran manera de traer más seguridad de tipo a su uso de la configuración. Por ejemplo:

```scala
case class AppConfig(title: String, baseUri: URI)
object AppConfig {

  implicit val configLoader: ConfigLoader[AppConfig] = new ConfigLoader[AppConfig] {
    def load(rootConfig: Config, path: String): AppConfig = {
      val config = rootConfig.getConfig(path)
      AppConfig(
        title = config.getString("title"),
        baseUri = new URI(config.getString("baseUri"))
      )
    }
  }
}
```

A continuación, puede utilizar config.get como hicimos anteriormente:

```scala
// app.config = {
//   title = "My App
//   baseUri = "https://example.com/"
// }
config.get[AppConfig]("app.config")
```

### Teclas de configuración opcionales

La Configuración de Play permite obtener claves de configuración opcionales mediante el método getOptional[A]. Funciona igual que get[A] pero devolverá None si la clave no existe. En lugar de utilizar este método, se recomienda establecer las claves opcionales en null en el archivo de configuración y utilizar get[Option[A]]. Pero proporcionamos este método por conveniencia en caso de que necesite interactuar con bibliotecas que utilizan la configuración de una manera no estándar.

## Acciones, controladores y resultados

### ¿Qué es una acción?

La mayoría de las peticiones que recibe una aplicación Play son gestionadas por una Acción.

Una acción play.api.mvc.Action es básicamente una función (play.api.mvc.Request => play.api.mvc.Result) que gestiona una solicitud y genera un resultado que se envía al cliente.

```scala
def echo = Action { request =>
  Ok("Got request [" + request + "]")
}
```

Una acción devuelve un valor play.api.mvc.Result, que representa la respuesta HTTP a enviar al cliente web. En este ejemplo Ok construye una respuesta 200 OK que contiene un cuerpo de respuesta text/plain. Para ver más ejemplos de respuestas HTTP, consulta los métodos play.api.mvc.Result.

### Construir una acción

Dentro de cualquier controlador que extienda BaseController, el valor Action es el constructor de acciones por defecto. Este constructor de acciones contiene varios ayudantes para crear Acciones.

El primero y más sencillo toma como argumento un bloque de expresión que devuelve un resultado:

```scala
Action {
  Ok("Hello world")
}
```

Esta es la forma más sencilla de crear una Acción, pero no obtenemos una referencia a la petición entrante. A menudo es útil acceder a la petición HTTP que llama a esta Acción.

Así que hay otro constructor de Acción que toma como argumento una función Solicitud => Resultado:

```scala
Action { request =>
  Ok("Got request [" + request + "]")
}
```

A menudo es útil marcar el parámetro de solicitud como implícito para que pueda ser utilizado implícitamente por otras APIs que lo necesiten:

```scala
Action { implicit request =>
  Ok("Got request [" + request + "]")
}
```

Si has dividido tu código en métodos, entonces puedes pasar a través de la solicitud implícita de la acción:

```scala
def action = Action { implicit request =>
  anotherMethod("Some para value")
  Ok("Got request [" + request + "]")
}

def anotherMethod(p: String)(implicit request: Request[_]) = {
  // do something that needs access to the request
}
```

La última forma de crear un valor Action es especificar un argumento BodyParser adicional:
  
```scala
Action(parse.json) { implicit request =>
  Ok("Got request [" + request + "]")
}
```

Los analizadores de cuerpo se tratarán más adelante en este manual. Por ahora sólo necesitas saber que los otros métodos de creación de valores de Acción utilizan un analizador de cuerpo de contenido Any por defecto.

### Los controladores son generadores de acciones

Un controlador en Play no es más que un objeto que genera valores de Acción. Los controladores suelen definirse como clases para aprovechar las ventajas de la inyección de dependencias.

El caso de uso más sencillo para definir un generador de acciones es un método sin parámetros que devuelve un valor de Acción:
  
```scala
package controllers

  import javax.inject.Inject

  import play.api.mvc._

  class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    def index = Action {
      Ok("It works!")
    }

  }
```

Por supuesto, el método generador de acciones puede tener parámetros, y estos parámetros pueden ser capturados por el cierre Action:
  
```scala
def hello(name: String) = Action {
  Ok("Hello " + name)
}
```

### Resultados sencillos

Por ahora sólo nos interesan los resultados simples: un resultado HTTP con un código de estado, un conjunto de cabeceras HTTP y un cuerpo que se enviará al cliente web.

Estos resultados están definidos por play.api.mvc.Result:

```scala
import play.api.http.HttpEntity

def index = Action {
  Result(
    header = ResponseHeader(200, Map.empty),
    body = HttpEntity.Strict(ByteString("Hello world!"), Some("text/plain"))
  )
}
```

Por supuesto, hay varios ayudantes disponibles para crear resultados comunes, como el resultado Ok del ejemplo anterior:
  
```scala
def index = Action {
  Ok("Hello world!")
}
```

Esto produce exactamente el mismo resultado que antes.

Aquí hay varios ejemplos para crear varios resultados:

```scala
val ok           = Ok("Hello world!")
val notFound     = NotFound
val pageNotFound = NotFound(<h1>Page not found</h1>)
val badRequest   = BadRequest(views.html.form(formWithErrors))
val oops         = InternalServerError("Oops")
val anyStatus    = Status(488)("Strange response type")
```

Todos estos ayudantes se pueden encontrar en el objeto play.api.mvc.Results trait y companion.

### Las redirecciones también son resultados sencillos

Redirigir el navegador a una nueva URL es otro tipo de resultado simple. Sin embargo, estos tipos de resultado no toman un cuerpo de respuesta.

Hay varios ayudantes disponibles para crear resultados de redirección:
  
```scala
def index = Action {
  Redirect("/user/home")
}
```

Por defecto se utiliza un tipo de respuesta 303 SEE_OTHER, pero también puede establecer un código de estado más específico si lo necesita:

```scala
def index = Action {
  Redirect("/user/home", MOVED_PERMANENTLY)
}
```

### Página de prueba TODO

Puede utilizar una implementación de Acción vacía definida como TODO: el resultado es una página de resultados estándar 'Not implemented yet':

```scala
def index(name: String) = TODO
```

## HTTP routing

### El enrutador HTTP integrado

El enrutador es el componente encargado de traducir cada solicitud HTTP entrante a una Acción.

Una petición HTTP es vista como un evento por el framework MVC. Este evento contiene dos piezas principales de información:

- la ruta de la solicitud (por ejemplo, /clientes/1542, /fotos/lista), incluida la cadena de consulta
- el método HTTP (por ejemplo, GET, POST, ...).

Las rutas se definen en el archivo conf/routes, que se compila. Esto significa que verás los errores de ruta directamente en tu navegador.

### Inyección de dependencia

El generador de rutas por defecto de Play crea una clase de enrutador que acepta instancias de controlador en un constructor anotado @Inject. Esto significa que la clase es adecuada para su uso con inyección de dependencias y también se puede instanciar manualmente utilizando el constructor.

Play también viene con un generador de rutas estáticas heredado que funciona con controladores declarados como objetos. Esto generalmente no se recomienda porque rompe la encapsulación, hace que el código sea menos comprobable y es incompatible con muchas de las nuevas APIs de Play.

Si necesitas utilizar controladores estáticos, puedes cambiar al generador de rutas estáticas añadiendo la siguiente configuración a tu build.sbt.

```scala
routesGenerator := StaticRoutesGenerator
```

Los ejemplos de código en la documentación de Play asumen que estás usando el generador de rutas inyectadas. Si no lo estás utilizando, puedes adaptar trivialmente los ejemplos de código para el generador de rutas estáticas, ya sea anteponiendo a la parte de invocación del controlador de la ruta un símbolo @, o declarando cada uno de tus controladores como un objeto en lugar de una clase.

### La sintaxis del archivo de rutas

conf/routes es el fichero de configuración utilizado por el router. Este fichero lista todas las rutas que necesita la aplicación. Cada ruta consiste en un método HTTP y un patrón URI, ambos asociados con una llamada a un generador de Acciones.

Veamos cómo es la definición de una ruta:

```scala
GET   /clients/:id          controllers.Clients.show(id: Long)
```

Cada ruta comienza con el método HTTP, seguido del patrón URI. El último elemento es la definición de la llamada.

También puede añadir comentarios al archivo de ruta, con el carácter #.

```scala
# Display a client.
GET   /clients/:id          controllers.Clients.show(id: Long)
```

Puede indicar al archivo de rutas que utilice un enrutador diferente bajo un prefijo específico utilizando "->" seguido del prefijo dado:

```scala
->      /api                        api.MyRouter
```

Esto es especialmente útil cuando se combina con String Interpolating Routing DSL también conocido como enrutamiento SIRD, o cuando se trabaja con subproyectos que enrutan utilizando varios archivos de rutas.

También es posible aplicar modificadores precediendo la ruta con una línea que empiece por +. Esto puede cambiar el comportamiento de ciertos componentes de Play. Uno de estos modificadores es el modificador "nocsrf" para eludir el filtro CSRF:

```scala
+ nocsrf
POST  /api/new              controllers.Api.newThing
```

### The HTTP method

El método HTTP puede ser cualquiera de los métodos válidos soportados por HTTP (GET, PATCH, POST, PUT, DELETE, HEAD).

#### The URI pattern

El patrón URI define la ruta de solicitud de la ruta. Algunas partes de la ruta de solicitud pueden ser dinámicas.

##### Static path

Por ejemplo, para que coincida exactamente con las solicitudes entrantes GET /clients/all, puede definir esta ruta:

```scala
GET   /clients/all          controllers.Clients.list()
```

##### Dynamic parts

Si desea definir una ruta que recupere un cliente por ID, tendrá que añadir una parte dinámica:

```scala
GET   /clients/:id          controllers.Clients.show(id: Long)
```

La estrategia de coincidencia por defecto para una parte dinámica está definida por la expresión regular [^/]+, lo que significa que cualquier parte dinámica definida como :id coincidirá exactamente con un segmento de ruta URI. A diferencia de otros tipos de patrones, los segmentos de ruta se decodifican automáticamente como URI en la ruta, antes de pasarlos a su controlador, y se codifican en la ruta inversa.

##### Partes dinámicas que abarcan varios

Si desea que una parte dinámica capture más de un segmento de ruta URI, separados por barras diagonales, puede definir una parte dinámica utilizando la sintaxis *id, también conocida como patrón comodín, que utiliza la expresión regular .*:

```conf
GET   /files/*name          controllers.Application.download(name)
```

Aquí para una petición como GET /files/images/logo.png, la parte dinámica name capturará el valor images/logo.png.

Tenga en cuenta que las partes dinámicas que abarcan varios / no son decodificadas por el enrutador ni codificadas por el enrutador inverso. Es su responsabilidad validar el segmento URI en bruto como lo haría para cualquier entrada de usuario. El enrutador inverso simplemente realiza una concatenación de cadenas, por lo que deberá asegurarse de que la ruta resultante es válida y no contiene, por ejemplo, varias barras inclinadas o caracteres no ASCII.

##### Partes dinámicas con expresiones regulares personalizadas

También puede definir su propia expresión regular para la parte dinámica, utilizando la sintaxis `$id<regex>`:

```conf
GET   /items/$id<[0-9]+>    controllers.Items.show(id: Long)
```

Al igual que con las rutas comodín, el parámetro no es decodificado por el enrutador ni codificado por el enrutador inverso. Eres responsable de validar la entrada para asegurarte de que tiene sentido en ese contexto.

##### Llamada al método generador de acciones

La última parte de la definición de una ruta es la llamada. Esta parte debe definir una llamada válida a un método que devuelva un valor play.api.mvc.Action, que normalmente será un método de acción del controlador.

Si el método no define ningún parámetro, basta con indicar el nombre completo del método:

```conf
GET   /                     controllers.Application.homePage()
```

Si el método de acción define algunos parámetros, todos los valores de estos parámetros se buscarán en la URI de la solicitud, ya sea extraídos de la propia ruta URI o de la cadena de consulta.

```scala
#  Extrae el parámetro de página de la ruta.
GET   /:page                controllers.Application.show(page)
```

O:

```scala
# Extrae el parámetro de página de la cadena de consulta.
GET   /                     controllers.Application.show(page)
```

Aquí está la correspondiente definición del método show en el controlador controllers.Application:

```scala
def show(page: String) = Action {
  loadContentFromDatabase(page)
    .map { htmlContent =>
      Ok(htmlContent).as("text/html")
    }
    .getOrElse(NotFound)
}
```

#### Tipos de parámetros

Para parámetros de tipo String, escribir el parámetro es opcional. Si quieres que Play transforme el parámetro entrante en un tipo Scala específico, puedes escribir explícitamente el parámetro:

```scala
GET   /clients/:id          controllers.Clients.show(id: Long)
```

Y haga lo mismo en la definición del método show correspondiente en el controlador controllers.Clients:

```scala
def show(id: Long) = Action {
  Client
    .findById(id)
    .map { client =>
      Ok(views.html.Clients.display(client))
    }
    .getOrElse(NotFound)
}
```

#### Parámetros con valores fijos

A veces querrá utilizar un valor fijo para un parámetro:

```scala
# Extraer el parámetro de página de la ruta, o fijar el valor para /
GET   /                     controllers.Application.show(page = "home")
GET   /:page                controllers.Application.show(page)
```

#### Parámetros con valores por defecto

También puede proporcionar un valor predeterminado que se utilizará si no se encuentra ningún valor en la solicitud entrante:

```scala
# Enlaces de paginación, como /clients?page=3
GET   /clients              controllers.Clients.list(page: Int ?= 1)
```

#### Parámetros opcionales

También puede especificar un parámetro opcional que no es necesario que esté presente en todas las solicitudes:

```scala
# El parámetro de versión es opcional. Por ejemplo, /api/list-all?version=3.0
GET   /api/list-all         controllers.Api.list(version: Option[String])
```

### Prioridad de enrutamiento

Varias rutas pueden coincidir con la misma solicitud. Si hay un conflicto, se utiliza la primera ruta (en orden de declaración).

### Enrutamiento inverso

El enrutador también se puede utilizar para generar una URL desde dentro de una llamada Scala. Esto hace que sea posible centralizar todos sus patrones URI en un único archivo de configuración, por lo que puede estar más seguro al refactorizar su aplicación.

Para cada controlador utilizado en el archivo de rutas, el enrutador generará un 'controlador inverso' en el paquete de rutas, teniendo los mismos métodos de acción, con la misma firma, pero devolviendo un play.api.mvc.Call en lugar de un play.api.mvc.Action.

El play.api.mvc.Call define una llamada HTTP, y proporciona tanto el método HTTP como el URI.

Por ejemplo, si crea un controlador como:

```scala
package controllers

  import javax.inject.Inject

  import play.api._
  import play.api.mvc._

  class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    def hello(name: String) = Action {
      Ok("Hello " + name + "!")
    }

  }
```

Y si lo mapeas en el archivo conf/routes:

```scala
# Hello action
GET   /hello/:name          controllers.Application.hello(name)
```

A continuación, puede invertir la dirección URL al método de acción hola, utilizando el controlador inverso controllers.routes.Application:

```scala
// Redirect to /hello/Bob
def helloBob = Action {
  Redirect(routes.Application.hello("Bob"))
}
```

**Nota:** Hay un subpaquete de rutas para cada paquete de controladores. Así que la acción controllers.Application.hello se puede invertir a través de controllers.routes.Application.hello (siempre y cuando no haya otra ruta antes en el archivo de rutas que coincida con la ruta generada).

El método de acción inversa funciona de forma bastante sencilla: toma sus parámetros y los sustituye de nuevo en el patrón de ruta. En el caso de segmentos de ruta (:foo), el valor se codifica antes de realizar la sustitución. En el caso de patrones regex y comodín, la cadena se sustituye sin codificar, ya que el valor puede abarcar varios segmentos. Asegúrese de escapar esos componentes como desee al pasarlos a la ruta inversa, y evite pasar entrada de usuario no validada.

### Rutas relativas

Hay casos en los que puede ser útil devolver una ruta relativa en lugar de una absoluta. Las rutas devueltas por play.mvc.Call son siempre absolutas (comienzan con un /), lo que puede dar lugar a problemas cuando las solicitudes a su aplicación web son reescritas por proxies HTTP, balanceadores de carga y pasarelas API. Algunos ejemplos en los que sería útil utilizar una ruta relativa son:

- Alojar una aplicación detrás de una pasarela web que prefija todas las rutas con algo distinto de lo que está configurado en su archivo conf/routes, y arraiga su aplicación en una ruta que no está esperando.
- Al renderizar dinámicamente las hojas de estilo, es necesario que los enlaces a los activos sean relativos, ya que pueden acabar siendo servidos desde diferentes URL por una CDN.

Para poder generar una ruta relativa necesita saber a qué hacer relativa la ruta de destino (la ruta de inicio). La ruta de inicio se puede recuperar del RequestHeader actual. Por lo tanto, para generar una ruta relativa es necesario pasar el RequestHeader actual o la ruta de inicio como parámetro String.

Por ejemplo, dados los puntos finales del controlador como:

```scala
package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class Relative @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def helloview() = Action { implicit request =>
    Ok(views.html.hello("Bob"))
  }

  def hello(name: String) = Action {
    Ok(s"Hello $name!")
  }
}
```

`Nota: La petición actual se pasa a la plantilla de vista implícitamente declarando una petición implícita`

Y si lo mapeas en el archivo conf/routes:

```scala
GET     /foo/bar/hello              controllers.Relative.helloview
GET     /hello/:name                controllers.Relative.hello(name)
```

A continuación, puede definir rutas relativas utilizando el enrutador inverso como antes e incluir una llamada adicional a relative:

```scala
@(name: String)(implicit request: RequestHeader)

<h1>Hello @name</h1>

<a href="@routes.Relative.hello(name)">Absolute Link</a>
<a href="@routes.Relative.hello(name).relative">Relative Link</a>
```

`Nota: El Request pasado desde el controlador se convierte en un RequestHeader y se marca implícitamente en los parámetros de la vista. Entonces se pasa implícitamente a la llamada a relative`

Al solicitar /foo/bar/hello el HTML generado tendrá el siguiente aspecto:

```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Bob</title>
    </head>
    <body>
      <a href="/hello/Bob">Absolute Link</a>
      <a href="../../hello/Bob">Relative Link</a>
    </body>
</html>
```

### El controlador por defecto

Play incluye un controlador por defecto que proporciona un puñado de acciones útiles. Éstas pueden invocarse directamente desde el archivo de rutas:

```scala
# Redirecciona a https://www.playframework.com/ con 303 Ver otro
GET   /about      controllers.Default.redirect(to = "https://www.playframework.com/")

# Responde con 404 Not Found
GET /pedidos controllers.Default.notFound

# Responde con 500 Internal Server Error
GET   /clients    controllers.Default.error

# Responde con 501 No aplicado
GET   /posts      controllers.Default.todo
```

En este ejemplo, GET /about redirige a un sitio web externo, pero también es posible redirigir a otra acción (como /posts en el ejemplo anterior).

### Enrutamiento personalizado

Play proporciona un DSL para definir enrutadores embebidos llamado String Interpolating Routing DSL, o SIRD para abreviar. Este DSL tiene muchos usos, incluyendo la incrustación de un servidor Play ligero, proporcionando capacidades de enrutamiento personalizadas o más avanzadas a una aplicación Play normal, y simulando servicios REST para pruebas.

## Manipulación de resultados

### Cambiar el Content-Type por defecto

El tipo de contenido del resultado se infiere automáticamente del valor Scala que especifiques como cuerpo de la respuesta.

Por ejemplo:

```scala
val textResult = Ok("Hello World!")
```

Establecerá automáticamente el encabezado Content-Type en text/plain, mientras que:
  
```scala
val xmlResult = Ok(<message>Hello World!</message>)
```

establecerá el encabezado Content-Type en application/xml.

`Consejo: esto se hace a través de la clase de tipo play.api.http.ContentTypeOf.`

Esto es bastante útil, pero a veces quieres cambiarlo. Basta con utilizar el método as(newContentType) en un resultado para crear un nuevo resultado similar con una cabecera Content-Type diferente:

```scala
val htmlResult = Ok(<h1>Hello World!</h1>).as("text/html")
```

o incluso mejor, usando:

```scala
val htmlResult2 = Ok(<h1>Hello World!</h1>).as(HTML)
```

`Nota: La ventaja de utilizar HTML en lugar de "text/html" es que el conjunto de caracteres se gestionará automáticamente y la cabecera Content-Type se establecerá en text/html; charset=utf-8. Lo veremos más adelante.`

### Manipulación de cabeceras HTTP

También puede añadir (o actualizar) cualquier cabecera HTTP al resultado:

```scala
val result = Ok("Hello World!").withHeaders(CACHE_CONTROL -> "max-age=3600", ETAG -> "xx")
```

Tenga en cuenta que al establecer un encabezado HTTP se descartará automáticamente el valor anterior si existía en el resultado original.

### Instalación y eliminación de cookies

Las cookies son sólo una forma especial de cabeceras HTTP, pero proporcionamos un conjunto de ayudantes para hacerlo más fácil.

Puedes añadir fácilmente una Cookie a la respuesta HTTP usando:

```scala
val result = Ok("Hello world")
  .withCookies(Cookie("theme", "blue"))
  .bakeCookies()
```

También, para descartar una Cookie previamente almacenada en el navegador Web:

```scala
val result2 = result.discardingCookies(DiscardingCookie("theme"))
```

También puede establecer y eliminar cookies como parte de la misma respuesta:

```scala
val result3 = result.withCookies(Cookie("theme", "blue")).discardingCookies(DiscardingCookie("skin"))
```

### Cambio del conjunto de caracteres para respuestas HTTP basadas en texto

Para respuestas HTTP basadas en texto es muy importante manejar el conjunto de caracteres correctamente. Play lo hace por ti y usa utf-8 por defecto (ver por qué usar utf-8).

El conjunto de caracteres se utiliza tanto para convertir la respuesta de texto en los bytes correspondientes para enviar a través del socket de red, como para actualizar la cabecera Content-Type con la extensión adecuada ;charset=xxx.

El conjunto de caracteres se gestiona automáticamente a través de la clase de tipo play.api.mvc.Codec. Basta con importar una instancia implícita de play.api.mvc.Codec en el ámbito actual para cambiar el conjunto de caracteres que utilizarán todas las operaciones:

```scala
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val myCustomCharset = Codec.javaSupported("iso-8859-1")

  def index = Action {
    Ok(<h1>Hello World!</h1>).as(HTML)
  }

}
```

Aquí, como hay un valor charset implícito en el ámbito, será utilizado tanto por el método Ok(...) para convertir el mensaje XML en bytes codificados ISO-8859-1 como para generar la cabecera text/html; charset=iso-8859-1 Content-Type.

Ahora bien, si te estás preguntando cómo funciona el método HTML, aquí tienes cómo está definido:

```scala
def HTML(implicit codec: Codec) = {
  "text/html; charset=" + codec.charset
}
```

Puede hacer lo mismo en su API si necesita manejar el conjunto de caracteres de forma genérica.

## Action composition

### Creadores de acciones personalizadas

Anteriormente vimos que hay múltiples formas de declarar una acción - con un parámetro de petición, sin un parámetro de petición, con un parser de cuerpo, etc. De hecho hay más que esto, como veremos en el capítulo sobre programación asíncrona.

Todos estos métodos para construir acciones están definidos por un trait llamado ActionBuilder y el objeto Action que usamos para declarar nuestras acciones es sólo una instancia de este trait. Implementando tu propio ActionBuilder, puedes declarar pilas de acciones reutilizables, que luego se pueden utilizar para construir acciones.

Comencemos con el ejemplo simple de un decorador de registro, queremos registrar cada llamada a esta acción.

La primera forma es implementar esta funcionalidad en el método invokeBlock, que es llamado para cada acción construida por el ActionBuilder:

```scala
import play.api.mvc._

class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
    extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    Logger.info("Calling action")
    block(request)
  }
}
```

Ahora podemos usar Inyección de Dependencia en tu controlador para obtener una instancia de LoggingAction y usarla de la misma forma que usamos Action:

```scala
class MyController @Inject()(loggingAction: LoggingAction, cc: ControllerComponents)
    extends AbstractController(cc) {
  def index = loggingAction {
    Ok("Hello World")
  }
}
```

Dado que ActionBuilder proporciona todos los diferentes métodos de construcción de acciones, esto también funciona con, por ejemplo, la declaración de un analizador de cuerpo personalizado:

```scala
def submit = loggingAction(parse.text) { request =>
  Ok("Got a body " + request.body.length + " bytes long")
}
```

### Acciones de composición

En la mayoría de las aplicaciones, querremos tener múltiples constructores de acciones, algunos que hagan diferentes tipos de autenticación, otros que proporcionen diferentes tipos de funcionalidad genérica, etc. En cuyo caso, no querremos reescribir nuestro código de acción de registro para cada tipo de constructor de acciones, sino que querremos definirlo de forma reutilizable.

El código de acción reutilizable puede ser implementado envolviendo acciones:

```scala
import play.api.mvc._

case class Logging[A](action: Action[A]) extends Action[A] {

  def apply(request: Request[A]): Future[Result] = {
    Logger.info("Calling action")
    action(request)
  }

  override def parser           = action.parser
  override def executionContext = action.executionContext
}
```

También podemos utilizar el constructor de acciones Action para construir acciones sin definir nuestra propia clase de acción:
  
```scala
import play.api.mvc._

def logging[A](action: Action[A]) = Action.async(action.parser) { request =>
  Logger.info("Calling action")
  action(request)
}
```

Las acciones pueden mezclarse en los constructores de acciones utilizando el método composeAction:

```scala
class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
    extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    block(request)
  }
  override def composeAction[A](action: Action[A]) = new Logging(action)
}
```

Ahora el constructor puede utilizarse de la misma forma que antes:

```scala
def index = loggingAction {
  Ok("Hello World")
}
```

También podemos mezclar acciones envolventes sin el constructor de acciones:
  
```scala
def index = Logging {
  Action {
    Ok("Hello World")
  }
}
```

### Acciones más complicadas

Hasta ahora sólo hemos mostrado acciones que no afectan en absoluto a la petición. Por supuesto, también podemos leer y modificar el objeto de solicitud entrante:

```scala
import play.api.mvc._
import play.api.mvc.request.RemoteConnection

def xForwardedFor[A](action: Action[A]) = Action.async(action.parser) { request =>
  val newRequest = request.headers.get("X-Forwarded-For") match {
    case None => request
    case Some(xff) =>
      val xffConnection = RemoteConnection(xff, request.connection.secure, None)
      request.withConnection(xffConnection)
  }
  action(newRequest)
}
```

Podríamos bloquear la solicitud:

```scala
import play.api.mvc._
import play.api.mvc.Results._

def onlyHttps[A](action: Action[A]) = Action.async(action.parser) { request =>
  request.headers
    .get("X-Forwarded-Proto")
    .collect {
      case "https" => action(request)
    }
    .getOrElse {
      Future.successful(Forbidden("Only HTTPS requests allowed"))
    }
}
```

Y, por último, también podemos modificar el resultado devuelto:

```scala
import play.api.mvc._

def addUaHeader[A](action: Action[A]) = Action.async(action.parser) { request =>
  action(request).map(_.withHeaders("X-UA-Compatible" -> "Chrome=1"))
}
```

### Diferentes tipos de solicitudes

Aunque la composición de acciones permite realizar procesamientos adicionales en el nivel de solicitud y respuesta HTTP, a menudo se desea crear cadenas de transformaciones de datos que añadan contexto o realicen validaciones en la propia solicitud. ActionFunction puede considerarse como una función sobre la solicitud, parametrizada sobre el tipo de solicitud de entrada y el tipo de salida que se pasa a la siguiente capa. Cada función de acción puede representar un procesamiento modular, como la autenticación, la búsqueda de objetos en la base de datos, la comprobación de permisos u otras operaciones que desee componer y reutilizar a través de las acciones.

Hay algunos rasgos predefinidos que implementan ActionFunction y que son útiles para diferentes tipos de procesamiento:

- ActionTransformer puede modificar la solicitud, por ejemplo añadiendo información adicional.
- ActionFilter puede interceptar peticiones selectivamente, por ejemplo para producir errores, sin cambiar el valor de la petición.
- ActionRefiner es el caso general de los dos anteriores.
- ActionBuilder es el caso especial de las funciones que toman Request como entrada, y por lo tanto pueden construir acciones.

También puede definir su propia ActionFunction arbitraria implementando el método invokeBlock. A menudo es conveniente hacer que los tipos de entrada y salida sean instancias de Request (usando WrappedRequest), pero esto no es estrictamente necesario.

#### Authentication

Uno de los casos de uso más comunes para las funciones de acción es la autenticación. Podemos implementar fácilmente nuestro propio transformador de acción de autenticación que determina el usuario a partir de la petición original y lo añade a una nueva UserRequest. Nótese que esto también es un ActionBuilder porque toma una simple Request como entrada:

```scala
import play.api.mvc._

class UserRequest[A](val username: Option[String], request: Request[A]) extends WrappedRequest[A](request)

class UserAction @Inject()(val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)
    extends ActionBuilder[UserRequest, AnyContent]
    with ActionTransformer[Request, UserRequest] {
  def transform[A](request: Request[A]) = Future.successful {
    new UserRequest(request.session.get("username"), request)
  }
}
```

Play también proporciona un creador de acciones de autenticación integrado. Aquí encontrará información sobre cómo utilizarlo.

**Nota:** El constructor de acciones de autenticación incorporado es sólo un ayudante práctico para minimizar el código necesario para implementar la autenticación en casos sencillos, su implementación es muy similar al ejemplo anterior.

Dado que es sencillo escribir tu propio ayudante de autenticación, te recomendamos que lo hagas si el ayudante incorporado no se adapta a tus necesidades.

#### Añadir información a las solicitudes

Consideremos ahora una API REST que trabaja con objetos de tipo Item. Puede haber muchas rutas bajo la ruta `/item/:itemId`, y cada una de ellas necesita buscar el elemento. En este caso, puede ser útil poner esta lógica en una función de acción.

En primer lugar, crearemos un objeto request que añada un Item a nuestro UserRequest:

```scala
import play.api.mvc._

class ItemRequest[A](val item: Item, request: UserRequest[A]) extends WrappedRequest[A](request) {
  def username = request.username
}
```

Ahora crearemos un refinador de acción que busque ese elemento y devuelva un error (Izquierda) o un nuevo ItemRequest (Derecha). Tenga en cuenta que este refinador de acción se define dentro de un método que toma el id del elemento:

```scala
def ItemAction(itemId: String)(implicit ec: ExecutionContext) = new ActionRefiner[UserRequest, ItemRequest] {
  def executionContext = ec
  def refine[A](input: UserRequest[A]) = Future.successful {
    ItemDao
      .findById(itemId)
      .map(new ItemRequest(_, input))
      .toRight(NotFound)
  }
}
```

#### Validación de solicitudes

Por último, es posible que queramos una función de acción que valide si una solicitud debe continuar. Por ejemplo, tal vez queremos comprobar si el usuario de UserAction tiene permiso para acceder al elemento de ItemAction, y si no devolver un error:

```scala
def PermissionCheckAction(implicit ec: ExecutionContext) = new ActionFilter[ItemRequest] {
  def executionContext = ec
  def filter[A](input: ItemRequest[A]) = Future.successful {
    if (!input.item.accessibleByUser(input.username))
      Some(Forbidden)
    else
      None
  }
}
```

#### Ponerlo todo junto

Ahora podemos encadenar estas funciones de acción (comenzando con un ActionBuilder) usando andThen para crear una acción:

```scala
def showItem(itemId: String) = (UserAction andThen ItemAction(itemId) andThen PermissionCheckAction) { request =>
  Ok("Got item " + request.item)
}

def tagItem(itemId: String, tag: String)(implicit ec: ExecutionContext) =
  userAction.andThen(ItemAction(itemId)).andThen(PermissionCheckAction) { request =>
    request.item.addTag(tag)
    Ok("User " + request.username + " tagged " + request.item.id)
  }

```

Play también proporciona una API de filtro global, que resulta útil para cuestiones transversales globales.

Crear un ActionsComposition que valide la autenticación de la siguiente forma, si en el request llegar un header con el nombre tkn continuará al action de lo contrario retornará un http 403, se hace de la siguiente manera:

```scala
import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}


class AuthAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    request.headers.get("tkn") match {
      case Some(tkn) => block(request)
      case None => Future.successful(Results.Forbidden)
    }
  }
}
```

### Tratamiento de errores

Hay dos tipos principales de errores que puede devolver una aplicación HTTP: errores del cliente y errores del servidor. Los errores del cliente indican que el cliente que se conecta ha hecho algo mal, los errores del servidor indican que hay algo mal en el servidor.

En muchas circunstancias, Play detectará automáticamente los errores del cliente, entre los que se incluyen errores como valores de cabecera malformados, tipos de contenido no soportados y peticiones de recursos que no se pueden encontrar. En muchas circunstancias, Play también gestionará automáticamente los errores del servidor: si tu código de acción lanza una excepción, Play la detectará y generará una página de error del servidor para enviarla al cliente.

La interfaz a través de la cual Play maneja estos errores es HttpErrorHandler. Define dos métodos, onClientError, y onServerError.

### Suministro de un gestor de errores personalizado

Si está utilizando BuiltInComponents para construir su aplicación, anule el método httpErrorHandler para devolver una instancia de su controlador personalizado.

Si utiliza inyección de dependencias en tiempo de ejecución (por ejemplo, Guice), el controlador de errores puede cargarse dinámicamente en tiempo de ejecución. La forma más sencilla es crear una clase en el paquete raíz llamada ErrorHandler que implemente HttpErrorHandler, por ejemplo:

```scala
import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent._
import javax.inject.Singleton

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }
}
```

Si no quieres colocar tu manejador de errores en el paquete raíz, o si quieres poder configurar diferentes manejadores de errores para diferentes entornos, puedes hacerlo configurando la propiedad de configuración play.http.errorHandler en application.conf:

```conf
play.http.errorHandler = "com.example.ErrorHandler"
```

### Ampliación del gestor de errores por defecto

El gestor de errores predeterminado de Play proporciona una gran cantidad de funciones útiles. Por ejemplo, en modo de desarrollo, cuando se produce un error de servidor, Play intentará localizar y mostrar el fragmento de código de tu aplicación que causó la excepción, para que puedas ver e identificar rápidamente el problema. Puede que quieras proporcionar errores de servidor personalizados en producción, mientras mantienes esa funcionalidad en desarrollo. Para facilitar esto, Play proporciona un DefaultHttpErrorHandler que tiene algunos métodos convenientes que puedes sobreescribir para que puedas mezclar tu lógica personalizada con el comportamiento existente de Play.

Por ejemplo, para proporcionar sólo un mensaje de error de servidor personalizado en producción, dejando intacto el mensaje de error de desarrollo, y también quería proporcionar una página de error prohibida específica:

```scala
import javax.inject._

import play.api.http.DefaultHttpErrorHandler
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.routing.Router
import scala.concurrent._

@Singleton
class ErrorHandler @Inject()(
    env: Environment,
    config: Configuration,
    sourceMapper: OptionalSourceMapper,
    router: Provider[Router]
) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override def onProdServerError(request: RequestHeader, exception: UsefulException) = {
    Future.successful(
      InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }

  override def onForbidden(request: RequestHeader, message: String) = {
    Future.successful(
      Forbidden("You're not allowed to access this resource.")
    )
  }
}
```

Consulta la documentación completa de la API de DefaultHttpErrorHandler para ver qué métodos están disponibles y cómo puedes aprovecharlos.

## Gestión de resultados asíncronos

### Hacer que los controladores sean asíncronos

Internamente, Play Framework es asíncrono de abajo hacia arriba. Play gestiona todas las solicitudes de forma asíncrona y no bloqueante.

La configuración por defecto está ajustada para controladores asíncronos. En otras palabras, el código de la aplicación debe evitar el bloqueo en los controladores, es decir, hacer que el código del controlador espere una operación. Ejemplos comunes de este tipo de operaciones de bloqueo son las llamadas JDBC, streaming API, peticiones HTTP y cálculos largos.

Aunque es posible incrementar el número de hilos en el contexto de ejecución por defecto para permitir que más peticiones concurrentes sean procesadas por controladores bloqueantes, seguir el enfoque recomendado de mantener los controladores asíncronos hace más fácil escalar y mantener la respuesta del sistema bajo carga.

### Crear acciones no bloqueantes

Debido a la forma en que funciona Play, el código de acción debe ser lo más rápido posible, es decir, no bloquearse. Entonces, ¿qué debemos devolver como resultado si aún no somos capaces de generarlo? La respuesta es un resultado futuro.

Un Futuro[Resultado] acabará siendo redimido con un valor de tipo Resultado. Al dar un Future[Result] en lugar de un Result normal, podemos generar rápidamente el resultado sin bloquearnos. Play entonces servirá el resultado tan pronto como la promesa sea redimida.

El cliente web se bloqueará mientras espera la respuesta, pero no se bloqueará nada en el servidor, y los recursos del servidor podrán utilizarse para servir a otros clientes.

Sin embargo, utilizar un Futuro es sólo la mitad de la cuestión. Si estás llamando a una API bloqueante como JDBC, entonces necesitarás que tu ExecutionStage se ejecute con un ejecutor diferente, para sacarlo del grupo de hilos de renderizado de Play. Puedes hacerlo creando una subclase de play.api.libs.concurrent.CustomExecutionContext con una referencia al despachador personalizado.

```scala
import play.api.libs.concurrent.CustomExecutionContext

// Asegúrate de enlazar la nueva clase de contexto a este trait usando una de las técnicas de enlace personalizadas
// personalizadas listadas en la página de documentación "Scala Dependency Injection
trait MyExecutionContext extends ExecutionContext

class MyExecutionContextImpl @Inject()(system: ActorSystem)
    extends CustomExecutionContext(system, "my.executor")
    with MyExecutionContext

class HomeController @Inject()(myExecutionContext: MyExecutionContext, val controllerComponents: ControllerComponents)
    extends BaseController {
  def index = Action.async {
    Future {
      // Llama a alguna API de bloqueo
      Ok("result of blocking call")
    }(myExecutionContext)
  }
}
```

Consulte ThreadPools para obtener más información sobre el uso eficaz de contextos de ejecución personalizados.

### Cómo crear un Futuro[Result]

Para crear un Futuro[Resultado] necesitamos primero otro futuro: el futuro que nos dará el valor real que necesitamos para calcular el resultado:

```scala
val futurePIValue: Future[Double] = computePIAsynchronously()
val futureResult: Future[Result] = futurePIValue.map { pi =>
  Ok("PI value computed: " + pi)
}
```

Todas las llamadas asíncronas de la API de Play te dan un Futuro. Este es el caso tanto si estás llamando a un servicio web externo usando la API play.api.libs.WS, como si estás usando Akka para programar tareas asíncronas o para comunicarte con actores usando play.api.libs.Akka.

He aquí una forma sencilla de ejecutar un bloque de código de forma asíncrona y obtener un Futuro:

```scala
val futureInt: Future[Int] = scala.concurrent.Future {
  intensiveComputation()
}
```

**Nota:** Es importante entender en qué hilo se ejecuta el código con los futuros. En los dos bloques de código anteriores, hay una importación en el contexto de ejecución predeterminado de Plays. Este es un parámetro implícito que se pasa a todos los métodos de la API de futuros que aceptan callbacks. El contexto de ejecución será a menudo equivalente a un grupo de hilos, aunque no necesariamente.

No se puede convertir por arte de magia la IO síncrona en asíncrona envolviéndola en un Future. Si no puedes cambiar la arquitectura de la aplicación para evitar operaciones de bloqueo, en algún momento esa operación tendrá que ser ejecutada, y ese hilo se va a bloquear. Así que además de encerrar la operación en un Futuro, es necesario configurarla para que se ejecute en un contexto de ejecución separado que haya sido configurado con suficientes hilos para manejar la concurrencia esperada. Consulta Understanding Play thread pools para obtener más información, y descarga las plantillas de ejemplo de play que muestran la integración de bases de datos.

También puede ser útil utilizar Actores para operaciones de bloqueo. Los Actores proporcionan un modelo limpio para manejar tiempos de espera y fallos, establecer contextos de ejecución de bloqueo y gestionar cualquier estado que pueda estar asociado al servicio. Además, los Actores proporcionan patrones como ScatterGatherFirstCompletedRouter para tratar peticiones simultáneas de caché y base de datos y permitir la ejecución remota en un cluster de servidores backend. Pero un Actor puede ser excesivo dependiendo de lo que necesites.

### Futuros de retorno

Mientras que hasta ahora utilizábamos el método constructor Action.apply para construir acciones, para enviar un resultado asíncrono necesitamos utilizar el método constructor Action.async:

```scala
def index = Action.async {
  val futureInt = scala.concurrent.Future { intensiveComputation() }
  futureInt.map(i => Ok("Got result: " + i))
}
```

### Las acciones son asíncronas por defecto

Las acciones de reproducción son asíncronas por defecto. Por ejemplo, en el código del controlador de abajo, la parte { Ok(...) } del código no es el cuerpo del método del controlador. Es una función anónima que se pasa al método apply del objeto Action, que crea un objeto de tipo Action. Internamente, la función anónima que escribiste será llamada y su resultado será encerrado en un Future.

```scala
def echo = Action { request =>
  Ok("Got request [" + request + "]")
}
```

**Nota:** Tanto Action.apply como Action.async crean objetos Action que se manejan internamente de la misma manera. Existe un único tipo de Acción, que es asíncrona, y no dos tipos (una síncrona y otra asíncrona). El constructor .async es sólo una facilidad para simplificar la creación de acciones basadas en APIs que devuelven un Futuro, lo que facilita la escritura de código no bloqueante.

### Gestión de los tiempos muertos

A menudo es útil manejar los tiempos de espera adecuadamente, para evitar que el navegador web se bloquee y espere si algo va mal. Puedes usar play.api.libs.concurrent.Futures para envolver un Future en un timeout no bloqueante.

```scala
import scala.concurrent.duration._
import play.api.libs.concurrent.Futures._

def index = Action.async {
  // Necesitarás un Futures implícito para withTimeout() -- normalmente lo consigues
  // inyectándolo en el constructor de tu controlador
  intensiveComputation()
    .withTimeout(1.seconds)
    .map { i =>
      Ok("Got result: " + i)
    }
    .recover {
      case e: scala.concurrent.TimeoutException =>
        InternalServerError("timeout")
    }
}
```

**Nota:** El tiempo de espera no es lo mismo que la cancelación - incluso en caso de tiempo de espera, el futuro dado todavía se completará, a pesar de que el valor completado no se devuelve.

## Comprender los grupos de hilos de Play

Play Framework es, desde la base, un framework web asíncrono. Los flujos se gestionan de forma asíncrona utilizando iteradores. Los grupos de hilos en Play están ajustados para utilizar menos hilos que en los marcos web tradicionales, ya que la IO en play-core nunca se bloquea.

Debido a esto, si planeas escribir código IO bloqueante, o código que potencialmente podría hacer mucho trabajo intensivo de CPU, necesitas saber exactamente qué grupo de hilos está soportando esa carga de trabajo, y necesitas ajustarlo en consecuencia. Hacer IO de bloqueo sin tener esto en cuenta es probable que resulte en un rendimiento muy pobre de Play Framework, por ejemplo, usted puede ver sólo unas pocas peticiones por segundo que se manejan, mientras que el uso de la CPU se sitúa en el 5%. En comparación, los puntos de referencia en el hardware de desarrollo típico (por ejemplo, un MacBook Pro) han demostrado que Play es capaz de manejar cargas de trabajo en los cientos o incluso miles de solicitudes por segundo sin sudar cuando se ajusta correctamente.

### Saber cuándo se está bloqueando

El lugar más común donde una aplicación Play típica se bloqueará es cuando esté hablando con una base de datos. Desafortunadamente, ninguna de las principales bases de datos proporciona controladores de base de datos asíncronos para la JVM, por lo que para la mayoría de las bases de datos, tu única opción es utilizar IO de bloqueo. Una notable excepción es ReactiveMongo, un controlador para MongoDB que utiliza la librería Iteratee de Play para comunicarse con MongoDB.

Otros casos en los que tu código puede bloquearse incluyen:

- Utilizar API REST/WebService a través de una biblioteca cliente de terceros (es decir, no utilizar la API WS asíncrona de Play).
- Algunas tecnologías de mensajería sólo proporcionan API síncronas para enviar mensajes
- Cuando se abren archivos o sockets directamente
- Operaciones intensivas de la CPU que se bloquean por el hecho de que tardan mucho tiempo en ejecutarse

En general, si la API que está utilizando devuelve Futuros, es no bloqueante, de lo contrario es bloqueante.

Ten en cuenta que puedes tener la tentación de envolver tu código bloqueante en Futuros. Esto no lo hace no-bloqueante, sólo significa que el bloqueo se producirá en un hilo diferente. Todavía necesitas asegurarte de que el pool de hilos que estás usando tiene suficientes hilos para manejar el bloqueo. Consulta las plantillas de ejemplo de Play en https://playframework.com/download#examples para saber cómo configurar tu aplicación para una API de bloqueo.

Por el contrario, los siguientes tipos de E/S no se bloquean:

- La API Play WS
- Controladores de base de datos asíncronos como ReactiveMongo
- Envío/recepción de mensajes a/desde actores Akka

### Hilo de piscinas de Play

Play utiliza varios grupos de hilos para distintos fines:

- Thread pools internos - Estos son usados internamente por el motor del servidor para manejar IO. El código de una aplicación nunca debería ser ejecutado por un hilo en estos thread pools. Play está configurado con el servidor Akka HTTP por defecto, por lo que los ajustes de configuración de application.conf deben ser utilizados para cambiar el backend. Alternativamente, Play también viene con un servidor Netty que, si está habilitado, también tiene opciones que se pueden configurar desde application.conf.

- Play default thread pool - Este es el thread pool en el que se ejecuta todo el código de tu aplicación en Play Framework. Es un despachador Akka, y es utilizado por el ActorSystem de la aplicación. Se puede configurar mediante la configuración de Akka, que se describe a continuación.

### Utilizar el grupo de hilos por defecto

Todas las acciones en Play Framework utilizan el grupo de hilos por defecto. Cuando se realizan ciertas operaciones asíncronas, por ejemplo, llamar a map o flatMap en un futuro, puede ser necesario proporcionar un contexto de ejecución implícito para ejecutar las funciones dadas. Un contexto de ejecución es básicamente otro nombre para un ThreadPool.

En la mayoría de las situaciones, el contexto de ejecución apropiado a usar será el thread pool por defecto de Play. Esto es accesible a través de @Inject()(implicit ec: ExecutionContext) Esto puede ser usado inyectándolo en tu archivo fuente Scala:

```scala
class Samples @Inject()(components: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(components) {
  def someAsyncAction = Action.async {
    someCalculation()
      .map { result =>
        Ok(s"The answer is $result")
      }
      .recover {
        case e: TimeoutException =>
          InternalServerError("Calculation timed out!")
      }
  }

  def someCalculation(): Future[Int] = {
    Future.successful(42)
  }
}
```

o utilizando CompletionStage con un HttpExecutionContext en código Java:

```scala
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class MyController extends Controller {

  private HttpExecutionContext httpExecutionContext;

  @Inject
  public MyController(HttpExecutionContext ec) {
    this.httpExecutionContext = ec;
  }

  public CompletionStage<Result> index() {
    // Utilizar una tarea diferente con CE explícita
    return calculateResponse()
        .thenApplyAsync(
            answer -> {
              // uses Http.Context
              ctx().flash().put("info", "Response updated!");
              return ok("answer was " + answer);
            },
            httpExecutionContext.current());
  }

  private static CompletionStage<String> calculateResponse() {
    return CompletableFuture.completedFuture("42");
  }
}
```

Este contexto de ejecución se conecta directamente al ActorSystem de la Aplicación y utiliza el despachador por defecto.

### Configuración del grupo de hilos por defecto

El grupo de hilos por defecto se puede configurar utilizando la configuración estándar de Akka en application.conf bajo el espacio de nombres akka. Esta es la configuración predeterminada para el grupo de hilos de Play:

```conf
akka {
  actor {
    default-dispatcher {
      fork-join-executor {
        # Configurarlo a 1 en lugar de a 3 parece mejorar el rendimiento.
        parallelism-factor = 1.0

        # @richdougherty: No estoy seguro de por qué esto se establece por debajo de la Akka
        # por defecto.
        parallelism-max = 24

        # Establecer esto a LIFO cambia el ejecutor fork-join
        # a usar una disciplina de pila para la programación de tareas. Esto normalmente
        # mejora el rendimiento a costa de un posible aumento de la
        # latencia y el riesgo de que la tarea se muera de hambre (lo que debería ser raro).
        task-peeking-mode = LIFO
      }
    }
  }
}
```

Esta configuración ordena a Akka crear 1 hilo por procesador disponible, con un máximo de 24 hilos en el pool.

También puedes probar la configuración por defecto de Akka:

```conf
akka {
  actor {
    default-dispatcher {
      # Esto se utilizará si ha establecido "executor = "fork-join-executor""
      fork-join-executor {
        # Número mínimo de hilos para limitar el número de paralelismo basado en factores a
        parallelism-min = 8

        # El factor de paralelismo se utiliza para determinar el tamaño del pool de hilos mediante la
        # siguiente fórmula: ceil(procesadores disponibles * factor). El tamaño resultante
        # está limitado por los valores paralelismo-min y paralelismo-max.
        parallelism-factor = 3.0

        # Número máximo de hilos para limitar el número de paralelismo basado en factores a
        parallelism-max = 64

        # Configurando a "FIFO" para usar cola como modo peeking que "poll" o "LIFO" para usar pila
        # como el modo peeking que "pop".
        task-peeking-mode = "FIFO"
      }
    }
  }
}
```

### Utilización de otros repositorios de hilos

En determinadas circunstancias, es posible que desee enviar trabajo a otros grupos de hilos. Esto puede incluir trabajo pesado de CPU, o trabajo IO, como acceso a bases de datos. Para hacer esto, primero debes crear un ThreadPool, esto se puede hacer fácilmente en Scala:

```scala
val myExecutionContext: ExecutionContext = akkaSystem.dispatchers.lookup("my-context")
```

En este caso, estamos utilizando Akka para crear el ExecutionContext, pero también podrías crear fácilmente tus propios ExecutionContexts utilizando ejecutores Java, o el pool de hilos Scala fork join, por ejemplo. Play proporciona play.libs.concurrent.CustomExecutionContext y play.api.libs.concurrent.CustomExecutionContext que se pueden utilizar para crear tus propios contextos de ejecución. Por favor, consulta ScalaAsync o JavaAsync para más detalles.

Para configurar este contexto de ejecución Akka, puedes añadir la siguiente configuración a tu application.conf:

```conf
my-context {
  fork-join-executor {
    parallelism-factor = 20.0
    parallelism-max = 200
  }
}
```

Para utilizar este contexto de ejecución en Scala, basta con utilizar la función de objeto scala Future companion:

```scala
Future {
  // Algún bloqueo o código caro aquí
}(myExecutionContext)
```

o puedes utilizarlo implícitamente:

```scala
implicit val ec = myExecutionContext

Future {
  // Algún bloqueo o código caro aquí
}
```

Además, consulte las plantillas de ejemplo en https://playframework.com/download#examples para ver ejemplos de cómo configurar su aplicación para una API de bloqueo.

### Cargadores de clases e hilos locales

Los cargadores de clases y los hilos locales necesitan un manejo especial en un entorno multihilo como un programa Play.

#### Cargador de clases de aplicación

En una aplicación Play, el cargador de clases del contexto del hilo no siempre puede cargar las clases de la aplicación. Debe utilizar explícitamente el cargador de clases de la aplicación para cargar clases.

```scala
val myClass = app.classloader.loadClass(myClassName)
```

Ser explícito sobre la carga de clases es más importante cuando se ejecuta Play en modo de desarrollo (usando run) que en modo de producción. Esto se debe a que el modo de desarrollo de Play utiliza múltiples cargadores de clases para poder soportar la recarga automática de la aplicación. Algunos de los hilos de Play pueden estar vinculados a un cargador de clases que sólo conoce un subconjunto de las clases de tu aplicación.

En algunos casos es posible que no puedas utilizar explícitamente el cargador de clases de la aplicación. Esto ocurre a veces cuando se utilizan librerías de terceros. En este caso puede que necesites configurar el cargador de clases del contexto del hilo explícitamente antes de llamar al código de terceros. Si lo haces, recuerda restaurar el cargador de clases de contexto a su valor anterior una vez que hayas terminado de llamar al código de terceros.

#### Hilos Java locales

El código Java en Play utiliza un ThreadLocal para averiguar información contextual como la petición HTTP actual. El código Scala no necesita usar ThreadLocals porque puede usar parámetros implícitos para pasar contexto en su lugar. Los ThreadLocals se utilizan en Java para que el código Java pueda acceder a la información contextual sin necesidad de pasar parámetros de contexto en todas partes.

El problema con el uso de hilos locales, sin embargo, es que tan pronto como el control cambia a otro hilo, se pierde la información del hilo local. Por lo tanto, si se asigna un CompletionStage utilizando thenApplyAsync, o utilizando thenApply en un momento después de que el futuro asociado con ese CompletionStage se haya completado, y luego se intenta acceder al contexto HTTP (por ejemplo, la sesión o solicitud), no funcionará. Para solucionar esto, Play proporciona un HttpExecutionContext. Esto te permite capturar el contexto actual en un Ejecutor, que luego puedes pasar a los métodos *Async de CompletionStage como thenApplyAsync(), y cuando el ejecutor ejecute tu callback, se asegurará de que el contexto local del hilo esté configurado para que puedas acceder a los objetos request/session/flash/response.

Para utilizar el HttpExecutionContext, inyéctelo en su componente y, a continuación, pase el contexto actual cada vez que se interactúe con un CompletionStage. Por ejemplo:

```scala
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class MyController extends Controller {

  private HttpExecutionContext httpExecutionContext;

  @Inject
  public MyController(HttpExecutionContext ec) {
    this.httpExecutionContext = ec;
  }

  public CompletionStage<Result> index() {
    // Use a different task with explicit EC
    return calculateResponse()
        .thenApplyAsync(
            answer -> {
              // uses Http.Context
              ctx().flash().put("info", "Response updated!");
              return ok("answer was " + answer);
            },
            httpExecutionContext.current());
  }

  private static CompletionStage<String> calculateResponse() {
    return CompletableFuture.completedFuture("42");
  }
}
```

Si tienes un ejecutor personalizado, puedes envolverlo en un HttpExecutionContext simplemente pasándolo al constructor HttpExecutionContexts.

### Buenas Practicas

La mejor manera de dividir el trabajo en tu aplicación entre los diferentes grupos de hilos depende en gran medida de los tipos de trabajo que tu aplicación está haciendo, y el control que deseas tener sobre la cantidad de trabajo que se puede hacer en paralelo. No hay una solución única para el problema, y la mejor decisión para ti vendrá de entender los requisitos de bloqueo-IO de tu aplicación y las implicaciones que tienen en tus grupos de hilos. Puede ser útil realizar pruebas de carga en tu aplicación para ajustar y verificar tu configuración.

**Nota:** En un entorno de bloqueo, thread-pool-executor es mejor que fork-join porque no es posible el robo de trabajo, y se debe utilizar un tamaño de pool fijo y ajustado al tamaño máximo del recurso subyacente.

Dado que JDBC es bloqueante, los thread pools pueden dimensionarse al número de conexiones disponibles a un pool de base de datos, asumiendo que el thread pool se utiliza exclusivamente para el acceso a la base de datos. Menos hilos no consumirán el número de conexiones disponibles. Más hilos que el número de conexiones disponibles podría ser un desperdicio dada la contención por las conexiones.

A continuación se describen algunos perfiles comunes que la gente puede querer utilizar en Play Framework:

#### Asíncrono puro

En este caso, usted no está haciendo ningún bloqueo de IO en su aplicación. Dado que nunca estás bloqueando, la configuración por defecto de un hilo por procesador se adapta perfectamente a tu caso de uso, por lo que no es necesario realizar ninguna configuración adicional. El contexto de ejecución por defecto de Play se puede utilizar en todos los casos.

#### Altamente sincrónico

Este perfil coincide con el de un framework web tradicional basado en E/S síncrona, como un contenedor de servlets Java. Utiliza grandes grupos de hilos para manejar el bloqueo de E/S. Es útil para aplicaciones en las que la mayoría de las acciones son llamadas a bases de datos síncronas, como el acceso a una base de datos, y no se desea o no es necesario controlar la concurrencia para diferentes tipos de trabajo. Este perfil es el más simple para manejar IO bloqueante.

En este perfil, usarías el contexto de ejecución por defecto en todas partes, pero lo configurarías para tener un gran número de hilos en su pool. Dado que el pool de hilos por defecto se utiliza tanto para atender peticiones de Play como peticiones de base de datos, el tamaño fijo del pool debería ser el tamaño máximo del pool de conexiones de base de datos, más el número de núcleos, más un par extra para mantenimiento, así:

```conf
akka {
  actor {
    default-dispatcher {
      executor = "thread-pool-executor"
      throughput = 1
      thread-pool-executor {
        fixed-pool-size = 55 # db conn pool (50) + number of cores (4) + housekeeping (1)
      }
    }
  }
}
```

Este perfil se recomienda para aplicaciones Java que hacen IO síncrono, ya que es más difícil en Java para despachar el trabajo a otros hilos.

Además, consulte las plantillas de ejemplo en https://playframework.com/download#examples para ver ejemplos de cómo configurar su aplicación para una API de bloqueo.

#### Muchos grupos de hilos específicos

Este perfil es para cuando se quiere hacer un montón de IO síncrono, pero también desea controlar exactamente la cantidad de qué tipo de operaciones que su aplicación hace a la vez. En este perfil, sólo harías operaciones no bloqueantes en el contexto de ejecución por defecto, y luego despacharías operaciones bloqueantes a diferentes contextos de ejecución para esas operaciones específicas.

En este caso, podrías crear un número de diferentes contextos de ejecución para diferentes tipos de operaciones, así:

```scala
object Contexts {
  implicit val simpleDbLookups: ExecutionContext = akkaSystem.dispatchers.lookup("contexts.simple-db-lookups")
  implicit val expensiveDbLookups: ExecutionContext =
    akkaSystem.dispatchers.lookup("contexts.expensive-db-lookups")
  implicit val dbWriteOperations: ExecutionContext = akkaSystem.dispatchers.lookup("contexts.db-write-operations")
  implicit val expensiveCpuOperations: ExecutionContext =
    akkaSystem.dispatchers.lookup("contexts.expensive-cpu-operations")
}
```

Estas podrían configurarse así:

```conf
contexts {
  simple-db-lookups {
    executor = "thread-pool-executor"
    throughput = 1
    thread-pool-executor {
      fixed-pool-size = 20
    }
  }
  expensive-db-lookups {
    executor = "thread-pool-executor"
    throughput = 1
    thread-pool-executor {
      fixed-pool-size = 20
    }
  }
  db-write-operations {
    executor = "thread-pool-executor"
    throughput = 1
    thread-pool-executor {
      fixed-pool-size = 10
    }
  }
  expensive-cpu-operations {
    fork-join-executor {
      parallelism-max = 2
    }
  }
}
```

Entonces en tu código, crearías Futuros y pasarías el ExecutionContext relevante para el tipo de trabajo que ese Futuro estaba haciendo.

**Nota:** El espacio de nombres de configuración puede elegirse libremente, siempre que coincida con el ID del dispatcher pasado a app.actorSystem.dispatchers.lookup. La clase CustomExecutionContext lo hará automáticamente.

#### Pocos grupos de hilos específicos

Se trata de una combinación entre los numerosos grupos de hilos específicos y el perfil altamente sincronizado. La mayoría de las operaciones de E/S sencillas se realizarían en el contexto de ejecución predeterminado y el número de subprocesos sería razonablemente alto (por ejemplo, 100), pero algunas operaciones costosas se enviarían a contextos específicos, en los que se podría limitar el número de operaciones que se realizan a la vez.

### Depuración de Thread Pools

Hay muchas configuraciones posibles para un dispatcher, y puede ser difícil ver cuáles se han aplicado y cuáles son los valores por defecto, particularmente cuando se sobrescribe el dispatcher por defecto. La opción de configuración akka.log-config-on-start muestra toda la configuración aplicada cuando se carga la aplicación:

```scala
akka.log-config-on-start = on
```

Tenga en cuenta que debe tener Akka logging configurado a un nivel de depuración para ver la salida, por lo que debe añadir lo siguiente a logback.xml:

```xml
<logger name="akka" level="DEBUG" />
```

Una vez que veas la salida HOCON registrada, puedes copiarla y pegarla en un archivo "example.conf" y verla en IntelliJ IDEA, que soporta la sintaxis HOCON. Deberías ver tus cambios fusionados con el dispatcher de Akka, así que si anulas thread-pool-executor lo verás fusionado:

```conf
{ 
  # Elided HOCON... 
  "actor" : {
    "default-dispatcher" : {
      # application.conf @ file:/Users/wsargent/work/catapi/target/universal/stage/conf/application.conf: 19
      "executor" : "thread-pool-executor"
    }
  }
}
```

Tenga en cuenta también que Play tiene diferentes ajustes de configuración para el modo de desarrollo que para el de producción. Para asegurarse de que la configuración del grupo de hilos es correcta, debe ejecutar Play en una configuración de producción.

## Conceptos básicos de JSON

Las aplicaciones web modernas a menudo necesitan analizar y generar datos en formato JSON (JavaScript Object Notation). Play lo soporta a través de su biblioteca JSON.

JSON es un formato ligero de intercambio de datos con este aspecto:

```json
{
  "name" : "Watership Down",
  "location" : {
    "lat" : 51.235685,
    "long" : -1.309197
  },
  "residents" : [ {
    "name" : "Fiver",
    "age" : 4,
    "role" : null
  }, {
    "name" : "Bigwig",
    "age" : 6,
    "role" : "Owsla"
  } ]
}
```

### La biblioteca Play JSON

El paquete play.api.libs.json contiene estructuras de datos para representar datos JSON y utilidades para convertir entre estas estructuras de datos y otras representaciones de datos. Algunas de las características de este paquete son:

- Conversión automática a y desde clases case con un mínimo de código. Si quieres ponerte en marcha rápidamente con un mínimo de código, este es probablemente el lugar para empezar.
- Validación personalizada durante el análisis.
- Análisis automático de JSON en los cuerpos de las peticiones, con errores autogenerados si el contenido no es analizable o se suministran cabeceras Content-type incorrectas.
- Se puede utilizar fuera de una aplicación Play como una biblioteca independiente. Basta con añadir libraryDependencies += "com.typesafe.play" %% "play-json" % playVersion al archivo build.sbt.
- Altamente personalizable.

El paquete proporciona los siguientes tipos:

### JsValue

Es un trait que representa cualquier valor JSON. La biblioteca JSON tiene una clase case que extiende JsValue para representar cada tipo JSON válido:

- JsString
- JsNumber
- JsBoolean
- JsObject
- JsArray
- JsNull

Utilizando los distintos tipos de JsValue, puede construir una representación de cualquier estructura JSON.

#### Json

El objeto Json proporciona utilidades, principalmente para la conversión a y desde estructuras JsValue.

#### JsPath

Representa una ruta dentro de una estructura JsValue, análoga a XPath para XML. Se utiliza para recorrer estructuras JsValue y en patrones para convertidores implícitos.

### Conversión a JsValue

#### Uso del análisis sintáctico de cadenas

```scala
import play.api.libs.json._

val json: JsValue = Json.parse("""
  {
    "name" : "Watership Down",
    "location" : {
      "lat" : 51.235685,
      "long" : -1.309197
    },
    "residents" : [ {
      "name" : "Fiver",
      "age" : 4,
      "role" : null
    }, {
      "name" : "Bigwig",
      "age" : 6,
      "role" : "Owsla"
    } ]
  }
  """)
```

#### Utilizar la construcción de clases

```scala
import play.api.libs.json._

val json: JsValue = JsObject(Seq(
  "name" -> JsString("Watership Down"),
  "location" -> JsObject(Seq("lat" -> JsNumber(51.235685), "long" -> JsNumber(-1.309197))),
  "residents" -> JsArray(IndexedSeq(
    JsObject(Seq(
      "name" -> JsString("Fiver"),
      "age" -> JsNumber(4),
      "role" -> JsNull
    )),
    JsObject(Seq(
      "name" -> JsString("Bigwig"),
      "age" -> JsNumber(6),
      "role" -> JsString("Owsla")
    ))
  ))
))
```

Json.obj y Json.arr pueden simplificar un poco la construcción. Tenga en cuenta que la mayoría de los valores no necesitan ser envueltos explícitamente por las clases JsValue, los métodos de fábrica utilizan la conversión implícita (más sobre esto más adelante).

```scala
import play.api.libs.json.{ JsNull, Json, JsString, JsValue }

val json: JsValue = Json.obj(
  "name" -> "Watership Down",
  "location" -> Json.obj("lat" -> 51.235685, "long" -> -1.309197),
  "residents" -> Json.arr(
    Json.obj(
      "name" -> "Fiver",
      "age" -> 4,
      "role" -> JsNull
    ),
    Json.obj(
      "name" -> "Bigwig",
      "age" -> 6,
      "role" -> "Owsla"
    )
  )
)
```

#### Uso de los convertidores de escritura

La conversión de Scala a JsValue se realiza mediante el método Json.toJson[T](T)(implicit writes: Writes[T]). Esta funcionalidad depende de un conversor de tipo Writes[T] que puede convertir un T en un JsValue.

La Play JSON API proporciona escrituras implícitas para la mayoría de los tipos básicos, como Int, Double, String y Boolean. También admite escrituras para colecciones de cualquier tipo T para las que exista un Writes[T].

```scala
import play.api.libs.json._

// basic types
val jsonString = Json.toJson("Fiver")
val jsonNumber = Json.toJson(4)
val jsonBoolean = Json.toJson(false)

// collections of basic types
val jsonArrayOfInts = Json.toJson(Seq(1, 2, 3, 4))
val jsonArrayOfStrings = Json.toJson(List("Fiver", "Bigwig"))
```

Para convertir tus propios modelos a JsValues, debes definir convertidores implícitos Writes y proporcionarlos en el ámbito.

```scala
case class Location(lat: Double, long: Double)
case class Resident(name: String, age: Int, role: Option[String])
case class Place(name: String, location: Location, residents: Seq[Resident])
```

```scala
import play.api.libs.json._

implicit val locationWrites = new Writes[Location] {
  def writes(location: Location) = Json.obj(
    "lat" -> location.lat,
    "long" -> location.long
  )
}

implicit val residentWrites = new Writes[Resident] {
  def writes(resident: Resident) = Json.obj(
    "name" -> resident.name,
    "age" -> resident.age,
    "role" -> resident.role
  )
}

implicit val placeWrites = new Writes[Place] {
  def writes(place: Place) = Json.obj(
    "name" -> place.name,
    "location" -> place.location,
    "residents" -> place.residents
  )
}

val place = Place(
  "Watership Down",
  Location(51.235685, -1.309197),
  Seq(
    Resident("Fiver", 4, None),
    Resident("Bigwig", 6, Some("Owsla"))
  )
)

val json = Json.toJson(place)
```

Alternativamente, puede definir sus escrituras utilizando el patrón combinador:

```scala
import play.api.libs.json._
import play.api.libs.functional.syntax._

implicit val locationWrites: Writes[Location] = (
  (JsPath \ "lat").write[Double] and
  (JsPath \ "long").write[Double]
)(unlift(Location.unapply))

implicit val residentWrites: Writes[Resident] = (
  (JsPath \ "name").write[String] and
  (JsPath \ "age").write[Int] and
  (JsPath \ "role").writeNullable[String]
)(unlift(Resident.unapply))

implicit val placeWrites: Writes[Place] = (
  (JsPath \ "name").write[String] and
  (JsPath \ "location").write[Location] and
  (JsPath \ "residents").write[Seq[Resident]]
)(unlift(Place.unapply))
```

### Recorrer una estructura JsValue

Puede recorrer una estructura JsValue y extraer valores específicos. La sintaxis y la funcionalidad son similares al procesamiento XML de Scala.

**Nota:** Los siguientes ejemplos se aplican a la estructura JsValue creada en los ejemplos anteriores.

#### Simple path \

Si se aplica el operador \ a un JsValue, se devolverá la propiedad correspondiente al argumento de campo en un JsObject, o el elemento en ese índice en un JsArray

```scala
val lat = (json \ "location" \ "lat").get
// returns JsNumber(51.235685)
val bigwig = (json \ "residents" \ 1).get
// returns {"name":"Bigwig","age":6,"role":"Owsla"}
```

El operador \ devuelve un JsLookupResult, que puede ser JsDefined o JsUndefined. Se pueden encadenar varios operadores \, y el resultado será JsUndefined si no se puede encontrar ningún valor intermedio. Llamar a get sobre un JsLookupResult intenta obtener el valor si está definido y lanza una excepción si no lo está.

También puede utilizar el método Direct lookup apply (más abajo) para obtener un campo en un objeto o índice en un array. Al igual que get, este método lanzará una excepción si el valor no existe.

#### Trayectoria recursiva \\\

Al aplicar el operador \\\ se buscará el campo en el objeto actual y en todos los descendientes.

```scala
val names = json \\ "name"
// returns Seq(JsString("Watership Down"), JsString("Fiver"), JsString("Bigwig"))
```

#### Búsqueda directa

Puede recuperar un valor en un JsArray o JsObject utilizando un operador .apply, que es idéntico al operador Simple path \ excepto que devuelve el valor directamente (en lugar de envolverlo en un JsLookupResult) y lanza una excepción si no se encuentra el índice o la clave:

```scala
val name = json("name")
// returns JsString("Watership Down")

val bigwig2 = json("residents")(1)
// returns {"name":"Bigwig","age":6,"role":"Owsla"}

// (json("residents")(3)
// throws an IndexOutOfBoundsException

// json("bogus")
// throws a NoSuchElementException

```

Esto es útil si estás escribiendo código rápido y sucio y estás accediendo a algunos valores JSON que sabes que existen, por ejemplo en scripts puntuales o en la REPL.

### Conversión de un JsValue

#### Utilización de las utilidades String

Minificado:

```scala
val minifiedString: String = Json.stringify(json)
```

Legible:

```scala
val readableString: String = Json.prettyPrint(json)
```

```json
{
  "name" : "Watership Down",
  "location" : {
    "lat" : 51.235685,
    "long" : -1.309197
  },
  "residents" : [ {
    "name" : "Fiver",
    "age" : 4,
    "role" : null
  }, {
    "name" : "Bigwig",
    "age" : 6,
    "role" : "Owsla"
  } ]
}
```

#### Uso de JsValue.as/asOpt

La forma más sencilla de convertir un JsValue a otro tipo es utilizando JsValue.as[T](implicit fjs: Reads[T]): T. Esto requiere un conversor implícito de tipo Reads[T] para convertir un JsValue a T (el inverso de Writes[T]). Al igual que con Writes, la API JSON proporciona Reads para los tipos básicos.

```scala
val name = (json \ "name").as[String]
// "Watership Down"

val names = (json \\ "name").map(_.as[String])
// Seq("Watership Down", "Fiver", "Bigwig")
```

El método as lanzará una JsResultException si no se encuentra la ruta o la conversión no es posible. Un método más seguro es JsValue.asOpt[T](implicit fjs: Reads[T]): Opción[T].

```scala
val nameOption = (json \ "name").asOpt[String]
// Some("Watership Down")

val bogusOption = (json \ "bogus").asOpt[String]
// None
```

Aunque el método asOpt es más seguro, cualquier información de error se pierde.

#### Utilizar la validación

La forma preferida de convertir un JsValue a otro tipo es utilizando su método validate (que toma un argumento de tipo Reads). Esto realiza tanto la validación como la conversión, devolviendo un tipo JsResult. JsResult es implementado por dos clases:

- JsSuccess: Representa una validación/conversión exitosa y envuelve el resultado.
- JsError: Representa una validación/conversión fallida y contiene una lista de errores de validación.

Puede aplicar varios patrones para manejar un resultado de validación:

```scala
val json = { ... }

val nameResult: JsResult[String] = (json \ "name").validate[String]

// Pattern matching
nameResult match {
  case s: JsSuccess[String] => println("Name: " + s.get)
  case e: JsError => println("Errors: " + JsError.toJson(e).toString())
}

// Fallback value
val nameOrFallback = nameResult.getOrElse("Undefined")

// map
val nameUpperResult: JsResult[String] = nameResult.map(_.toUpperCase())

// fold
val nameOption: Option[String] = nameResult.fold(
  invalid = {
    fieldErrors =>
      fieldErrors.foreach(x => {
        println("field: " + x._1 + ", errors: " + x._2)
      })
      None
  },
  valid = {
    name => Some(name)
  }
)
```

#### JsValue a un modelo

Para convertir de JsValue a un modelo, debe definir Reads[T] implícito donde T es el tipo de su modelo.

Nota: El patrón utilizado para implementar Reads y la validación personalizada se tratan en detalle en Combinadores JSON Reads/Writes/Formats.

```scala
case class Location(lat: Double, long: Double)
case class Resident(name: String, age: Int, role: Option[String])
case class Place(name: String, location: Location, residents: Seq[Resident])
```

```scala
import play.api.libs.json._
import play.api.libs.functional.syntax._

implicit val locationReads: Reads[Location] = (
  (JsPath \ "lat").read[Double] and
  (JsPath \ "long").read[Double]
)(Location.apply _)

implicit val residentReads: Reads[Resident] = (
  (JsPath \ "name").read[String] and
  (JsPath \ "age").read[Int] and
  (JsPath \ "role").readNullable[String]
)(Resident.apply _)

implicit val placeReads: Reads[Place] = (
  (JsPath \ "name").read[String] and
  (JsPath \ "location").read[Location] and
  (JsPath \ "residents").read[Seq[Resident]]
)(Place.apply _)

val json = { ... }

val placeResult: JsResult[Place] = json.validate[Place]
// JsSuccess(Place(...),)

val residentResult: JsResult[Resident] = (json \ "residents")(1).validate[Resident]
// JsSuccess(Resident(Bigwig,6,Some(Owsla)),)
```

## Combinadores de lectura/escritura/formato JSON

JSON basics introdujo los conversores Reads y Writes que se utilizan para convertir entre estructuras JsValue y otros tipos de datos. Esta página cubre en mayor detalle cómo construir estos convertidores y cómo utilizar la validación durante la conversión.

Los ejemplos de esta página utilizarán esta estructura JsValue y el modelo correspondiente:

```scala
import play.api.libs.json._

val json: JsValue = Json.parse("""
  {
    "name" : "Watership Down",
    "location" : {
      "lat" : 51.235685,
      "long" : -1.309197
    },
    "residents" : [ {
      "name" : "Fiver",
      "age" : 4,
      "role" : null
    }, {
      "name" : "Bigwig",
      "age" : 6,
      "role" : "Owsla"
    } ]
  }
  """)
```

```scala
case class Location(lat: Double, long: Double)
case class Resident(name: String, age: Int, role: Option[String])
case class Place(name: String, location: Location, residents: Seq[Resident])
```

### JsPath

JsPath es un bloque básico para la creación de Reads/Writes. JsPath representa la ubicación de los datos en una estructura JsValue. Puede utilizar el objeto JsPath (ruta raíz) para definir una instancia JsPath hija utilizando una sintaxis similar a la de recorrer JsValue:

```scala
import play.api.libs.json._

val json = { ... }

// Simple path
val latPath = JsPath \ "location" \ "lat"

// Recursive path
val namesPath = JsPath \\ "name"

// Indexed path
val firstResidentPath = (JsPath \ "residents")(0)
```

El paquete play.api.libs.json define un alias para JsPath: __ (guión bajo doble). Puede utilizarlo si lo prefiere:

```scala
val longPath = __ \ "location" \ "long"
```

### Reads

Los conversores Reads se utilizan para convertir de un JsValue a otro tipo. Se pueden combinar y anidar Reads para crear Reads más complejos.

Necesitarás estas importaciones para crear Reads:

```scala
import play.api.libs.json._ // JSON library
import play.api.libs.json.Reads._ // Custom validation helpers
import play.api.libs.functional.syntax._ // Combinator syntax
```

### Path Reads

JsPath tiene métodos para crear Reads especiales que aplican otro Reads a un JsValue en una ruta especificada:

- JsPath.read[T](implícito r: Reads[T]): Reads[T] - Crea un Reads[T] que aplicará el argumento implícito r al JsValue en esta ruta.
- JsPath.readNullable[T](implicit r: Reads[T]): Reads[Opción[T]] - Se utiliza para rutas que pueden faltar o que pueden contener un valor nulo.

**Nota:** La biblioteca JSON proporciona lecturas implícitas para tipos básicos como String, Int, Double, etc.

Definir una ruta individual Reads tiene este aspecto:

```scala
val nameReads: Reads[String] = (JsPath \ "name").read[String]
```

### Complex Reads

Puede combinar lecturas de rutas individuales para formar lecturas más complejas que pueden utilizarse para convertir a modelos complejos.

Para facilitar la comprensión, dividiremos la función de combinación en dos sentencias. En primer lugar, se combinan objetos de lectura mediante el combinador and:

```scala
val locationReadsBuilder =
  (JsPath \ "lat").read[Double] and
    (JsPath \ "long").read[Double]
```

Esto producirá un tipo de FunctionalBuilder[Reads]#CanBuild2[Double, Double]. Este es un objeto intermedio y no necesitas preocuparte demasiado por él, sólo saber que se utiliza para crear un Reads complejo.

En segundo lugar llame al método apply de CanBuildX con una función para traducir los valores individuales a su modelo, esto le devolverá su complejo Reads. Si tienes una clase case con una firma de constructor que coincida, puedes usar su método apply:

```scala
implicit val locationReads = locationReadsBuilder.apply(Location.apply _)
```

Aquí está el mismo código en una sola declaración:

```scala
implicit val locationReads: Reads[Location] = (
  (JsPath \ "lat").read[Double] and
  (JsPath \ "long").read[Double]
)(Location.apply _)
```

### Validación con lecturas

El método JsValue.validate fue introducido en JSON basics como la forma preferida de realizar la validación y conversión de un JsValue a otro tipo. Este es el patrón básico:

```scala
val json = { ... }

val nameReads: Reads[String] = (JsPath \ "name").read[String]

val nameResult: JsResult[String] = json.validate[String](nameReads)

nameResult match {
  case s: JsSuccess[String] => println("Name: " + s.get)
  case e: JsError => println("Errors: " + JsError.toJson(e).toString())
}
```

La validación por defecto para Reads es mínima, como la comprobación de errores de conversión de tipos. Puede definir reglas de validación personalizadas utilizando los ayudantes de validación de Reads. Éstos son algunos de los más utilizados:

- Reads.email - Valida si una cadena tiene formato de correo electrónico.
- Reads.minLength(nb) - Valida la longitud mínima de una colección o cadena.
- Reads.min - Valida un valor mínimo.
- Reads.max - Valida un valor máximo.
- Reads[A] keepAnd Reads[B] => Reads[A] - Operador que prueba Reads[A] y Reads[B] pero sólo mantiene el resultado de Reads[A] (Para los que conozcan los combinadores del analizador Scala keepAnd == <~ ).
- Reads[A] andKeep Reads[B] => Reads[B] - Operador que prueba Reads[A] y Reads[B] pero sólo mantiene el resultado de Reads[B] (Para los que conozcan los combinadores de Scala yKeep == ~> ).
- Reads[A] or Reads[B] => Reads - Operador que realiza un OR lógico y mantiene el resultado del último Reads comprobado.

Para añadir validación, aplique helpers como argumentos al método JsPath.read:

```scala
val improvedNameReads =
  (JsPath \ "name").read[String](minLength[String](2))
```

### Ponerlo todo junto - Ejemplo de lectura

Utilizando lecturas complejas y validación personalizada podemos definir un conjunto de lecturas efectivas para nuestro modelo de ejemplo y aplicarlas:

```scala
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

implicit val locationReads: Reads[Location] = (
  (JsPath \ "lat").read[Double](min(-90.0) keepAnd max(90.0)) and
  (JsPath \ "long").read[Double](min(-180.0) keepAnd max(180.0))
)(Location.apply _)

implicit val residentReads: Reads[Resident] = (
  (JsPath \ "name").read[String](minLength[String](2)) and
  (JsPath \ "age").read[Int](min(0) keepAnd max(150)) and
  (JsPath \ "role").readNullable[String]
)(Resident.apply _)

implicit val placeReads: Reads[Place] = (
  (JsPath \ "name").read[String](minLength[String](2)) and
  (JsPath \ "location").read[Location] and
  (JsPath \ "residents").read[Seq[Resident]]
)(Place.apply _)

val json = { ... }

json.validate[Place] match {
  case s: JsSuccess[Place] => {
    val place: Place = s.get
    // do something with place
  }
  case e: JsError => {
    // error handling flow
  }
}
```

Tenga en cuenta que las lecturas complejas pueden estar anidadas. En este caso, placeReads utiliza los locationReads y residentReads implícitos previamente definidos en rutas específicas de la estructura.

### Writes

Los convertidores de escritura se utilizan para convertir de algún tipo a un JsValue.

Puedes construir Writes complejos utilizando JsPath y combinadores muy similares a los Reads. Aquí están los Writes para nuestro modelo de ejemplo:

```scala
import play.api.libs.json._
import play.api.libs.functional.syntax._

implicit val locationWrites: Writes[Location] = (
  (JsPath \ "lat").write[Double] and
  (JsPath \ "long").write[Double]
)(unlift(Location.unapply))

implicit val residentWrites: Writes[Resident] = (
  (JsPath \ "name").write[String] and
  (JsPath \ "age").write[Int] and
  (JsPath \ "role").writeNullable[String]
)(unlift(Resident.unapply))

implicit val placeWrites: Writes[Place] = (
  (JsPath \ "name").write[String] and
  (JsPath \ "location").write[Location] and
  (JsPath \ "residents").write[Seq[Resident]]
)(unlift(Place.unapply))

val place = Place(
  "Watership Down",
  Location(51.235685, -1.309197),
  Seq(
    Resident("Fiver", 4, None),
    Resident("Bigwig", 6, Some("Owsla"))
  )
)

val json = Json.toJson(place)
```

Existen algunas diferencias entre las escrituras y las lecturas complejas:

- Las escrituras individuales de la ruta se crean utilizando el método JsPath.write.
- No hay validación en la conversión a JsValue lo que hace la estructura más simple y no necesitarás ningún ayudante de validación.
- El intermediario FunctionalBuilder#CanBuildX (creado por y combinadores) toma una función que traduce un tipo complejo T a una tupla que coincide con la ruta individual Escribe. Aunque esto es simétrico al caso Reads, el método unapply de una clase case devuelve una Option de una tupla de propiedades y debe utilizarse con unlift para extraer la tupla.

### Tipos recursivos

Un caso especial que nuestro modelo de ejemplo no demuestra es cómo manejar lecturas y escrituras para tipos recursivos. JsPath proporciona métodos lazyRead y lazyWrite que toman parámetros call-by-name para manejar esto:

```scala
case class User(name: String, friends: Seq[User])

implicit lazy val userReads: Reads[User] = (
  (__ \ "name").read[String] and
  (__ \ "friends").lazyRead(Reads.seq[User](userReads))
)(User)

implicit lazy val userWrites: Writes[User] = (
  (__ \ "name").write[String] and
  (__ \ "friends").lazyWrite(Writes.seq[User](userWrites))
)(unlift(User.unapply))
```

### Formato

Format[T] no es más que una mezcla de los rasgos Reads y Writes y puede utilizarse para la conversión implícita en lugar de sus componentes.

#### Creación de formato a partir de lecturas y escrituras

Puede definir un Formato construyéndolo a partir de Lecturas y Escrituras del mismo tipo:

```scala
val locationReads: Reads[Location] = (
  (JsPath \ "lat").read[Double](min(-90.0) keepAnd max(90.0)) and
  (JsPath \ "long").read[Double](min(-180.0) keepAnd max(180.0))
)(Location.apply _)

val locationWrites: Writes[Location] = (
  (JsPath \ "lat").write[Double] and
  (JsPath \ "long").write[Double]
)(unlift(Location.unapply))

implicit val locationFormat: Format[Location] =
  Format(locationReads, locationWrites)
```

#### Creación de formatos mediante combinadores

En el caso de que tus Lecturas y Escrituras sean simétricas (lo que puede no ser el caso en aplicaciones reales), puedes definir un Formato directamente desde los combinadores:

```scala
implicit val locationFormat: Format[Location] = (
  (JsPath \ "lat").format[Double](min(-90.0) keepAnd max(90.0)) and
  (JsPath \ "long").format[Double](min(-180.0) keepAnd max(180.0))
)(Location.apply, unlift(Location.unapply))
```

## Asignación automatizada JSON

Si el JSON mapea directamente a una clase, proporcionamos una práctica macro para que no tengas que escribir el Reads[T], Writes[T], o Format[T] manualmente. Dado el siguiente caso clase :

```scala
case class Resident(name: String, age: Int, role: Option[String])
```

La siguiente macro creará un Reads[Resident] basándose en su estructura y en el nombre de sus campos :

```scala
import play.api.libs.json._

implicit val residentReads = Json.reads[Resident]
```

Al compilar, la macro inspeccionará la clase dada e
inyectará el siguiente código, exactamente como si lo hubiera escrito manualmente :

```scala
import play.api.libs.json._
import play.api.libs.functional.syntax._

implicit val residentReads = (
  (__ \ "name").read[String] and
  (__ \ "age").read[Int] and
  (__ \ "role").readNullable[String]
)(Resident)
```

Esto se hace en tiempo de compilación, por lo que no se pierde seguridad de tipos ni rendimiento.
Existen macros similares para un Writes[T] o un Format[T] :

```scala
import play.api.libs.json._

implicit val residentWrites = Json.writes[Resident]

implicit val residentFormat = Json.format[Resident]
```

Así, un ejemplo completo de cómo realizar la conversión automatizada de una clase de caso a JSON es el siguiente:

```scala
import play.api.libs.json._

implicit val residentWrites = Json.writes[Resident]

val resident = Resident(name = "Fiver", age = 4, role = None)

val residentJson: JsValue = Json.toJson(resident)
```

Y un ejemplo completo de análisis sintáctico automático de JSON a una clase de caso es:

```scala
import play.api.libs.json._

implicit val residentReads = Json.reads[Resident]

// En una petición, es probable que un JsValue provenga de `request.body.asJson`.
// o simplemente `request.body` si se utiliza el analizador `Action(parse.json)` body parser
val jsonString: JsValue = Json.parse(
  """{
    "name" : "Fiver",
    "age" : 4
  }"""
)

val residentFromJson: JsResult[Resident] = Json.fromJson[Resident](jsonString)

residentFromJson match {
  case JsSuccess(r: Resident, path: JsPath) => println("Name: " + r.name)
  case e: JsError => println("Errors: " + JsError.toJson(e).toString())
}
```

**Nota:** Para poder acceder a JSON desde request.body.asJson, la petición debe tener una cabecera Content-Type de application/json. Puedes relajar esta restricción utilizando el analizador sintáctico tolerantJson.

El ejemplo anterior puede hacerse aún más conciso utilizando analizadores de cuerpo con una función de validación tipada. Véase el ejemplo savePlaceConcise en la documentación JSON with HTTP.

### Requisitos

Estas macros se basan en algunas suposiciones sobre el tipo con el que trabajan:

- Debe tener un objeto compañero con métodos apply y unapply.
- Los tipos de retorno del método unapply deben coincidir con los tipos de argumentos del método apply.
- Los nombres de los parámetros del método apply deben coincidir con los nombres de las propiedades deseadas en el JSON.

Las clases Case cumplen estos requisitos de forma nativa. Para clases o rasgos más personalizados, puede que tenga que implementarlos.
tener que implementarlos.

### Estrategias de denominación personalizadas

Para utilizar una Estrategia de Nomenclatura personalizada es necesario definir un objeto JsonConfiguration implícito y un JsonNaming.

Se proporcionan dos estrategias de nomenclatura: la predeterminada, que utiliza tal cual los nombres de las propiedades de la clase,
y la de JsonNaming.SnakeCase.

Se puede utilizar una estrategia distinta de la predeterminada, como se indica a continuación:

```scala
import play.api.libs.json._

implicit val config = JsonConfiguration(SnakeCase)

implicit val userReads: Reads[PlayUser] = Json.reads[PlayUser]

implicit val config = JsonConfiguration(SnakeCase)

implicit val userWrites: OWrites[PlayUser] = Json.writes[PlayUser]

implicit val config = JsonConfiguration(SnakeCase)

implicit val userFormat: OFormat[PlayUser] = Json.format[PlayUser]
```

#### Aplicar su propia estrategia de nombres

Para implementar tu propia Estrategia de Nomenclatura sólo necesitas implementar el rasgo JsonNaming:

```scala
import play.api.libs.json._

object Lightbend extends JsonNaming {
  override def apply(property: String): String = s"lightbend_$property"
}

implicit val config = JsonConfiguration(Lightbend)

implicit val customWrites: OFormat[PlayUser] = Json.format[PlayUser]
```

## JSON con HTTP

Play admite solicitudes y respuestas HTTP con un tipo de contenido JSON utilizando la API HTTP en combinación con la biblioteca JSON.

Demostraremos los conceptos necesarios diseñando un sencillo servicio web RESTful para obtener una lista de entidades y aceptar POSTs para crear nuevas entidades. El servicio utilizará un tipo de contenido JSON para todos los datos.

Este es el modelo que usaremos para nuestro servicio:

```scala
case class Location(lat: Double, long: Double)

case class Place(name: String, location: Location)

object Place {

  var list: List[Place] = {
    List(
      Place(
        "Sandleford",
        Location(51.377797, -1.318965)
      ),
      Place(
        "Watership Down",
        Location(51.235685, -1.309197)
      )
    )
  }

  def save(place: Place) = {
    list = list ::: List(place)
  }
}
```

### Servir una lista de entidades en JSON

Empezaremos por añadir las importaciones necesarias a nuestro controlador.

```scala
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {}
```

Antes de escribir nuestra Acción, necesitaremos la fontanería para hacer la conversión de nuestro modelo a una representación JsValue. Esto se consigue definiendo un Writes[Place] implícito.

```scala
implicit val locationWrites: Writes[Location] =
  (JsPath \ "lat").write[Double].and((JsPath \ "long").write[Double])(unlift(Location.unapply))

implicit val placeWrites: Writes[Place] =
  (JsPath \ "name").write[String].and((JsPath \ "location").write[Location])(unlift(Place.unapply))
```

A continuación escribimos nuestra Acción:

```scala
def listPlaces = Action {
  val json = Json.toJson(Place.list)
  Ok(json)
}
```

La Acción recupera una lista de objetos Place, los convierte en un JsValue utilizando Json.toJson con nuestro implícito Writes[Place], y devuelve esto como el cuerpo del resultado. Play reconocerá el resultado como JSON y establecerá la cabecera Content-Type y el valor del cuerpo apropiados para la respuesta.

El último paso es añadir una ruta para nuestra Acción en conf/routes:

```scala
GET   /places               controllers.Application.listPlaces
```

Podemos probar la acción realizando una petición con un navegador o herramienta HTTP. Este ejemplo utiliza la herramienta de línea de comandos unix cURL:

```bash
curl --include http://localhost:9000/places
```

Respuesta:

```bash
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 141

[{"name":"Sandleford","location":{"lat":51.377797,"long":-1.318965}},{"name":"Watership Down","location":{"lat":51.235685,"long":-1.309197}}]
```

### Creación de una nueva instancia de entidad en JSON

Para esta Acción necesitaremos definir un Reads[Place] implícito para convertir un JsValue a nuestro modelo.

```scala
implicit val locationReads: Reads[Location] =
  (JsPath \ "lat").read[Double].and((JsPath \ "long").read[Double])(Location.apply _)

implicit val placeReads: Reads[Place] =
  (JsPath \ "name").read[String].and((JsPath \ "location").read[Location])(Place.apply _)
```

A continuación definiremos la Acción.

```scala
def savePlace = Action(parse.json) { request =>
  val placeResult = request.body.validate[Place]
  placeResult.fold(
    errors => {
      BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
    },
    place => {
      Place.save(place)
      Ok(Json.obj("status" -> "OK", "message" -> ("Place '" + place.name + "' saved.")))
    }
  )
}
```

Esta acción es más complicada que el caso de nuestra lista. Algunas cosas a tener en cuenta:

- Esta acción espera una solicitud con una cabecera Content-Type text/json o application/json y un cuerpo que contenga una representación JSON de la entidad a crear.
- Utiliza un BodyParser específico de JSON que analizará la solicitud y proporcionará request.body como un JsValue.
- Hemos utilizado el método de validación para la conversión, que se basará en nuestras lecturas implícitas[Place].
- Para procesar el resultado de la validación, utilizamos un pliegue con flujos de error y éxito. Este patrón puede ser familiar ya que también se utiliza para el envío de formularios.
- La Acción también envía respuestas JSON.

Los analizadores de cuerpo pueden ser tipados con una clase case, un objeto Reads explícito o tomar una función. Así que podemos descargar aún más trabajo en Play para que analice automáticamente JSON en una clase case y lo valide incluso antes de llamar a nuestra Acción:

```scala
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

implicit val locationReads: Reads[Location] =
  (JsPath \ "lat")
    .read[Double](min(-90.0).keepAnd(max(90.0)))
    .and((JsPath \ "long").read[Double](min(-180.0).keepAnd(max(180.0))))(Location.apply _)

implicit val placeReads: Reads[Place] =
  (JsPath \ "name").read[String](minLength[String](2)).and((JsPath \ "location").read[Location])(Place.apply _)

// Este helper analiza y valida JSON usando el `placeReads` implícito.
// anterior, devolviendo errores si el json analizado falla la validación.
def validateJson[A: Reads] = parse.json.validate(
  _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
)

// si no nos importa la validación podríamos reemplazar `validateJson[Place]`.
// con `BodyParsers.parse.json[Place]` para obtener una clase de caso sin validar
// en `request.body` en su lugar.
def savePlaceConcise = Action(validateJson[Place]) { request =>
  // El archivo `request.body` contiene una instancia de `Place` totalmente validada.
  val place = request.body
  Place.save(place)
  Ok(Json.obj("status" -> "OK", "message" -> ("Place '" + place.name + "' saved.")))
}
```

Por último, añadiremos un enlace de ruta en conf/routes:

```scala
POST  /places               controllers.Application.savePlace
```

Probaremos esta acción con peticiones válidas e inválidas para verificar nuestros flujos de éxito y error.

Probando la acción con un dato válido:
  
```bash
curl --include
  --request POST
  --header "Content-type: application/json" 
  --data '{"name":"Nuthanger Farm","location":{"lat" : 51.244031,"long" : -1.263224}}' 
  http://localhost:9000/places
```

Response:

```bash
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 57

{"status":"OK","message":"Place 'Nuthanger Farm' saved."}
```

Probando la acción con un dato no válido, falta el campo "nombre":
  
```bash
curl --include
  --request POST
  --header "Content-type: application/json"
  --data '{"location":{"lat" : 51.244031,"long" : -1.263224}}' 
  http://localhost:9000/places
```

Response:

```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json
Content-Length: 79

{"status":"KO","message":{"obj.name":[{"msg":"error.path.missing","args":[]}]}}
```

Probando la acción con un dato inválido, tipo de dato incorrecto para "lat":

```bash
curl --include
  --request POST
  --header "Content-type: application/json" 
  --data '{"name":"Nuthanger Farm","location":{"lat" : "xxx","long" : -1.263224}}' 
  http://localhost:9000/places
```

Response:

```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json
Content-Length: 92

{"status":"KO","message":{"obj.location.lat":[{"msg":"error.expected.jsnumber","args":[]}]}}
```

## Carga de archivos

### Carga de archivos en un formulario mediante multipart/form-data

La forma estándar de cargar archivos en una aplicación web es utilizar un formulario con una codificación especial multipart/form-data, que permite mezclar los datos estándar del formulario con los datos adjuntos del archivo.

**Nota:** El método HTTP utilizado para enviar el formulario debe ser POST (no GET).

Empiece por escribir un formulario HTML:

```html
@helper.form(action = routes.ScalaFileUploadController.upload, 'enctype -> "multipart/form-data") {
    
    <input type="file" name="picture">
    
    <p>
        <input type="submit">
    </p>
    
}
```

Añada un token CSRF al formulario, a menos que tenga desactivado el filtro CSRF. El filtro CSRF comprueba el formulario multiparte en el orden en que se enumeran los campos, así que coloque el token CSRF antes del campo de entrada de archivo. Esto mejora la eficiencia y evita un error de token no encontrado si el tamaño del archivo excede play.filters.csrf.body.bufferSize.

Ahora defina la acción de carga utilizando un analizador de cuerpo multipartFormData:

```scala
def upload = Action(parse.multipartFormData) { request =>
  request.body
    .file("picture")
    .map { picture =>
      // obtener sólo la última parte del nombre del archivo
      // de lo contrario alguien puede enviar una ruta como ../../home/foo/bar.txt para escribir en otros archivos del sistema
      val filename = Paths.get(picture.filename).getFileName

      picture.ref.moveTo(Paths.get(s"/tmp/picture/$filename"), replace = true)
      Ok("File uploaded")
    }
    .getOrElse {
      Redirect(routes.ScalaFileUploadController.index).flashing("error" -> "Missing file")
    }
}
```

El atributo ref le da una referencia a un TemporaryFile. Esta es la forma predeterminada en que el analizador multipartFormData maneja la carga de archivos.

**Nota:** Como siempre, también puede utilizar el analizador sintáctico del cuerpo anyContent y recuperarlo como request.body.asMultipartFormData.

Por último, añade un router POST

```scala
POST  /          controllers.ScalaFileUploadController.upload()
```

### Carga directa de archivos

Otra forma de enviar archivos al servidor es utilizar Ajax para cargar el archivo de forma asíncrona en un formulario. En este caso, el cuerpo de la solicitud no se habrá codificado como multipart/form-data, sino que contendrá simplemente el contenido del archivo.

En este caso podemos utilizar un analizador de cuerpo para almacenar el contenido del cuerpo de la petición en un archivo. Para este ejemplo, vamos a utilizar el analizador de cuerpo temporaryFile:

```scala
def upload = Action(parse.temporaryFile) { request =>
  request.body.moveTo(Paths.get("/tmp/picture/uploaded"), replace = true)
  Ok("File uploaded")
}
```

### Escribir su propio analizador sintáctico

Si quieres manejar la carga del archivo directamente sin almacenarlo en un archivo temporal, puedes escribir tu propio BodyParser. En este caso, recibirás trozos de datos que podrás insertar donde quieras.

Si desea utilizar la codificación multipart/form-data, puede seguir utilizando el analizador multipartFormData por defecto proporcionando un FilePartHandler[A] y utilizando un Sink diferente para acumular los datos. Por ejemplo, puede utilizar un FilePartHandler[File] en lugar de un TemporaryFile especificando un Accumulator(fileSink):

```scala
type FilePartHandler[A] = FileInfo => Accumulator[ByteString, FilePart[A]]

def handleFilePartAsFile: FilePartHandler[File] = {
  case FileInfo(partName, filename, contentType) =>
    val perms       = java.util.EnumSet.of(OWNER_READ, OWNER_WRITE)
    val attr        = PosixFilePermissions.asFileAttribute(perms)
    val path        = JFiles.createTempFile("multipartBody", "tempFile", attr)
    val file        = path.toFile
    val fileSink    = FileIO.toPath(path)
    val accumulator = Accumulator(fileSink)
    accumulator.map {
      case IOResult(count, status) =>
        FilePart(partName, filename, contentType, file)
    }(ec)
}

def uploadCustom = Action(parse.multipartFormData(handleFilePartAsFile)) { request =>
  val fileOption = request.body.file("name").map {
    case FilePart(key, filename, contentType, file) =>
      file.toPath
  }

  Ok(s"File uploaded: $fileOption")
}
```

### Limpieza de archivos temporales

La carga de archivos utiliza una API TemporaryFile que se basa en el almacenamiento de archivos en un sistema de archivos temporal, accesible a través del atributo ref. Todas las referencias a TemporaryFile provienen de un rasgo TemporaryFileCreator, y la implementación se puede intercambiar según sea necesario, y ahora hay un método atomicMoveWithFallback que utiliza StandardCopyOption.ATOMIC_MOVE si está disponible.

La carga de archivos es una operación inherentemente peligrosa, ya que la carga de archivos sin límites puede hacer que el sistema de archivos se llene - como tal, la idea detrás de TemporaryFile es que sólo está en el ámbito de aplicación en la finalización y debe ser movido fuera del sistema de archivos temporales tan pronto como sea posible. Los archivos temporales que no se mueven se eliminan.

Sin embargo, bajo ciertas condiciones, la recolección de basura no ocurre a tiempo. Como tal, también hay un play.api.libs.Files.TemporaryFileReaper que puede ser habilitado para eliminar archivos temporales de forma programada utilizando el planificador de Akka, distinto del método de recolección de basura.

El reaper está desactivado por defecto, y se activa a través de application.conf:

```conf
play.temporaryFile {
  reaper {
    enabled = true
    initialDelay = "5 minutes"
    interval = "30 seconds"
    olderThan = "30 minutes"
  }
}
```

La configuración anterior eliminará los archivos que tengan más de 30 minutos de antigüedad, utilizando la propiedad "olderThan". Iniciará el reaper cinco minutos después de que se inicie la aplicación, y comprobará el sistema de archivos cada 30 segundos a partir de entonces. El reaper no es consciente de ninguna carga de archivos existente, por lo que las cargas de archivos prolongadas pueden toparse con el reaper si el sistema no está configurado cuidadosamente.

## Uso de Play Slick

El módulo Play Slick convierte a Slick en un ciudadano de primera clase de Play.

El módulo Play Slick consta de dos características:

- Integración de Slick en el ciclo de vida de las aplicaciones de Play.
- Soporte para las evoluciones de la base de datos de Play.

Play Slick actualmente soporta Slick 3.1 con Play 2.5, para Scala 2.11.

### Obtener ayuda

Si tienes problemas para utilizar Play Slick, comprueba si las FAQ contienen la respuesta. Si no, no dudes en ponerte en contacto con el grupo de usuarios de play-framework. Además, ten en cuenta que si estás buscando ayuda sobre Slick, el grupo de usuarios de slick puede ser un lugar mejor.

Por último, si prefieres obtener una respuesta a tus preguntas sobre Play y Slick de manera oportuna y con un SLA bien definido, es posible que prefieras ponerte en contacto con Lightbend, ya que ofrece soporte comercial para estas tecnologías.

### Acerca de esta versión

Si ha estado utilizando una versión anterior de Play Slick, se dará cuenta de que ha habido bastantes cambios importantes. Se recomienda leer la guía de migración para una actualización sin problemas.

Mientras que, si es la primera vez que utilizas Play Slick, apreciarás que la integración de Slick en Play es bastante austera. Esto significa que si conoces tanto Play como Slick, utilizar el módulo Play Slick debería ser sencillo.

### Configurar

Añade una biblioteca dependiente de play-slick:

```scala
libraryDependencies += "com.typesafe.play" %% "play-slick" % "2.0.0"
```

La dependencia anterior también traerá consigo la biblioteca Slick como dependencia transitiva. Esto implica que no necesitas añadir una dependencia explícita de Slick, pero puedes hacerlo si quieres. Una razón probable para querer definir explícitamente una dependencia de Slick es si desea utilizar una versión más reciente que la incluida con play-slick. Dado que las versiones de Slick trailing dot son compatibles bíblicamente, no incurrirás en ningún riesgo al usar una versión de Slick trailing point diferente a la que se usó para compilar play-slick.

#### Soporte para las evoluciones de la base de datos Play

Play Slick es compatible con las evoluciones de la base de datos Play.

Para habilitar las evoluciones, necesitará las siguientes dependencias:

```scala
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.0"
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0"
)
```

Tenga en cuenta que no es necesario añadir el componente Play evolutions a sus dependencias, ya que es una dependencia transitiva del módulo play-slick-evolutions.

#### Dependencia del controlador JDBC

El módulo Play Slick no incluye ningún controlador JDBC. Por lo tanto, tendrás que añadir explícitamente los controladores JDBC que quieras utilizar en tu aplicación. Por ejemplo, si quieres usar una base de datos en memoria como H2, tendrás que añadirle una dependencia:

```scala
"com.h2database" % "h2" % "${H2_VERSION}" // sustituye `${H2_VERSION}` por un número de versión real
```

### Configuración de la base de datos

Para que el módulo Play Slick maneje el ciclo de vida de las bases de datos Slick, es importante que nunca crees instancias de bases de datos explícitamente en tu código. En su lugar, debes proporcionar un controlador Slick válido y una configuración de base de datos en tu application.conf (por convención la base de datos Slick por defecto debe llamarse default):

```conf
# Configuración por defecto de la base de datos
slick.dbs.default.driver="slick.driver.H2Driver$"
slick.dbs.default.db.driver="org.h2.Driver"
slick.dbs.default.db.url="jdbc:h2:mem:play"
```

En primer lugar, tenga en cuenta que lo anterior es una configuración válida de Slick (para la lista completa de parámetros de configuración que puede utilizar para configurar una base de datos consulte el Slick ScalaDoc para Database.forConfig - asegúrese de ampliar la fila forConfig en el doc).

En segundo lugar, el prefijo slick.dbs que precede al nombre de la base de datos es configurable. De hecho, puedes cambiarlo anulando el valor de la clave de configuración play.slick.db.config.

En tercer lugar, en la configuración anterior slick.dbs.default.driver se utiliza para configurar el controlador de Slick, mientras que slick.dbs.default.db.driver es el controlador JDBC subyacente utilizado por el backend de Slick. En la configuración anterior estamos configurando Slick para utilizar la base de datos H2, pero Slick soporta varias otras bases de datos. Consulta la documentación de Slick para obtener una lista completa de las bases de datos soportadas, y para encontrar un controlador de Slick adecuado.

Slick no soporta la variable de entorno DATABASE_URL de la misma forma que el pool de conexiones por defecto de Play JBDC. Pero a partir de la versión 3.0.3, Slick proporciona un DatabaseUrlDataSource específicamente para analizar la variable de entorno.

```conf
slick.dbs.default.driver="slick.driver.PostgresDriver$"
slick.dbs.default.db.dataSourceClass = "slick.jdbc.DatabaseUrlDataSource"
slick.dbs.default.db.properties.driver = "org.postgresql.Driver"
```

En algunas plataformas, como Heroku, puede sustituir la JDBC_DATABASE_URL, que tiene el formato `jdbc:vendor://host:port/db?args`, si está disponible. Por ejemplo:

```conf
slick.dbs.default.driver="slick.driver.PostgresDriver$"
slick.dbs.default.db.driver="org.postgresql.Driver"
slick.dbs.default.db.url=${JDBC_DATABASE_URL}
```

**Nota:** Si no se proporciona un valor válido tanto para slick.dbs.default.driver como para slick.dbs.default.db.driver se producirá una excepción al intentar ejecutar la aplicación Play.

Para configurar varias bases de datos:

```conf
# Orders database
slick.dbs.orders.driver="slick.driver.H2Driver$"
slick.dbs.orders.db.driver="org.h2.Driver"
slick.dbs.orders.db.url="jdbc:h2:mem:play"

# Customers database
slick.dbs.customers.driver="slick.driver.H2Driver$"
slick.dbs.customers.db.driver="org.h2.Driver"
slick.dbs.customers.db.url="jdbc:h2:mem:play"
```

Si algo no está bien configurado, recibirá una notificación en su navegador.

### Utilización

Después de haber configurado correctamente una base de datos Slick, puedes obtener una DatabaseConfig (que es un tipo de Slick que agrupa una base de datos y un controlador) de dos maneras diferentes. Ya sea mediante el uso de inyección de dependencia, o a través de una búsqueda global a través del singleton DatabaseConfigProvider.

**Nota:** Una instancia de base de datos Slick gestiona un pool de hilos y un pool de conexiones. En general, no deberías necesitar cerrar una base de datos explícitamente en tu código (llamando a su método close), ya que el módulo Play Slick ya se encarga de ello.

#### DatabaseConfig mediante inyección de dependencias

He aquí un ejemplo de cómo inyectar una instancia DatabaseConfig para la base de datos por defecto (es decir, la base de datos llamada por defecto en su configuración):

```scala
class Application @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
```

Inyectar una instancia de DatabaseConfig para una base de datos diferente también es fácil. Basta con añadir la anotación @NamedDatabase("<nombre_db>") al parámetro del constructor dbConfigProvider:

```scala
class Application2 @Inject()(@NamedDatabase("<db-name>") dbConfigProvider: DatabaseConfigProvider) extends Controller {
```

Por supuesto, debe sustituir la cadena "<nombre de la base de datos>" por el nombre de la configuración de la base de datos que desee utilizar.

Para ver un ejemplo completo, echa un vistazo a este proyecto de ejemplo.

#### DatabaseConfig mediante búsqueda global

A continuación se muestra un ejemplo de cómo buscar una instancia de DatabaseConfig para la base de datos predeterminada (es decir, la base de datos denominada predeterminada en su configuración):

```scala
val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
```

También es fácil buscar una instancia de DatabaseConfig para una base de datos diferente. Basta con pasar el nombre de la base de datos:

```scala
val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("<db-name>")(Play.current)
```

Por supuesto, debe sustituir la cadena "<nombre de la base de datos>" por el nombre de la configuración de la base de datos que desee utilizar.

Para ver un ejemplo completo, echa un vistazo a este proyecto de ejemplo.

#### Ejecutar una consulta de base de datos en un controlador

Para ejecutar una consulta de base de datos en tu controlador, necesitarás tanto una base de datos Slick como un controlador. Afortunadamente, a partir de lo anterior ahora sabemos cómo obtener un Slick DatabaseConfig, por lo tanto tenemos lo que necesitamos para ejecutar una consulta de base de datos.

Necesitarás importar algunos tipos e implícitos del controlador:

```scala
import dbConfig.profile.api._
```

Y luego puedes definir un método del controlador que ejecute una consulta a la base de datos:

```scala
def index(name: String) = Action.async { implicit request =>
  val resultingUsers: Future[Seq[User]] = dbConfig.db.run(Users.filter(_.name === name).result)
  resultingUsers.map(users => Ok(views.html.index(users)))
}
```

Es como usar Play y Slick.

## Queries

Este capítulo describe cómo escribir consultas de tipo seguro para seleccionar, insertar, actualizar y borrar datos con la API de consultas basada en Scala de Slick. La API para construir consultas es una incrustación levantada, lo que significa que no estás trabajando con tipos estándar de Scala sino con tipos que son levantados en un constructor de tipo Rep. Esto se hace más claro cuando se comparan los tipos de un simple ejemplo de colecciones Scala

```scala
case class Coffee(name: String, price: Double)
val coffees: List[Coffee] = //...

val l = coffees.filter(_.price > 8.0).map(_.name)
//                       ^       ^          ^
//                       Double  Double     String
```

... con los tipos de código similares en Slick:

```scala
class Coffees(tag: Tag) extends Table[(String, Double)](tag, "COFFEES") {
  def name = column[String]("COF_NAME")
  def price = column[Double]("PRICE")
  def * = (name, price)
}
val coffees = TableQuery[Coffees]

val q = coffees.filter(_.price > 8.0).map(_.name)
//                       ^       ^          ^
//               Rep[Double]  Rep[Double]  Rep[String]
```

Lo mismo ocurre con el tipo de fila de la tabla Cafés, que es un subtipo de Rep[(String, Double)]. Incluso el literal 8.0 se eleva automáticamente a Rep[Double] mediante una conversión implícita porque eso es lo que el operador > de Rep[Double] espera para el lado derecho. Esta elevación es necesaria porque los tipos elevados nos permiten generar un árbol sintáctico que captura los cálculos de la consulta. Obtener funciones y valores simples de Scala no nos daría suficiente información para traducir esos cálculos a SQL.

### Expressions

Los valores escalares (no registro, no colección) se representan mediante el tipo Rep[T] para el que existe un TypedType[T] implícito.

Los operadores y otros métodos que se utilizan habitualmente en las consultas se añaden mediante conversiones implícitas definidas en ExtensionMethodConversions. Los métodos reales se encuentran en las clases AnyExtensionMethods, ColumnExtensionMethods, NumericColumnExtensionMethods, BooleanColumnExtensionMethods y StringColumnExtensionMethods (véase ExtensionMethods).

Advertencia

La mayoría de los operadores imitan a los equivalentes de Scala, pero tienes que usar === en lugar de == para comparar dos valores para igualdad y =!= en lugar de != para desigualdad. Esto es necesario porque estos operadores ya están definidos (con tipos y semántica inadecuados) en el tipo base Any, por lo que no pueden ser reemplazados por métodos de extensión.

Los valores de colección se representan mediante la clase Query (una Rep[Seq[T]]), que contiene muchos métodos estándar de colección, como flatMap, filter, take y groupBy. Debido a los dos tipos de componentes diferentes de un Query (lifted y plain, por ejemplo Query[(Rep[Int), Rep[String]), (Int, String), Seq]), las firmas de estos métodos son muy complejas pero la semántica es esencialmente la misma que para las colecciones Scala.

Se añaden métodos adicionales para consultas de valores escalares mediante una conversión implícita a SingleColumnQueryExtensionMethods.

### Clasificación y filtrado

Existen varios métodos con semántica de ordenación/filtrado (es decir, toman una consulta y devuelven una nueva consulta del mismo tipo), por ejemplo:

```scala
val q1 = coffees.filter(_.supID === 101)

// compiles to SQL (simplified):
//   select "COF_NAME", "SUP_ID", "PRICE", "SALES", "TOTAL"
//     from "COFFEES"
//     where "SUP_ID" = 101

val q2 = coffees.drop(10).take(5)
// compiles to SQL (simplified):
//   select "COF_NAME", "SUP_ID", "PRICE", "SALES", "TOTAL"
//     from "COFFEES"
//     limit 5 offset 10

val q3 = coffees.sortBy(_.name.desc.nullsFirst)
// compiles to SQL (simplified):
//   select "COF_NAME", "SUP_ID", "PRICE", "SALES", "TOTAL"
//     from "COFFEES"
//     order by "COF_NAME" desc nulls first

// construir criterios mediante un "filtro dinámico", por ejemplo, a partir de un formulario web.
val criteriaColombian = Option("Colombian")
val criteriaEspresso = Option("Espresso")
val criteriaRoast:Option[String] = None

val q4 = coffees.filter { coffee =>
  List(
      criteriaColombian.map(coffee.name === _),
      criteriaEspresso.map(coffee.name === _),
      criteriaRoast.map(coffee.name === _) // not a condition as `criteriaRoast` evaluates to `None`
  ).collect({case Some(criteria)  => criteria}).reduceLeftOption(_ || _).getOrElse(true: Rep[Boolean])
}

// compiles to SQL (simplified):
//   select "COF_NAME", "SUP_ID", "PRICE", "SALES", "TOTAL"
//     from "COFFEES"
//     where ("COF_NAME" = 'Colombian' or "COF_NAME" = 'Espresso')

```

### Unir y comprimir

Las uniones se utilizan para combinar dos tablas o consultas diferentes en una única consulta. Hay dos formas diferentes de escribir joins: Aplicativa y monádica.

#### Unión aplicativa

Las uniones aplicativas se realizan llamando a un método que une dos consultas en una única consulta de una tupla de los resultados individuales. Tienen las mismas restricciones que las uniones en SQL, es decir, el lado derecho no puede depender del lado izquierdo. Esto se aplica de forma natural a través de las reglas de alcance de Scala.

```scala
val crossJoin = for {
  (c, s) <- coffees join suppliers
} yield (c.name, s.name)
// compiles to SQL (simplified):
//   select x2."COF_NAME", x3."SUP_NAME" from "COFFEES" x2
//     inner join "SUPPLIERS" x3

val innerJoin = for {
  (c, s) <- coffees join suppliers on (_.supID === _.id)
} yield (c.name, s.name)
// compiles to SQL (simplified):
//   select x2."COF_NAME", x3."SUP_NAME" from "COFFEES" x2
//     inner join "SUPPLIERS" x3
//     on x2."SUP_ID" = x3."SUP_ID"

val leftOuterJoin = for {
  (c, s) <- coffees joinLeft suppliers on (_.supID === _.id)
} yield (c.name, s.map(_.name))
// compiles to SQL (simplified):
//   select x2."COF_NAME", x3."SUP_NAME" from "COFFEES" x2
//     left outer join "SUPPLIERS" x3
//     on x2."SUP_ID" = x3."SUP_ID"

val rightOuterJoin = for {
  (c, s) <- coffees joinRight suppliers on (_.supID === _.id)
} yield (c.map(_.name), s.name)
// compiles to SQL (simplified):
//   select x2."COF_NAME", x3."SUP_NAME" from "COFFEES" x2
//     right outer join "SUPPLIERS" x3
//     on x2."SUP_ID" = x3."SUP_ID"

val fullOuterJoin = for {
  (c, s) <- coffees joinFull suppliers on (_.supID === _.id)
} yield (c.map(_.name), s.map(_.name))
// compiles to SQL (simplified):
//   select x2."COF_NAME", x3."SUP_NAME" from "COFFEES" x2
//     full outer join "SUPPLIERS" x3
//     on x2."SUP_ID" = x3."SUP_ID"
```

Observe el uso de map en las cláusulas yield de las uniones externas. Dado que estas uniones pueden introducir valores NULL adicionales (en el lado derecho en el caso de una unión externa izquierda, en el lado izquierdo en el caso de una unión externa derecha y en ambos lados en el caso de una unión externa completa), los lados respectivos de la unión se envuelven en una Opción (con None representando una fila que no coincidió).

#### Uniones monádicas

Las uniones monádicas se crean con flatMap. Teóricamente son más potentes que las uniones aplicativas porque el lado derecho puede depender del lado izquierdo. Sin embargo, esto no es posible en SQL estándar, por lo que Slick tiene que compilarlas a uniones aplicativas, lo que es posible en muchos casos útiles pero no en todos (y hay casos en los que es posible en teoría pero Slick aún no puede realizar la transformación necesaria). Si una unión monádica no puede traducirse correctamente, fallará en tiempo de ejecución.

Una unión cruzada se crea con una operación flatMap sobre una consulta (es decir, introduciendo más de un generador en una comprensión for):

```scala
val monadicCrossJoin = for {
  c <- coffees
  s <- suppliers
} yield (c.name, s.name)
// compiles to SQL:
//   select x2."COF_NAME", x3."SUP_NAME"
//     from "COFFEES" x2, "SUPPLIERS" x3
```

Si añade una expresión de filtro, se convierte en una unión interna:

```scala
val monadicInnerJoin = for {
  c <- coffees
  s <- suppliers if c.supID === s.id
} yield (c.name, s.name)
// compiles to SQL:
//   select x2."COF_NAME", x3."SUP_NAME"
//     from "COFFEES" x2, "SUPPLIERS" x3
//     where x2."SUP_ID" = x3."SUP_ID"
```

La semántica de estas uniones monádicas es la misma que cuando usas flatMap en colecciones Scala.

**Nota**
Slick genera actualmente uniones implícitas en SQL (select ... from a, b where ...) para uniones monádicas, y uniones explícitas (select ... from a join b on ...) para uniones aplicativas. Esto está sujeto a cambios en futuras versiones.

#### Zip joins

Además de los operadores de unión aplicativos habituales soportados por las bases de datos relacionales (que se basan en una unión cruzada o una unión externa), Slick también tiene uniones zip que crean una unión por pares de dos consultas. La semántica es la misma que para las colecciones de Scala, utilizando los métodos zip y zipWith:

```scala
val zipJoinQuery = for {
  (c, s) <- coffees zip suppliers
} yield (c.name, s.name)

val zipWithJoin = for {
  res <- coffees.zipWith(suppliers, (c: Coffees, s: Suppliers) => (c.name, s.name))
} yield res
```

Un tipo particular de zip join es zipWithIndex. Comprime el resultado de una consulta con una secuencia infinita que comienza en 0. Una secuencia de este tipo no puede ser representada por una base de datos SQL y Slick tampoco la soporta actualmente. La consulta comprimida resultante, sin embargo, puede representarse en SQL con el uso de una función de número de fila, por lo que zipWithIndex está soportado como un operador primitivo:

```scala
val zipWithIndexJoin = for {
  (c, idx) <- coffees.zipWithIndex
} yield (c.name, idx)
```

### Unions

Dos consultas pueden concatenarse con los operadores ++ (o unionAll) y union si tienen tipos compatibles:

```scala
val q1 = coffees.filter(_.price < 8.0)
val q2 = coffees.filter(_.price > 9.0)

val unionQuery = q1 union q2
// compiles to SQL (simplified):
//   select x8."COF_NAME", x8."SUP_ID", x8."PRICE", x8."SALES", x8."TOTAL"
//     from "COFFEES" x8
//     where x8."PRICE" < 8.0
//   union select x9."COF_NAME", x9."SUP_ID", x9."PRICE", x9."SALES", x9."TOTAL"
//     from "COFFEES" x9
//     where x9."PRICE" > 9.0

val unionAllQuery = q1 ++ q2
// compiles to SQL (simplified):
//   select x8."COF_NAME", x8."SUP_ID", x8."PRICE", x8."SALES", x8."TOTAL"
//     from "COFFEES" x8
//     where x8."PRICE" < 8.0
//   union all select x9."COF_NAME", x9."SUP_ID", x9."PRICE", x9."SALES", x9."TOTAL"
//     from "COFFEES" x9
//     where x9."PRICE" > 9.0
```

A diferencia de la unión, que filtra los valores duplicados, ++ simplemente concatena los resultados de las consultas individuales, lo que suele ser más eficiente.

### Aggregation

La forma más sencilla de agregación consiste en calcular un valor primitivo a partir de una consulta que devuelve una única columna, normalmente de tipo numérico, por ejemplo:

```scala
val q = coffees.map(_.price)

val q1 = q.min
// compiles to SQL (simplified):
//   select min(x4."PRICE") from "COFFEES" x4

val q2 = q.max
// compiles to SQL (simplified):
//   select max(x4."PRICE") from "COFFEES" x4

val q3 = q.sum
// compiles to SQL (simplified):
//   select sum(x4."PRICE") from "COFFEES" x4

val q4 = q.avg
// compiles to SQL (simplified):
//   select avg(x4."PRICE") from "COFFEES" x4
```

Tenga en cuenta que estas consultas agregadas devuelven un resultado escalar, no una colección. Algunas funciones de agregación se definen para consultas arbitrarias (de más de una columna):

```scala
val q1 = coffees.length
// compiles to SQL (simplified):
//   select count(1) from "COFFEES"

val q2 = coffees.exists
// compiles to SQL (simplified):
//   select exists(select * from "COFFEES")
```

La agrupación se realiza con el método groupBy. Tiene la misma semántica que para las colecciones Scala:

```scala
val q = (for {
  c <- coffees
  s <- c.supplier
} yield (c, s)).groupBy(_._1.supID)

val q2 = q.map { case (supID, css) =>
  (supID, css.length, css.map(_._1.price).avg)
}
// compiles to SQL:
//   select x2."SUP_ID", count(1), avg(x2."PRICE")
//     from "COFFEES" x2, "SUPPLIERS" x3
//     where x3."SUP_ID" = x2."SUP_ID"
//     group by x2."SUP_ID"
```

La consulta intermedia q contiene valores anidados de tipo Query. Éstos se convertirían en colecciones anidadas al ejecutar la consulta, lo que no está soportado por el momento. Por lo tanto, es necesario aplanar las consultas anidadas inmediatamente agregando sus valores (o columnas individuales) como se hace en q2.

### Querying

Una consulta puede convertirse en una acción llamando a su método de resultado. A continuación, la acción puede ejecutarse directamente en flujo o de forma totalmente materializada, o componerse con otras acciones:

```scala
val q = coffees.map(_.price)
val action = q.result
val result: Future[Seq[Double]] = db.run(action)
val sql = action.statements.head
```

Si sólo desea un único valor resultante, puede llamar a head o headOption en la Acción resultante.

### Deleting

El borrado funciona de forma muy similar a la consulta. Escribes una consulta que selecciona las filas a borrar y luego obtienes una Acción llamando al método delete sobre ella:

```scala
val q = coffees.filter(_.supID === 15)
val action = q.delete
val affectedRowsCount: Future[Int] = db.run(action)
val sql = action.statements.head
```

Una consulta para borrar sólo debe seleccionar de una única tabla. Cualquier proyección se ignora (siempre borra filas completas).

### Inserting

Las inserciones se realizan a partir de una proyección de columnas de una misma tabla. Cuando se utiliza la tabla directamente, la inserción se realiza contra su * proyección. Si se omiten algunas de las columnas de una tabla al realizar la inserción, la base de datos utilizará los valores predeterminados especificados en la definición de la tabla, o un valor predeterminado específico del tipo en caso de que no se haya indicado ningún valor predeterminado explícito. Todos los métodos para crear acciones de inserción se definen en CountingInsertActionComposer y ReturningInsertActionComposer.

```scala
val insertActions = DBIO.seq(
  coffees += ("Colombian", 101, 7.99, 0, 0),

  coffees ++= Seq(
    ("French_Roast", 49, 8.99, 0, 0),
    ("Espresso",    150, 9.99, 0, 0)
  ),

  // "sales" and "total" will use the default value 0:
  coffees.map(c => (c.name, c.supID, c.price)) += ("Colombian_Decaf", 101, 8.99)
)

// Obtener la declaración sin tener que especificar un valor a insertar:
val sql = coffees.insertStatement

// compiles to SQL:
//   INSERT INTO "COFFEES" ("COF_NAME","SUP_ID","PRICE","SALES","TOTAL") VALUES (?,?,?,?,?)
```

Cuando se incluye una columna AutoInc en una operación de inserción, se ignora silenciosamente, para que la base de datos pueda generar el valor adecuado. En este caso, normalmente querrá recuperar la columna de clave primaria autogenerada. Por defecto, += le proporciona un recuento del número de filas afectadas (que normalmente será 1) y ++= le proporciona un recuento acumulado en una Opción (que puede ser None si el sistema de base de datos no proporciona recuentos para todas las filas). Esto puede cambiarse con el método de retorno, en el que se especifican las columnas a devolver (como un único valor o tupla desde += y como una Secuencia de dichos valores desde ++=):

```scala
val userId =
  (users returning users.map(_.id)) += User(None, "Stefan", "Zeiger")
```

**Nota**
Muchos sistemas de bases de datos sólo permiten devolver una única columna, que debe ser la clave primaria autoincrementable de la tabla. Si se piden otras columnas se lanza una SlickException en tiempo de ejecución (a menos que la base de datos lo soporte).

Puede seguir el método returning con el método into para asignar los valores insertados y las claves generadas (especificadas en returning) a un valor deseado. He aquí un ejemplo de uso de esta función para devolver un objeto con un id actualizado:

```scala
val userWithId =
  (users returning users.map(_.id)
         into ((user,id) => user.copy(id=Some(id)))
  ) += User(None, "Stefan", "Zeiger")
```

En lugar de insertar datos desde el lado del cliente también puede insertar datos creados por una Consulta o una expresión escalar que se ejecuta en el servidor de base de datos:

```scala
class Users2(tag: Tag) extends Table[(Int, String)](tag, "users2") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name")
  def * = (id, name)
}
val users2 = TableQuery[Users2]

val actions = DBIO.seq(
  users2.schema.create,
  users2 forceInsertQuery (users.map { u => (u.id, u.first ++ " " ++ u.last) }),
  users2 forceInsertExpr (users.length + 1, "admin")
)
```

En estos casos, las columnas AutoInc no se ignoran.

### Updating

Las actualizaciones se realizan escribiendo una consulta que selecciona los datos a actualizar y luego los sustituye por datos nuevos. La consulta sólo debe devolver columnas sin procesar (sin valores calculados) seleccionadas de una única tabla. Los métodos relevantes para la actualización se definen en UpdateExtensionMethods.

```scala
val q = for { c <- coffees if c.name === "Espresso" } yield c.price
val updateAction = q.update(10.49)

// Obtener la declaración sin tener que especificar un valor actualizado:
val sql = q.updateStatement

// compiles to SQL:
//   update "COFFEES" set "PRICE" = ? where "COFFEES"."COF_NAME" = 'Espresso'
```

Actualmente no hay forma de utilizar expresiones escalares o transformaciones de los datos existentes en la base de datos para las actualizaciones.

### Upserting

La sobreinserción se realiza suministrando una fila que debe insertarse o actualizarse. El objeto debe contener la clave primaria de la tabla, ya que la parte de actualización debe ser capaz de encontrar una fila que coincida de forma única.

```scala
val updated = users.insertOrUpdate(User(Some(1), "Admin", "Zeiger"))
// returns: number of rows updated

val updatedAdmin = (users returning users).insertOrUpdate(User(Some(1), "Slick Admin", "Zeiger"))
// returns: None if updated, Some((Int, String)) if row inserted
```

### Consultas compiladas

Las consultas a bases de datos suelen depender de algunos parámetros, por ejemplo, un ID para el que desea recuperar una fila de base de datos coincidente. Puedes escribir una función Scala regular para crear un objeto Query parametrizado cada vez que necesites ejecutar esa consulta pero esto incurrirá en el coste de recompilar la consulta en Slick (y posiblemente también en la base de datos si no usas variables bind para todos los parámetros). Es más eficiente precompilar dichas funciones de consulta parametrizadas:

```scala
def userNameByIDRange(min: Rep[Int], max: Rep[Int]) =
  for {
    u <- users if u.id >= min && u.id < max
  } yield u.first

val userNameByIDRangeCompiled = Compiled(userNameByIDRange _)

// La consulta se compilará una sola vez:
val namesAction1 = userNameByIDRangeCompiled(2, 5).result
val namesAction2 = userNameByIDRangeCompiled(1, 3).result
// También funciona para .insert, .update y .delete
```

Esto funciona para todas las funciones que toman parámetros consistentes únicamente en columnas individuales o registros de columnas y devuelven un objeto Query o una consulta escalar. Consulte la documentación de la API de Compilado y sus subclases para obtener más información sobre la composición de consultas compiladas.

Tenga en cuenta que take y drop toman parámetros ConstColumn[Long]. A diferencia de Rep[Long]], que podría sustituirse por otro valor calculado por una consulta, una ConstColumn sólo puede ser un valor literal o un parámetro de una consulta compilada. Esto es necesario porque el valor real tiene que ser conocido en el momento en que la consulta es preparada para su ejecución por Slick.

```scala
val userPaged = Compiled((d: ConstColumn[Long], t: ConstColumn[Long]) => users.drop(d).take(t))

val usersAction1 = userPaged(2, 1).result
val usersAction2 = userPaged(1, 3).result
```

Puede utilizar una consulta compilada para consultar, insertar, actualizar y eliminar datos. Por compatibilidad con Slick 1.0, puede crear una consulta compilada llamando a flatMap sobre un objeto Parameters. En muchos casos, esto permite escribir un único for de comprensión para una consulta compilada:

```scala
val userNameByID = for {
  id <- Parameters[Int]
  u <- users if u.id === id
} yield u.first

val nameAction = userNameByID(2).result.head

val userNameByIDRange = for {
  (min, max) <- Parameters[(Int, Int)]
  u <- users if u.id >= min && u.id < max
} yield u.first

val namesAction = userNameByIDRange(2, 5).result
```

### Connection Pool

Con la versión Slick 3, Slick inicia y controla tanto un pool de conexiones como un pool de hilos para una ejecución asíncrona óptima de sus acciones de base de datos.

En Play Slick hemos decidido dejar que Slick controle la creación y gestión del pool de conexiones (el pool de conexiones por defecto usado por Slick 3 es HikariCP), lo que significa que para ajustar el pool de conexiones necesitarás mirar el ScalaDoc de Slick para [Database.forConfig](http://slick.typesafe.com/doc/3.1.0/api/index.html#slick.jdbc.JdbcBackend$DatabaseFactoryDef@forConfig(String,Config,Driver,ClassLoader):Database) (asegúrate de expandir la fila forConfig en el documento). De hecho, ten en cuenta que cualquier valor que puedas pasar para establecer el pool de conexiones de Play (por ejemplo, bajo la clave play.db.default.hikaricp) simplemente no es recogido por Slick, y por lo tanto es efectivamente ignorado.

Además, ten en cuenta que como se indica en la documentación de Slick, un valor predeterminado razonable para el tamaño del pool de conexiones se calcula a partir del tamaño del pool de hilos. De hecho, sólo deberías necesitar ajustar numThreads y queueSize en la mayoría de los casos, para cada configuración de tu base de datos.

Finalmente, vale la pena mencionar que mientras Slick permite usar un pool de conexiones diferente a HikariCP (aunque, Slick actualmente sólo ofrece soporte integrado para HikariCP, y requiere que proporciones una implementación de JdbcDataSourceFactory si quieres usar un pool de conexiones diferente), Play Slick actualmente no permite usar un pool de conexiones diferente a HikariCP.

**Nota:** Cambiar el valor de play.db.pool no afectará a qué pool de conexión está usando Slick. Además, ten en cuenta que cualquier configuración bajo play.db no es considerada por Play Slick.

### Pool de hilos

Con el lanzamiento de Slick 3.0, Slick inicia y controla tanto un grupo de hilos como un grupo de conexiones para una ejecución asíncrona óptima de sus acciones de base de datos.

Para una ejecución óptima, puede que necesites ajustar los parámetros numThreads y queueSize, para cada una de las configuraciones de tu base de datos. Consulte la documentación de Slick para más detalles.

## Acceso a una base de datos SQL

NOTA: JDBC es una operación de bloqueo que causará hilos de espera. Puede afectar negativamente al rendimiento de su aplicación Play si ejecuta consultas JDBC directamente en su controlador. Consulte la sección "Configuración de un CustomExecutionContext".

### Configuración de grupos de conexiones JDBC

Play proporciona un plug-in para gestionar pools de conexiones JDBC. Puede configurar tantas bases de datos como necesite.

Para habilitar el complemento de base de datos, añada jdbc en sus dependencias de compilación :

```scala
libraryDependencies += jdbc
```

A continuación, debe configurar un pool de conexiones en el archivo conf/application.conf. Por convención, la fuente de datos JDBC por defecto debe llamarse default y las propiedades de configuración correspondientes son db.default.driver y db.default.url.

Si algo no está bien configurado, se le notificará directamente en su navegador.

**Nota:** Es probable que tenga que encerrar el valor de configuración de la URL JDBC entre comillas dobles, ya que ':' es un carácter reservado en la sintaxis de configuración.

#### Propiedades de conexión del motor de base de datos H2

Base de datos en memoria:

```scala
# Configuración por defecto de la base de datos utilizando el motor de base de datos H2 en modo en memoria
db.default.driver=org.h2.Driver
db.default.url="jdbc:h2:mem:play"
```

Base de datos basada en ficheros:

```scala
# Configuración por defecto de la base de datos utilizando el motor de base de datos H2 en modo persistente
db.default.driver=org.h2.Driver
db.default.url="jdbc:h2:/path/to/db-file"
```
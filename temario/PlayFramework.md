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
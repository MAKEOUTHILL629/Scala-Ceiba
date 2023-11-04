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
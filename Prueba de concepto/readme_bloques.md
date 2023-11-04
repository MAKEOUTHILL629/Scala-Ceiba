
Este bloque contiene la estructura necesaria para construir un API REST en Scala implementando una arquitectura hexagonal siguiendo los principios de DDD  

Los principales patrones y estilos de arquitectura que guían este bloque son

### Arquitectura

####  Rest  
Rest es una especificación arquitectural que busca crear interfaces mucho mas claras y concisas representando la información como recursos que pueden ser accedidos a través de la web.
[https://restfulapi.net/](https://restfulapi.net/)

#### Arquitectura hexagonal

Arquitectura que fomenta que nuestro dominio sea el núcleo de todas las capas, también conocida como puertos y adaptadores en la cual el dominio define los puertos y en las capas superiores se definen los adaptadores para desacoplar el dominio. Se divide princialmente en tres capas, **aplicación**, **dominio** e **infraestructura**

-  **Infraestructura**: Capa que tiene las responsabilidades de realizar los adaptadores a los puertos definidos en el domino, exponer web services, consumir web services, realizar conexiones a bases de datos, ejecutar sentencias DML, en general todo lo que sea implementaciones de cualquier framework

-  **Aplicación**: capa encargada de enrutar los eventos entrantes de la capa de infraestructura hacía la capa del dominio, generalmente se conoce como una barrera transaccional la cual agrupa toda la invocación de un caso de uso, se pueden encontrar patrones como Fabricas, Manejadores de Comandos, Bus de eventos, etc

-  **Dominio**: representa toda la lógica de negocio de la aplicación la cual es la razón de existir del negocio. Se busca evitar el anti-patron [https://martinfowler.com/bliki/AnemicDomainModel.html](https://martinfowler.com/bliki/AnemicDomainModel.html) y favorecer el principio [https://martinfowler.com/bliki/TellDontAsk.html](https://martinfowler.com/bliki/TellDontAsk.html) en esta capa se pueden encontrar los siguientes patrones agregados, servicios de dominio, entidades, objetos de valor, repositorios (puerto), etc.

  

Para obtener mas documentación sobre este tipo de arquitectura se recomienda [https://codely.tv/blog/screencasts/arquitectura-hexagonal-ddd/](https://codely.tv/blog/screencasts/arquitectura-hexagonal-ddd/)

#### Especificaciones técnicas:

  

- Scala 2.12

- Sbt 1.3.3

- PlayFramework 2.7
- Slick

- Cats
- Monix

- Swagger
    

#### Compilar el proyecto:

Para compilar el proyecto se debe tener instalado SBT y desde su IDE de confianza(Con plugins para SBT) o desde la linea de comandos de SBT ejecutar la tarea.

`sbt build`

#### Correr pruebas con coberturas

Para correr  el proyecto se debe ejecutar  la siguiente tarea	
`sbt web/run`

#### Ejecutar el proyecto:

Para compilar el proyecto se debe tener instalado SBT y desde su IDE de confianza(Con plugins para SBT) o desde la linea de comandos de SBT ejecutar la tarea.

`sbt build`
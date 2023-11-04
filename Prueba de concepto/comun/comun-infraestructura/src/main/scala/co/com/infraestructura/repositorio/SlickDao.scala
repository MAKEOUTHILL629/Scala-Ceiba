package co.com.infraestructura.repositorio

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.language.postfixOps

trait SlickDao extends HasDatabaseConfigProvider[JdbcProfile]
  with SlickQueryOps {

  type SimpleJoin[A, B] = List[(A, List[B])]
  type DoubleJoin[A, B, C] = List[(A, SimpleJoin[B, C])]

  /**
   *
   * @param as: Es el resultado de hacer un joinLeft en slick
   * @tparam A: Tipo de dato de la primera tabla
   * @tparam B: Tipo de dato de la segunda tabla
   * @return un SimpleJoin[A, B] que abstrae la organizacion de los datos en una lista
   *         de tuplas organizadas por cada ocurrencia unica del tipo A
   */
  protected def resolveLeftJoin[A, B](as: Seq[(A, Option[B])]): SimpleJoin[A, B] = for {
    (x1, y1) <- as.groupBy(_._1).toList
    x2 = y1 collect { case (_, Some(x)) => x } toList
  } yield (x1, x2)

  /**
   *
   * @param doubleJoin: Es el resultado de hacer dos joinLeft consecutivos en slick
   * @tparam A: Tipo de dato de la primera tabla
   * @tparam B: Tipo de dato de la segunda tabla
   * @tparam C: Tipo de dato de la tercera tabla
   * @return un DoubleJoin[A, B, C] que abstrae la organizacion de los datos derivados
   *         de un SimpleJoin[A, B]
   */
  protected def resolveDoubleLeftJoin[A, B, C](doubleJoin: Seq[(A, Option[(B, Option[C])])]): DoubleJoin[A, B, C] = for {
    (a, as) <- resolveLeftJoin(doubleJoin)
  } yield (a, resolveLeftJoin(as))

}

package co.com.infraestructura.repositorio

import play.api.db.slick.HasDatabaseConfig
import slick.ast.ScalaBaseType
import slick.jdbc.JdbcProfile

trait SlickQueryOps {
  databaseConfig: HasDatabaseConfig[JdbcProfile] =>

  import cats.Apply
  import cats.instances.option._
  import profile.api._

  implicit class RepOps[T](rep: Rep[T]) {
    def maybeEqual(opt: Option[T])(implicit typed: ScalaBaseType[T]): Option[Rep[Boolean]] =
      opt.map(o => rep === o)

    def ?=(opt: Option[T])(implicit typed: ScalaBaseType[T]) = maybeEqual(opt)
  }

  implicit class QueryOptionFilter[X, Y](query: Query[X, Y, Seq]) {

    def paginated(page: Option[Long], size: Option[Long]): Query[X, Y, Seq] = {
      val comp = Compiled((d: ConstColumn[Long], t: ConstColumn[Long]) => query.drop(d).take(t))
      Apply[Option].map2(size, page)((p, s) => comp(p, s).extract) getOrElse query
    }

    def maybeFoundBy[T](ops: X => List[Option[Rep[Boolean]]]): Query[X, Y, Seq] =
      query.filter(q => ops(q).collect { case Some(y) => y } reduceLeftOption (_ && _) getOrElse false.bind)

    def filteredBy[T](ops: X => List[Option[Rep[Boolean]]])(f: (Rep[Boolean], Rep[Boolean]) => Rep[Boolean]): Query[X, Y, Seq] =
      query.filter(q => ops(q).collect { case Some(y) => y } reduceLeftOption f getOrElse true.bind)
  }
}

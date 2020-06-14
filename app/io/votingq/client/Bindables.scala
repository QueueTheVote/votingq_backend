package io.votingq.client

import play.api.mvc.QueryStringBindable

object Bindables {
  implicit def setBindable[T: QueryStringBindable]: QueryStringBindable[Set[T]] = {
    val wrapped = implicitly[QueryStringBindable[List[T]]]
    new QueryStringBindable[Set[T]] {
      override def bind(
        key: String,
        params: Map[String, Seq[String]]
      ): Option[Either[String, Set[T]]] = wrapped.bind(key, params).map {either =>
        either.flatMap {
          case aList if aList.distinct != aList => Left("Collection not distinct.")
          case aDistinctList => Right(aDistinctList.toSet)
        }
      }

      override def unbind(key: String, value: Set[T]): String = wrapped.unbind(key, value.toList)
    }
  }

  implicit val setBindableLong: QueryStringBindable[Set[Long]] = setBindable[Long]
}

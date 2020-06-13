package service.models

import play.api.libs.json.{Json, Writes}

import scala.util.matching.Regex

case class Address(
  street1: String,
  street2: Option[String],
  city: String,
  state: String,
  zip: String
) {

  require(Address.isStateValid(state.toUpperCase), "State must be 2-letter abbreviation")
}

object Address {

  private val stateRegex: Regex = """^[A-Z]{2}$""".r

  private def isStateValid(
    state: String
  ): Boolean = stateRegex.findFirstMatchIn(state).isDefined

  implicit val writes: Writes[Address] = Json.writes[Address]
}

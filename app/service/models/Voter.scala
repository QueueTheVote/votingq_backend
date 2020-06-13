package service.models

import play.api.libs.json.{Json, Writes}

case class Voter(
  id: Long,
  name: String,
  email: String
)
object Voter {

  implicit val writes: Writes[Voter] = Json.writes[Voter]
}
